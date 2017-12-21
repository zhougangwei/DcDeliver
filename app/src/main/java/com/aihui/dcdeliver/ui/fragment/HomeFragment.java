package com.aihui.dcdeliver.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.adapter.InspectPagerAdapter;
import com.aihui.dcdeliver.adapter.ReceivedAdapter;
import com.aihui.dcdeliver.adapter.WaitAdapter;
import com.aihui.dcdeliver.base.BaseFragment;
import com.aihui.dcdeliver.base.BaseView;
import com.aihui.dcdeliver.bean.DataMessage;
import com.aihui.dcdeliver.bean.LoadingBean;
import com.aihui.dcdeliver.bean.RecordListBean;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.aihui.dcdeliver.rxbus.RxBus;
import com.aihui.dcdeliver.rxbus.event.FraEvent;
import com.aihui.dcdeliver.rxbus.event.ReceiveEvent;
import com.aihui.dcdeliver.ui.activity.WaybillInTransActivity;
import com.aihui.dcdeliver.ui.imp.HomeImpl;
import com.blankj.utilcode.utils.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/3/17 15:31
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/3/17$
 * @ 更新描述  ${TODO}
 */

public class HomeFragment extends BaseFragment<HomeImpl> implements BaseView, TabLayout.OnTabSelectedListener {

