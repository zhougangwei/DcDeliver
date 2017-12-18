package com.aihui.dcdeliver.ui.fragment;

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
import android.widget.ImageView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.adapter.HomeAdapter;
import com.aihui.dcdeliver.adapter.InspectPagerAdapter;
import com.aihui.dcdeliver.base.BaseFragment;
import com.aihui.dcdeliver.base.BaseView;
import com.aihui.dcdeliver.bean.CancelBottomDialog;
import com.aihui.dcdeliver.bean.LoadingBean;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/3/17 15:31
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/3/17$
 * @ 更新描述  ${TODO}
 */

public class WorkSummaryFragment extends BaseFragment<HomeImpl> implements BaseView, TabLayout.OnTabSelectedListener {

    @BindView(R.id.tb)
    TabLayout mTb;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.cv)
    CardView  mCv;

    PtrClassicFrameLayout mStoreHousePtrFrame;
    private InspectPagerAdapter mPagerAdapter;

    String[] mTitleStrings = {
            "已完成"
            , "异常单"
            ,"取消单"
    };

    private List<String> mWaitingBillList   = new ArrayList<>();
    private List<String> mReceivingBillList = new ArrayList<>();


    //存放列表的
    private List<FrameLayout> mViewList = new ArrayList<>();
    private List<LoadingBean>  mDataList = new ArrayList<>();

    private HomeAdapter   mWaitingAdapter;
    private CommonAdapter mReceiAdapter;
    private AlertDialog   mDakaDialog;
    private RecyclerView  mWaitingBillRecycleView;

    private boolean      isErr;
    private int          mCurrentCounter;
    private double       TOTAL_COUNTER;
    private RecyclerView mReceivingBillRecycleView;
    private SwipeRefreshLayout mWaitSpr;


    @Override
    protected void initView() {


        initTabLayout();
        initRecyclView();
        //initRefreshView();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_waybill_receive;
    }

    @Override
    protected void initData() {

        mWaitingBillList.add("33333");
        mWaitingBillList.add("11111");
        mWaitingBillList.add("44444");
        mWaitingBillList.add("5555");
        mWaitingBillList.add("666");
        mWaitingBillList.add("777");
        mWaitingBillList.add("8888");

        mReceivingBillList.add("222222");
        mReceivingBillList.add("33333");
        mReceivingBillList.add("11111");
        mReceivingBillList.add("44444");


        //mWaitingAdapter.notifyDataSetChanged();
        mReceiAdapter.notifyDataSetChanged();

        judgeIfAlert();
    }

    private void judgeIfAlert() {
        if (true) {

            View inflate = View.inflate(getActivity(), R.layout.dialog_daka, null);
            ImageView iv_close = (ImageView) inflate.findViewById(R.id.iv_close);




            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDakaDialog.dismiss();


                }
            });

            mDakaDialog = new AlertDialog.Builder(getActivity())
                    .setView(inflate)
                    .create();

            mDakaDialog.show();

            WindowManager.LayoutParams p = mDakaDialog.getWindow().getAttributes();
            p.height = (int) (ScreenUtils.getScreenHeight(mActivity) * 0.4);
            p.width = (int) (ScreenUtils.getScreenWidth(mActivity) * 0.77);

            mDakaDialog.getWindow().setAttributes(p);

        }

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


        final MaterialHeader header = new MaterialHeader(mActivity);
        int[] colors = getResources().getIntArray(R.array.fresh_color);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, SizeUtils.dp2px(mActivity, 15), 0, SizeUtils.dp2px(mActivity, 10));
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
        FrameLayout mWaitingFrame = (FrameLayout) View.inflate(mActivity, R.layout.frame_recycleview, null);
        FrameLayout mReceivingFrame = (FrameLayout) View.inflate(mActivity, R.layout.frame_recycleview, null);


        mWaitingBillRecycleView = mWaitingFrame.findViewById(R.id.rv);
        mReceivingBillRecycleView = mReceivingFrame.findViewById(R.id.rv);
        mWaitSpr = (SwipeRefreshLayout) mWaitingFrame.findViewById(R.id.swipeLayout);
        SwipeRefreshLayout receiSpr = (SwipeRefreshLayout) mReceivingFrame.findViewById(R.id.swipeLayout);
        mWaitSpr.setColorSchemeColors(Color.rgb(47, 223, 189));
        receiSpr.setColorSchemeColors(Color.rgb(47, 223, 189));

        mWaitSpr.setRefreshing(true);
        mWaitSpr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(mWaitSpr);
            }
        });
        receiSpr.setRefreshing(true);

        mViewList.add(mWaitingFrame);
        mViewList.add(mReceivingFrame);

        mWaitingBillRecycleView.setLayoutManager(new LinearLayoutManager(mActivity));
        mPagerAdapter.notifyDataSetChanged();

        mWaitingAdapter = new HomeAdapter(R.layout.item_rv_watingbill, mWaitingBillList);
        mWaitingAdapter.openLoadAnimation();

        final ArrayList<String> objects = new ArrayList<>();
        objects.add("1111111111");
        objects.add("222");
        objects.add("woowoweoewrw");
        objects.add("你好啊");
        mWaitingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                new CancelBottomDialog(mActivity).showDialog(objects, new CancelBottomDialog.onBackResult() {
                    @Override
                    public void backResult(String bean) {

                    }
                });
            }
        });

        mReceiAdapter = new CommonAdapter<String>(mActivity, R.layout.item_rv_watingbill, mReceivingBillList) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                holder.setText(R.id.tv_dlwz_before, o);
            }
        };


        mReceivingBillRecycleView.setAdapter(mReceiAdapter);
        mWaitingAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mWaitingBillRecycleView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(mActivity, R.string.network_err, Toast.LENGTH_LONG).show();
                        mWaitingAdapter.loadMoreComplete();
                       /* if (mCurrentCounter >= TOTAL_COUNTER) {
                            //数据全部加载完毕
                            mHomeAdapter.loadMoreEnd();
                        } else {
                            if (isErr) {
                                //成功获取更多数据
                             //   mHomeAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
                                mCurrentCounter = mHomeAdapter.getData().size();
                                mHomeAdapter.loadMoreComplete();
                            } else {
                                //获取更多数据失败
                                isErr = true;
                                Toast.makeText(mActivity, R.string.network_err, Toast.LENGTH_LONG).show();
                                mHomeAdapter.loadMoreFail();

                            }
                        }*/
                    }

                }, 500);
            }
        }, mWaitingBillRecycleView);
        mWaitingBillRecycleView.setAdapter(mWaitingAdapter);

        setScrollListener(mViewList, mDataList);
        refresh(mWaitSpr);

    }

    private void refresh(final SwipeRefreshLayout waitSpr) {

        mWaitingAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mWaitingAdapter.setEnableLoadMore(true);
                waitSpr.setRefreshing(false);
            }
        },500);

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
            RecyclerView recyclerView = (RecyclerView)frameLayout.findViewById(R.id.rv);
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
