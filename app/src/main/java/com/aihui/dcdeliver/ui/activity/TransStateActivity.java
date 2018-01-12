package com.aihui.dcdeliver.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.base.AppActivity;
import com.aihui.dcdeliver.bean.RecordInfoBean;
import com.aihui.dcdeliver.commponent.stepview.VerticalStepView;
import com.aihui.dcdeliver.commponent.stepview.bean.StepBean;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.aihui.dcdeliver.rxbus.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TransStateActivity extends AppActivity {


    @BindView(R.id.iv_back)
    ImageView             mIvBack;

   @BindView(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout mPtr;
    @BindView(R.id.fl)
    FrameLayout mFl;

    private int mRecordId;
    private List<StepBean> mDetailList;
    private RecordInfoBean.BodyBean.TaskRecordBean mTaskRecordBean;
    private VerticalStepView mStepView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_trans_state;
    }


    @Override
    protected void initData() {

    }




    @Override
    protected void initView() {

        Intent intent = getIntent();

        mRecordId = intent.getIntExtra("recordId", -1);
        if (mRecordId ==-1){
            return;
        }
        mDetailList = (List<StepBean>) intent.getSerializableExtra("detailList");

        mTaskRecordBean = (RecordInfoBean.BodyBean.TaskRecordBean) intent.getSerializableExtra("taskFirst");
        String gmtCreate = mTaskRecordBean.getGmtCreate();
        mTaskRecordBean.setGmtCreate(gmtCreate.substring(0,gmtCreate.lastIndexOf(":")));

        initStepView();
        //getData();
       mPtr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
               /*
                 *检查是否可以刷新，这里使用默认的PtrHandler进行判断
                 */
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                /*
                 * 在刷新前需要准备什么工作
                 **/
                getData();
            }
        });

    }

    private void initStepView() {

        mStepView = new VerticalStepView(this);
        mDetailList.add(0, mTaskRecordBean);
        mStepView.setStepsViewIndicatorComplectingPosition(mDetailList.size() - 1)//设置完成的步数
                .reverseDraw(true)//default is true
                .setStepViewTexts(mDetailList)//总步骤
                .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.appColor))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.textGrey666))//设置StepsView text完成字体的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.uncompleted_text_color));//设置StepsView text未完成字体的颜色
        mFl.removeAllViews();
        mFl.addView(mStepView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {

        RxBus.getInstance().unSubscribe(this);
        super.onDestroy();
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(RESULT_OK);
                finish();
                break;

        }
    }

    public void getData() {

        mDetailList.clear();
        RetrofitClient.getInstance().getRecordInfo(mRecordId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RecordInfoBean>(this) {
                    @Override
                    public void onNext(RecordInfoBean recordInfoBean) {
                        RecordInfoBean.BodyBean body = recordInfoBean.getBody();
                        ArrayList<RecordInfoBean.BodyBean.DetailListBean> detailList = (ArrayList<RecordInfoBean.BodyBean.DetailListBean>) body.getDetailList();
                        RecordInfoBean.BodyBean.DetailListBean detailListBean = detailList.get(0);
                        detailListBean.setPlaceName(detailListBean.getUserName() + "接单");
                        detailListBean.setDetailTime(detailListBean.getDetailTime());

                        RecordInfoBean.BodyBean.DetailListBean detailListBean1 = detailList.get(1);
                        detailListBean1.setPlaceName(detailListBean1.getUserName() + "取单");
                        detailListBean.setDetailTime(detailListBean.getDetailTime());

                        RecordInfoBean.BodyBean.DetailListBean lastDetailBean = detailList.get(detailList.size() - 1);

                        RecordInfoBean.BodyBean.DetailListBean stateBean = new RecordInfoBean.BodyBean.DetailListBean();

                        String detailTime = lastDetailBean.getDetailTime();

                        stateBean.setDetailTime(detailTime.substring(0,detailTime.lastIndexOf(":")));

                        switch (lastDetailBean.getState()) {
                            case 1:
                                stateBean.setPlaceName("等待取单");
                                break;
                            case 2:
                                stateBean.setPlaceName("护工运送中");
                                break;
                            case 3:                 //正在运送中
                                stateBean.setPlaceName("护工运送中");
                                break;
                            case 4:                 //运送完成
                                stateBean.setPlaceName("运送完成");
                                break;
                            default:
                                stateBean.setPlaceName("等待接单");
                                break;
                        }

                        String dealTime = null;
                        for (int i = 0; i < detailList.size(); i++) {
                            RecordInfoBean.BodyBean.DetailListBean detailListBean3 = detailList.get(i);
                            String[] split = detailListBean3.getDetailTime().split(" ");
                            String date = split[0];
                            String hour = split[1];
                            String[] split1 = hour.split(":");
                            String timeSecond = split1[0] + ":" + split1[1];
                            if (!date.equals(dealTime)) {
                                detailListBean3.setDetailTime(date + " " + timeSecond);
                            } else {
                                detailListBean3.setDetailTime(timeSecond);
                            }
                            dealTime=date;
                        }
                        stateBean.setType(lastDetailBean.getType());
                        detailList.add(stateBean);

                        mDetailList.addAll(detailList);


                        initStepView();



                    }

                    @Override
                    public void onError(Throwable e) {
                        mPtr.refreshComplete();
                        super.onError(e);
                    }

                    @Override
                    public void onCompleted() {
                        mPtr.refreshComplete();
                        super.onCompleted();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