    @BindView(R.id.tb)
    TabLayout mTb;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.cv)
    CardView  mCv;

    PtrClassicFrameLayout mStoreHousePtrFrame;
    private InspectPagerAdapter mPagerAdapter;

    String[] mTitleStrings = {
            "待接单"
            , "正在进行"
    };


    private List<RecordListBean.BodyBean.ListBean> mWaitingBillList   = new ArrayList<>();
    private List<RecordListBean.BodyBean.ListBean> mReceivingBillList = new ArrayList<>();


    //存放列表的
    private List<FrameLayout> mViewList = new ArrayList<>();
    private List<LoadingBean> mDataList = new ArrayList<>();

    private WaitAdapter     mWaitingAdapter;
    private ReceivedAdapter mReceiAdapter;
    private AlertDialog     mDakaDialog;


    private boolean isErr;
    private int     mCurrentCounter;
    private double  TOTAL_COUNTER;


    //自己发送的所有单子
    private final String MY_SEND_RECORD = "1";
    //可接受的单子
    public static final String WAIT_RECORD    = "2";
    //自己处理的单子
    public static final String RECEIVE_RECORD = "3";

    //自己发送的正在进行的单子
    private static final String MY_SENDING_RECORD = "4";
    //自己发送的已完成的单子
    public static final String MY_FINISH_RECORED    = "5";


    public   String local_record_first    ="4" ;
    public   String local_record_two    ="5" ;



    //单页数目
    private final int PAGE_NUM = 10;
    private Map<String, DataMessage> mLocalData;



    private boolean isReceice= false;

    @Override
    protected void initView() {
        //初始化两组数据的状态总数
        mLocalData = new HashMap<>();
        mLocalData.put(local_record_first, new DataMessage());
        mLocalData.put(local_record_two, new DataMessage());
        initTabLayout();
        initRecyclView();
        //initRefreshView();
       /* boolean aBoolean = SPUtil.getBoolean(mActivity, Content.HAS_SAVE, false);
        //说明是有开单权限的
        if(aBoolean){
            isReceice  = aBoolean;
        }*/

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_waybill_receive;
    }

    @Override
    protected void initData() {



        judgeIfAlert();
        initEvent();

    }

    private void initEvent() {
        RxBus.getInstance().toObservable(ReceiveEvent.class)
                .subscribe(new Action1<ReceiveEvent>() {
                    @Override
                    public void call(ReceiveEvent downEvent) {
                       refresh();
                    }
                });

        RxBus.getInstance().toObservable(FraEvent.class)
                .subscribe(new Action1<FraEvent>() {
                    @Override
                    public void call(FraEvent fraEvent) {
                             changeFragment();
                    }
                });
    }

    private void changeFragment() {
        isReceice=!isReceice;
        if (isReceice){
            mTitleStrings[0]="正在进行";
            mTitleStrings[1]="已完成";
        }else{
            mTitleStrings[0]="待接单";
            mTitleStrings[1]="正在进行";
        }
        if(isReceice){
            local_record_first = MY_SENDING_RECORD;
            local_record_two = MY_FINISH_RECORED;

        }else{
            local_record_first = WAIT_RECORD;
            local_record_two = RECEIVE_RECORD;
        }







        mLocalData.put(local_record_first, new DataMessage());
        mLocalData.put(local_record_two, new DataMessage());


        mPagerAdapter.notifyDataSetChanged();
        refresh();
    }




    /**
     * 获取数据
     *
     * @param dataType
     * @param dataList
     * @param dataAdapter
     */
    private void getData(final boolean ifDel, final String dataType, final List<RecordListBean.BodyBean.ListBean> dataList, final BaseQuickAdapter dataAdapter) {
        RetrofitClient.getInstance().getRecordList(dataType, 1, PAGE_NUM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RecordListBean>(mActivity) {
                    @Override
                    public void onNext(RecordListBean recordListBean) {
                        //设置总数
                        mLocalData.get(dataType).setTotal(recordListBean.getBody().getTotal());
                        //设置数据渲染
                        List<RecordListBean.BodyBean.ListBean> list = recordListBean.getBody().getList();
                        if (ifDel) {
                            //加载更多不删数据 刷新删数据
                            dataList.clear();
                        }
                        dataList.addAll(list);
                        //设置当前数目
                        mLocalData.get(dataType).setCurrentNum(dataList.size());
                        dataAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLocalData.get(dataType).setErr(true);
                        super.onError(e);
                    }
                });
    }

    /**
     * 判断是否弹窗打卡
     */
    private void judgeIfAlert() {
        if (true) {

            View inflate = View.inflate(getBaseActivity(), R.layout.dialog_daka, null);
            RelativeLayout rv_close = inflate.findViewById(R.id.rv_close);

            rv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDakaDialog.dismiss();
                }
            });

            mDakaDialog = new AlertDialog.Builder(getBaseActivity())
                    .setView(inflate)
                    .create();

            mDakaDialog.show();

            WindowManager.LayoutParams p = mDakaDialog.getWindow().getAttributes();
            p.height = (int) (ScreenUtils.getScreenHeight(mActivity) * 0.4);
            p.width = (int) (ScreenUtils.getScreenWidth(mActivity) * 0.77);
            mDakaDialog.getWindow().setAttributes(p);
        }
    }


    /**
     * 初始化RecycleView
     */
    private void initRecyclView() {
        FrameLayout mWaitingFrame = (FrameLayout) View.inflate(mActivity, R.layout.frame_recycleview, null);
        FrameLayout mReceivingFrame = (FrameLayout) View.inflate(mActivity, R.layout.frame_recycleview, null);

        RecyclerView mWaitingBillRecycleView = mWaitingFrame.findViewById(R.id.rv);
        RecyclerView mReceivingBillRecycleView = mReceivingFrame.findViewById(R.id.rv);

        final SwipeRefreshLayout mWaitSpr = (SwipeRefreshLayout) mWaitingFrame.findViewById(R.id.swipeLayout);
        final SwipeRefreshLayout mReceiveSpr = (SwipeRefreshLayout) mReceivingFrame.findViewById(R.id.swipeLayout);

        mWaitSpr.setColorSchemeColors(Color.rgb(47, 223, 189));
        mReceiveSpr.setColorSchemeColors(Color.rgb(47, 223, 189));

        mWaitSpr.setRefreshing(true);
        mWaitSpr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                refresh(local_record_first, mWaitingBillList, mWaitSpr, mWaitingAdapter);
            }
        });
        mReceiveSpr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                refresh(local_record_two, mReceivingBillList, mReceiveSpr, mReceiAdapter);

            }
        });

        mReceiveSpr.setRefreshing(true);
        mWaitSpr.setRefreshing(true);

        mViewList.add(mWaitingFrame);
        mViewList.add(mReceivingFrame);

        mWaitingBillRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));
        mReceivingBillRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));

        mPagerAdapter.notifyDataSetChanged();

        /*
        * 适配器
        * */
        mWaitingAdapter = new WaitAdapter(R.layout.item_rv_watingbill, mWaitingBillList);
        mWaitingAdapter.openLoadAnimation();

        mReceiAdapter = new ReceivedAdapter(R.layout.item_rv_receivedbill, mReceivingBillList);
        mReceiAdapter.openLoadAnimation();
        mReceivingBillRecycleView.setAdapter(mReceiAdapter);

        /*
        * 设置刷新数据
        * */
        setLoadMore(mReceiAdapter, mReceivingBillList, mReceivingBillRecycleView, local_record_two);
        setLoadMore(mWaitingAdapter, mWaitingBillList, mWaitingBillRecycleView, local_record_first);

        mWaitingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, WaybillInTransActivity.class);
                intent.putExtra("recordId", mWaitingBillList.get(position).getId());
                intent.putExtra("type", local_record_first);
                startActivity(intent);
            }
        });

        mReceiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, WaybillInTransActivity.class);
                intent.putExtra("recordId", mReceivingBillList.get(position).getId());
                intent.putExtra("type", local_record_two);
                startActivity(intent);
            }
        });


        mWaitingBillRecycleView.setAdapter(mWaitingAdapter);
        mReceivingBillRecycleView.setAdapter(mReceiAdapter);

        setScrollListener(mViewList, mDataList);
        refresh(local_record_two, mReceivingBillList, mReceiveSpr, mReceiAdapter);
        refresh(local_record_first, mWaitingBillList, mWaitSpr, mWaitingAdapter);


    }

    private void setLoadMore(final BaseQuickAdapter dataAdapter, final List<RecordListBean.BodyBean.ListBean> dataList, final RecyclerView dataRecycleView, final String dataType) {
        dataAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                dataRecycleView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(mActivity, R.string.network_err, Toast.LENGTH_LONG).show();
                        dataAdapter.loadMoreComplete();
                        if (mLocalData.get(dataType).getCurrentNum() >= mLocalData.get(dataType).getTotal()) {
                            //数据全部加载完毕
                            dataAdapter.loadMoreEnd();
                        } else {
                            if (isErr) {
                                //成功获取更多数据
                                getData(false, dataType, dataList, dataAdapter);
                                dataAdapter.loadMoreComplete();
                            } else {
                                //获取更多数据失败
                                isErr = true;
                                Toast.makeText(mActivity, R.string.network_err, Toast.LENGTH_LONG).show();
                                dataAdapter.loadMoreFail();
                            }
                        }
                    }

                }, 500);
            }
        }, dataRecycleView);
    }


    /**
     * 刷新数据
     * @param dataType
     * @param dataList
     * @param waitSpr
     * @param adapter
     */
    private void refresh(final String dataType, final List<RecordListBean.BodyBean.ListBean> dataList, final SwipeRefreshLayout waitSpr, final BaseQuickAdapter adapter) {
        adapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(true, dataType, dataList, adapter);
                adapter.setEnableLoadMore(true);
                waitSpr.setRefreshing(false);
            }
        }, 500);

    }

    /**
     * 刷新全部数据
     */
    private void refresh() {

        getData(true, local_record_first, mWaitingBillList, mWaitingAdapter);
        getData(true, local_record_two, mReceivingBillList, mReceiAdapter);


    }

    private void initTabLayout() {
        mPagerAdapter = new InspectPagerAdapter(mTitleStrings, mViewList, mActivity);
        mVp.setAdapter(mPagerAdapter);
        mTb.setupWithViewPager(mVp);
        mTb.addOnTabSelectedListener(this);
        mDataList.add(new LoadingBean(mWaitingBillList, false));
        mDataList.add(new LoadingBean(mReceivingBillList, false));

    }


    /**
     * @param viewList 图的列表
     * @param dataList 数据的列表
     */
    private void setScrollListener(List<FrameLayout> viewList, final List<LoadingBean> dataList) {
        for (int i = 0; i < viewList.size(); i++) {
            FrameLayout frameLayout = viewList.get(i);
            RecyclerView recyclerView = (RecyclerView) frameLayout.findViewById(R.id.rv);
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
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


}
