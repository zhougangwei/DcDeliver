package com.aihui.dcdeliver.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.commponent.stepview.VerticalStepView;
import com.aihui.dcdeliver.commponent.stepview.bean.StepBean;

public class TransStateActivity extends AppCompatActivity {


    private VerticalStepView mStepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_state);
        ButterKnife.bind(this);
        mStepView = (VerticalStepView) findViewById(R.id.step_view);

        List<StepBean> list0 = new ArrayList<>();


        list0.add(new StepBean("接已提交定案，等待系统确认", "1999-08-09"));
        list0.add(new StepBean("您的商品需要从外地调拨，我们会尽快处理，请耐心等待", "1999-08-09"));
        list0.add(new StepBean("您的订单已经进入亚洲第一仓储中心1号库准备出库", "1999-08-09"));
        list0.add(new StepBean("打包成功", "1999-08-09"));
        list0.add(new StepBean("配送员【包牙齿】已出发，好多礼物哦", "1999-08-09"));
        list0.add(new StepBean("感谢你在京东购物，欢迎你下次光临！", "1999-08-09"));
        mStepView.setStepsViewIndicatorComplectingPosition(list0.size() - 2)//设置完成的步数
                .reverseDraw(true)//default is true
                .setStepViewTexts(list0)//总步骤
                .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.appColor))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.textGrey666))//设置StepsView text完成字体的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.uncompleted_text_color));//设置StepsView text未完成字体的颜色

    }
}
