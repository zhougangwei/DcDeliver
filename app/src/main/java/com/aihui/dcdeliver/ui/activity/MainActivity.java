package com.aihui.dcdeliver.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.adapter.MenuAdapter;
import com.aihui.dcdeliver.base.AppActivity;
import com.aihui.dcdeliver.base.BaseFragment;
import com.aihui.dcdeliver.base.Content;
import com.aihui.dcdeliver.bean.ServiceBean;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.aihui.dcdeliver.rxbus.RxBus;
import com.aihui.dcdeliver.rxbus.event.AddEvent;
import com.aihui.dcdeliver.rxbus.event.FraEvent;
import com.aihui.dcdeliver.service.BlueService;
import com.aihui.dcdeliver.ui.FragmentFactory;
import com.aihui.dcdeliver.util.SPUtil;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppActivity implements DuoMenuView.OnMenuClickListener {
    @BindView(R.id.iv_add)
    ImageView   mIvAdd;
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.tv_title)
    TextView    mTvTitle;
    private MenuAdapter mMenuAdapter;
    private ViewHolder  mViewHolder;

    private ArrayList<String> mTitles    = new ArrayList<>();
    private int[]             mTitleDraw = new int[]{
            R.mipmap.ic_ysd, R.mipmap.ic_gzhz, R.mipmap.ic_kqdk
            , R.mipmap.ic_kqdk
            , R.mipmap.ic_kqdk
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initData() {
        initEventBus();
    }

    private void initEventBus() {
        Subscription subscribe = RxBus.getInstance().toObservable(AddEvent.class)
                .subscribe(new Action1<AddEvent>() {
                    @Override
                    public void call(AddEvent fraEvent) {
                        //背压
                        if (mIvAdd != null) {
                            if (fraEvent.showAdd) {
                                mIvAdd.setVisibility(View.VISIBLE);
                            } else {
                                mIvAdd.setVisibility(View.GONE);
                            }
                        }
                    }
                });
        RxBus.getInstance().addSubscription(this,subscribe);

    }


    @Override
    protected void initView() {

        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));
        // Initialize the views
        mViewHolder = new ViewHolder();
        handleToolbar();
        // Handle menu actions
        handleMenu();
        // Handle drawer actions
        handleDrawer();
        goToFragment(0, false);
        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));
            startService();
    }

    private void startService() {

        Intent it = new Intent().setClass(this, BlueService.class);
        startService(it);
        bindService(it,mConnection,BIND_AUTO_CREATE);

    }


    private BlueService.MyBinder myBinder;
    private BlueService mService;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (BlueService.MyBinder) service;
            mService = myBinder.GetService();
            myBinder.startSendLocation();
        }
    };




    private void handleToolbar() {
        mTvTitle.setText(mTitles.get(0));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mViewHolder.mToolbar.setTitle("");
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles, mTitleDraw);

        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {
        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    private void goToFragment(int position, boolean addToBackStack) {
        /**
         * 先判断当前Fragment是否被添加到了MainActivity中
         * 如果添加了则直接显示即可
         * 如果没有添加则添加，然后显示
         */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BaseFragment fragment = FragmentFactory.getFragment(position);
        if (!fragment.isAdded()) {
            // addFragment(getFragmentContentId(),fragment,""+position);
            transaction.add(R.id.container, fragment, "" + position);
        }
        transaction.show(fragment).commit();

    }


    @Override
    protected void onDestroy() {
        if (mService != null) {
            unbindService(mConnection);
            //stopService(intent);
        }
        /*
        * 注销rxbus
        * */

        super.onDestroy();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        // Set the toolbar title
        mTvTitle.setText(mTitles.get(position));
        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);
        // Navigate to the right fragment
        switch (position) {
            case 3:
                mTvTitle.setText(mTitles.get(0));
                RxBus.getInstance().post(new FraEvent());
                break;
            case 4:
                gotoOut();
                break;
            default:
                goToFragment(position, false);
                break;


        }
        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    private void gotoOut() {

        RetrofitClient.getInstance().logout()
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<ServiceBean>(MainActivity.this) {
                    @Override
                    public void onNext(ServiceBean loginBean) {
                /*
                * 清除本地缓存
                * */
                        if(RxBus.getInstance().hasObservers()){
                            RxBus.getInstance().unSubscribe(this);
                        }

                        for (int i = 0; i < mTitles.size(); i++) {
                            BaseFragment fragment = FragmentFactory.getFragment(i);
                            /*
                            * 注销
                            * */
                            fragment.unSubsrcibe();
                            if (fragment!=null){
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.remove(fragment).commit();
                            }
                        }
                        FragmentFactory.clearAllFragment();
                        SPUtil.saveBoolean(MainActivity.this, Content.IS_LOGIN, false);
                        SPUtil.clear(MainActivity.this);
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                })
        ;

    }


    @OnClick({R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                Intent intent = new Intent(this, NewWayBillActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView     mDuoMenuView;
        private Toolbar         mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
        }
    }
}
