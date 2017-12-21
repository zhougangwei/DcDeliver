package com.aihui.dcdeliver.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.base.AppActivity;
import com.aihui.dcdeliver.bean.RecordInfoBean;
import com.aihui.dcdeliver.bean.ServiceBean;
import com.aihui.dcdeliver.commponent.CircleImageView;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.aihui.dcdeliver.util.SPUtil;
import com.aihui.dcdeliver.util.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.aihui.dcdeliver.ui.fragment.HomeFragment.RECEIVE_RECORD;
import static com.aihui.dcdeliver.ui.fragment.HomeFragment.WAIT_RECORD;

public class WaybillInTransActivity extends AppActivity {


    @BindView(R.id.ll_title)
    LinearLayout    mLlTitle;
    @BindView(R.id.iv_cycle)
    CircleImageView mIvCycle;
    @BindView(R.id.tv_time)
    TextView        mTvTime;
    @BindView(R.id.tv_state)
    TextView        mTvState;
    @BindView(R.id.iv_detail)
    ImageView       mIvDetail;
    @BindView(R.id.rl_ys)
    RelativeLayout  mRlYs;
    @BindView(R.id.tv_cancel)
    TextView        mTvCancel;
    @BindView(R.id.tv_sure)
    TextView        mTvSure;
    @BindView(R.id.bt_receive)
    Button          mBtReceive;
    @BindView(R.id.iv_shadow)
    ImageView       mIvShadow;
    @BindView(R.id.tv_taskType)
    TextView        mTvTaskType;
    @BindView(R.id.tv_arrive_time)
    TextView        mTvArriveTime;
    @BindView(R.id.tv_trans_num)
    TextView        mTvTransNum;
    @BindView(R.id.tv_start_area)
    TextView        mTvStartArea;
    @BindView(R.id.tv_end_area)
    TextView        mTvEndArea;
    @BindView(R.id.tv_get_way)
    TextView        mTvGetWay;
    @BindView(R.id.cv_one)
    CardView        mCvOne;

    @BindView(R.id.rv_task)
    RecyclerView mRvTask;


    @BindView(R.id.rl_cancel_recive)
    RelativeLayout        mRlCancel;
    private int mRecordId = -1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_waybill_in_trans;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mRecordId = intent.getIntExtra("recordId", -1);
        if (mRecordId == -1) {
            return;
        }
        String type = intent.getStringExtra("type");
        if (WAIT_RECORD.equals(type)) {
            mRlCancel.setVisibility(View.GONE);
            mBtReceive.setVisibility(View.VISIBLE);
        } else if (RECEIVE_RECORD.equals(type)) {
            mRlCancel.setVisibility(View.VISIBLE);
            mBtReceive.setVisibility(View.GONE);
        }
        RetrofitClient.getInstance().getRecordInfo(mRecordId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RecordInfoBean>(this) {
                    @Override
                    public void onNext(RecordInfoBean recordInfoBean) {
                        RecordInfoBean.BodyBean.TaskRecordBean taskRecord = recordInfoBean.getBody().getTaskRecord();
                        String[] split = taskRecord.getDeadline().split(" ")[1].split(":");
                        mTvTime.setText(split[0] + ":" + split[1]);
                        mTvTaskType.setText(taskRecord.getTaskClassName());
                        mTvStartArea.setText(taskRecord.getStartPlaceName());
                        mTvEndArea.setText(taskRecord.getEndPlaceName());
                        mTvArriveTime.setText(taskRecord.getDeadline());
                        mTvState.setText(SPUtil.getUserName(WaybillInTransActivity.this) + " " + taskRecord.getStatusText());

                        List<RecordInfoBean.BodyBean.ExtListBean> extList = recordInfoBean.getBody().getExtList();
                        if (extList.size() != 0) {
                            mRvTask.setVisibility(View.VISIBLE);
                            mRvTask.setLayoutManager(new LinearLayoutManager(WaybillInTransActivity.this));
                            mRvTask.setAdapter(new CommonAdapter<RecordInfoBean.BodyBean.ExtListBean>(WaybillInTransActivity.this, R.layout.item_text_edit, extList) {
                                @Override
                                protected void convert(final ViewHolder holder, final RecordInfoBean.BodyBean.ExtListBean bean, int position) {
                                    holder.setText(R.id.tv_name, bean.getColName());
                                    final TextView tvValue = (TextView) holder.getView(R.id.tv_value);
                                    if (!TextUtils.isEmpty(bean.getColValue())) {
                                        tvValue.setText(bean.getColValue());
                                    } else {
                                        tvValue.setText("");
                                    }
                                }
                            });
                        } else {
                            mRvTask.setVisibility(View.GONE);
                        }

                        if (taskRecord.getStatus()>=3){         //可点击看详情
                            mRlYs.setClickable(true);
                            mRlYs.setEnabled(true);
                        }else{
                            mRlYs.setClickable(false);
                            mRlYs.setEnabled(false);
                        }
                    }
                });

    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.tv_cancel, R.id.tv_sure ,R.id.rl_ys})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:

                break;
            case R.id.tv_sure:
                if (mRecordId != -1) {
                    RetrofitClient.getInstance().startRecord(mRecordId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseSubscriber<ServiceBean>(this) {
                                @Override
                                public void onNext(ServiceBean bean) {
                                    ToastUtil.showToast("成功");
                                }
                            });
                }
                break;
            case R.id.rl_ys:
                Intent intent = new Intent(this,TransStateActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
