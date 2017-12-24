package com.aihui.dcdeliver.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.base.AppActivity;
import com.aihui.dcdeliver.bean.RecordInfoBean;
import com.aihui.dcdeliver.commponent.stepview.VerticalStepView;
import com.aihui.dcdeliver.commponent.stepview.bean.StepBean;
import com.aihui.dcdeliver.rxbus.RxBus;
import com.aihui.dcdeliver.rxbus.event.BlueEvent;
import com.aihui.dcdeliver.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class TransStateActivity extends AppActivity {


    @BindView(R.id.iv_back)
    ImageView        mIvBack;
    @BindView(R.id.step_view)
    VerticalStepView mStepView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_trans_state;
    }


    @Override
    protected void initData() {
        Subscription subscribe = RxBus.getInstance()
                .toObservable(BlueEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BlueEvent>() {
                    @Override
                    public void call(BlueEvent blueEvent) {
                        ToastUtil.showToast(blueEvent.getLocation());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        RxBus.getInstance().addSubscription(this, subscribe);

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        List<StepBean> detailList = (List<StepBean>) intent.getSerializableExtra("detailList");
        RecordInfoBean.BodyBean.TaskRecordBean taskRecordBean = (RecordInfoBean.BodyBean.TaskRecordBean) intent.getSerializableExtra("taskFirst");
        detailList.set(0, taskRecordBean);
        mStepView.setStepsViewIndicatorComplectingPosition(detailList.size() - 1)//设置完成的步数
                .reverseDraw(true)//default is true
                .setStepViewTexts(detailList)//总步骤
                .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.appColor))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.textGrey666))//设置StepsView text完成字体的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.uncompleted_text_color));//设置StepsView text未完成字体的颜色

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        RxBus.getInstance().unSubscribe(this);

    }
}
