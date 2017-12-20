package com.aihui.dcdeliver.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.base.AppActivity;
import com.aihui.dcdeliver.bean.ServiceBean;
import com.aihui.dcdeliver.bean.TaskDetailBean;
import com.aihui.dcdeliver.bean.TaskTypeBean;
import com.aihui.dcdeliver.bean.TaskTypeBeanList;
import com.aihui.dcdeliver.commponent.jdaddressselector.ISelectAble;
import com.aihui.dcdeliver.commponent.jdaddressselector.SelectDecotor;
import com.aihui.dcdeliver.commponent.jdaddressselector.selectAbleDataManager;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.MyService;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.aihui.dcdeliver.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewWayBillActivity extends AppActivity {


    @BindView(R.id.tv_title)
    TextView       mTvTitle;
    @BindView(R.id.tv_submit)
    TextView       mTvSubmit;
    @BindView(R.id.ll_title)
    RelativeLayout mLlTitle;
    @BindView(R.id.tv_waybill_num)
    TextView       mTvWaybillNum;
    @BindView(R.id.tv_task_type)
    TextView       mTvTaskType;
    @BindView(R.id.ll_task_type)
    LinearLayout   mLlTaskType;
    @BindView(R.id.tv_ysr)
    TextView       mTvYsr;
    @BindView(R.id.ll_ysr)
    LinearLayout   mLlYsr;
    @BindView(R.id.tv_ysrq)
    TextView       mTvYsrq;
    @BindView(R.id.ll_ysrq)
    LinearLayout   mLlYsrq;
    @BindView(R.id.tv_ysqy)
    TextView       mTvYsqy;
    @BindView(R.id.ll_ysqy)
    LinearLayout   mLlYsqy;
    @BindView(R.id.tv_jsqy)
    TextView       mTvJsqy;
    @BindView(R.id.ll_jsqy)
    LinearLayout   mLlJsqy;
    @BindView(R.id.tv_ssqy)
    TextView       mTvSsqy;
    @BindView(R.id.ll_ssqy)
    LinearLayout   mLlSsqy;
    @BindView(R.id.tv_bz)
    TextView       mTvBz;
    @BindView(R.id.ll_bz)
    LinearLayout   mLlBz;
    private SelectDecotor mTaskDecotor;

    //接收的地理位子
    private SelectDecotor mDlwzAccetDector;

    //输送的地理位子
    private SelectDecotor mDlwzOutDector;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_new_waybill;
    }

    @Override
    protected void initData() {

        RetrofitClient.getRetrofit()
                .create(MyService.class)
                .getTasksList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<TaskTypeBeanList>(NewWayBillActivity.this) {
                    @Override
                    public void onNext(TaskTypeBeanList bean) {
                        mTaskDecotor = new SelectDecotor(NewWayBillActivity.this, bean, TaskTypeBean.class);
                        mTaskDecotor.setOnBackSelectListener(new SelectDecotor.OnBackSelectListener() {
                            @Override
                            public void onBackSelect(ArrayList<ISelectAble> selectAbles) {
                                selectAbleDataManager manger = new selectAbleDataManager();
                                manger.solveDatas(selectAbles);
                                mTvTaskType.setText(manger.getLongName());
                                RetrofitClient.getRetrofit().create(MyService.class)
                                        .getTaskDetail(manger.getShortIds()).subscribeOn(Schedulers.io())
                                        .subscribe(new BaseSubscriber<ServiceBean>(NewWayBillActivity.this) {
                                            @Override
                                            public void onNext(ServiceBean bean) {
                                                Map<String, Object> body = (Map<String, Object>) bean.getBody();
                                                String s = GsonUtil.parseMapToJson(body);
                                                TaskDetailBean taskDetailBean = GsonUtil.parseJsonToBean(s, TaskDetailBean.class);
                                                List<TaskDetailBean.ExtListBean> extList = taskDetailBean.getExtList();


                                            }
                                        });
                            }
                        });
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
        RetrofitClient.getRetrofit()
                .create(MyService.class)
                .getPlaceMap()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ServiceBean>(NewWayBillActivity.this) {
                    @Override
                    public void onNext(ServiceBean bean) {
                       Map<String, List<ISelectAble>> body = (Map<String, List<ISelectAble>>)bean.getBody();
                        mDlwzOutDector = new SelectDecotor(NewWayBillActivity.this, body, TaskTypeBean.class);
                        mDlwzOutDector.setOnBackSelectListener(new SelectDecotor.OnBackSelectListener() {
                            @Override
                            public void onBackSelect(ArrayList<ISelectAble> selectAbles) {
                                selectAbleDataManager manger = new selectAbleDataManager();
                                manger.solveDatas(selectAbles);
                                mTvSsqy.setText(manger.getLongName());
                            }
                        });

                        mDlwzAccetDector = new SelectDecotor(NewWayBillActivity.this, body, TaskTypeBean.class);
                        mDlwzAccetDector.setOnBackSelectListener(new SelectDecotor.OnBackSelectListener() {
                            @Override
                            public void onBackSelect(ArrayList<ISelectAble> selectAbles) {
                                selectAbleDataManager manger = new selectAbleDataManager();
                                manger.solveDatas(selectAbles);
                                mTvJsqy.setText(manger.getLongName());
                            }
                        });

                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });

    }

    @Override
    protected void initView() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_task_type, R.id.ll_ysr, R.id.ll_ysrq, R.id.ll_ysqy, R.id.ll_jsqy, R.id.ll_ssqy, R.id.ll_bz,R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //任务类型
            case R.id.ll_task_type:
                mTaskDecotor.showDialog();
                break;
            case R.id.ll_ysr:
                break;
            case R.id.ll_ysrq:
                break;
            //运送区域
            case R.id.ll_ysqy:
                mDlwzOutDector.showDialog();
                break;
            //接收区域
            case R.id.ll_jsqy:
                mDlwzAccetDector.showDialog();
                break;
            case R.id.ll_ssqy:
                break;
            case R.id.ll_bz:
                break;
            //上传
            case R.id.tv_submit:
              // RetrofitClient.getInstance().saveTaskRecord()
                break;
        }
    }
}
