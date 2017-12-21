/*
package com.aihui.dcdeliver.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.adapter.HomeAdapter;
import com.aihui.dcdeliver.adapter.InspectPagerAdapter;
import com.aihui.dcdeliver.base.AppActivity;
import com.aihui.dcdeliver.bean.LoadingBean;
import com.aihui.dcdeliver.bean.RecordBean;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class WaybillReceiveActivity extends AppActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.ll_title)
    LinearLayout          mLlTitle;
    @BindView(R.id.tb)
    TabLayout             mTb;
    @BindView(R.id.vp)
    ViewPager             mVp;
    @BindView(R.id.cv)
    CardView              mCv;
    @BindView(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout mStoreHousePtrFrame;
    private InspectPagerAdapter mPagerAdapter;

    String[] mTitleStrings = {
            "待接单"
            , "已借单"
    };

    private List<RecordBean.BodyBean.ListBean> mWaitingBillList   = new ArrayList<>();
    private List<RecordBean.BodyBean.ListBean> mReceivingBillList = new ArrayList<>();


    //存放列表的
    private List<FrameLayout> mViewList = new ArrayList<>();
    private List<LoadingBean> mDataList = new ArrayList<>();

    private HomeAdapter mWaitingAdapter;
    private HomeAdapter mReceiAdapter;
    private AlertDialog   mDakaDialog;

    //自己发送的单子
    private final int MY_SEND_RECORD =1;
    //可接受的单子
    private final int CAN_RECEIVE_RECORD =2;
    //自己处理的单子
    private final int MY_ACCET_RECORD =3;

    //单页数目
    private final int PAGE_NUM =10;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_waybill_receive;
    }


    @Override
    protected void initData() {
        RetrofitClient.getInstance().getRecordList(CAN_RECEIVE_RECORD,1,PAGE_NUM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RecordBean>(this) {
                    @Override
                    public void onNext(RecordBean recordBean) {
                        List<RecordBean.BodyBean.ListBean> list = recordBean.getBody().getList();
                        mWaitingBillList.clear();
                        mWaitingBillList.addAll(list);
                        mWaitingAdapter.notifyDataSetChanged();
                    }
                });

        RetrofitClient.getInstance().getRecordList(CAN_RECEIVE_RECORD,2,PAGE_NUM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RecordBean>(this) {
                    @Override
                    public void onNext(RecordBean recordBean) {
                        List<RecordBean.BodyBean.ListBean> list = recordBean.getBody().getList();
                        mReceivingBillList.clear();
                        mReceivingBillList.addAll(list);
                        mReceiAdapter.notifyDataSetChanged();
                    }
                });
        judgeIfAlert();

    }

    private void judgeIfAlert() {
        if (true) {

            View inflate = View.inflate(this, R.layout.dialog_daka, null);
            RelativeLayout rv_close =  inflate.findViewById(R.id.rv_close);
            rv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDakaDialog.dismiss();
                }
            });

            mDakaDialog = new AlertDialog.Builder(this)
                    .setView(inflate)
                    .create();

            mDakaDialog.show();

            WindowManager.LayoutParams p = mDakaDialog.getWindow().getAttributes();
            p.height = (int) (ScreenUtils.getScreenHeight(this) * 0.4);
            p.width = (int) (ScreenUtils.getScreenWidth(this) * 0.77);

            mDakaDialog.getWindow().setAttributes(p);

        }

    }

    @Override
    public void initView() {



        initTabLayout();
        initRecyclView();
        initRefreshView();




    }



    private void initRefreshView() {

        mStoreHousePtrFrame.disableWhenHorizontalMove(true);
        mStoreHousePtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // 默认实现，根据实际情况做改动
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });


        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.fresh_color);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, SizeUtils.dp2px(this,15), 0, SizeUtils.dp2px(this,10));
        header.setPtrFrameLayout(mStoreHousePtrFrame);

        mStoreHousePtrFrame.setLoadingMinTime(1000);
        mStoreHousePtrFrame.setDurationToCloseHeader(1500);
        mStoreHousePtrFrame.setHeaderView(header);
        mStoreHousePtrFrame.addPtrUIHandler(header);
        mStoreHousePtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mStoreHousePtrFrame.autoRefresh(false);
            }
        }, 100);

        mStoreHousePtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }
            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {

                    long delay = (long) (1000 + Math.random() * 2000);
                    delay = Math.max(0, delay);
                    delay = 0;
                    frame.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            frame.refreshComplete();
                        }
                    }, delay);
            }
        });
    }



















    private void initRecyclView() {
        RecyclerView waitingBill = (RecyclerView) View.inflate(this, R.layout.frame_recycleview, null);
        RecyclerView receivingBill = (RecyclerView) View.inflate(this, R.layout.frame_recycleview, null);
       */
/* mViewList.add(waitingBill);
        mViewList.add(receivingBill);*//*

        mPagerAdapter.notifyDataSetChanged();

        mWaitingAdapter  = new HomeAdapter(R.layout.item_rv_watingbill, mWaitingBillList);
        mReceiAdapter  = new HomeAdapter(R.layout.item_rv_watingbill, mReceivingBillList);
        waitingBill.setAdapter(mWaitingAdapter);
        receivingBill.setAdapter(mReceiAdapter);
        */
/*setScrollListener(mViewList, mDataList);*//*



    }

    private void initTabLayout() {
        mPagerAdapter = new InspectPagerAdapter(mTitleStrings, mViewList, this);
        mVp.setAdapter(mPagerAdapter);
        mTb.setupWithViewPager(mVp);
        mTb.addOnTabSelectedListener(this);
        mDataList.add(new LoadingBean(mWaitingBillList, false));
        mDataList.add(new LoadingBean(mReceivingBillList, false));

    }


    */
/**
     * @param viewList 图的列表
     * @param dataList 数据的列表
     *//*

    private void setScrollListener(List<RecyclerView> viewList, final List<LoadingBean> dataList) {
        for (int i = 0; i < viewList.size(); i++) {
            RecyclerView recyclerView = viewList.get(i);
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            final int finalI = i;
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                    int totalItemCount = mLayoutManager.getItemCount();
                    //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                    // dy>0 表示向下滑动
                    if (lastVisibleItem >= totalItemCount - 4 && dy > 0 && lastVisibleItem != totalItemCount - 1) {
                        if (!dataList.get(finalI).isLoading) {
                            dataList.get(finalI).isLoading = true;

                            //滚去数据查询
                            dataList.get(finalI).isLoading = false;
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
*/
