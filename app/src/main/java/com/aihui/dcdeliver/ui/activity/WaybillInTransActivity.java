package com.aihui.dcdeliver.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.application.BaseApplication;
import com.aihui.dcdeliver.base.AppActivity;
import com.aihui.dcdeliver.base.Content;
import com.aihui.dcdeliver.bean.RecordInfoBean;
import com.aihui.dcdeliver.bean.ServiceBean;
import com.aihui.dcdeliver.commponent.CircleImageView;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.aihui.dcdeliver.rxbus.RxBus;
import com.aihui.dcdeliver.rxbus.event.ReceiveEvent;
import com.aihui.dcdeliver.service.BlueService;
import com.aihui.dcdeliver.util.AlertUtil;
import com.aihui.dcdeliver.util.SPUtil;
import com.aihui.dcdeliver.util.ToastUtil;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

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

    @BindView(R.id.tv_sqr)
    TextView mTvSqr;
    @BindView(R.id.cv_one)
    CardView mCvOne;

    @BindView(R.id.tv_remark)
    TextView mTvRemark;

    @BindView(R.id.rv_task)
    RecyclerView mRvTask;

    @BindView(R.id.fl)
    FrameLayout mFl;

    @BindView(R.id.ll_remark)
    LinearLayout mLlRemark;


    @BindView(R.id.rl_cancel_recive)
    RelativeLayout         mRlCancel;
    @BindView(R.id.iv_back)
    ImageView              mIvBack;
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAvi;
    @BindView(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout  mPtr;


    private int mRecordId = -1;
    private RecordInfoBean.BodyBean mBodyBean;

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
        } else if (RECEIVE_RECORD.equals(type)) {
        } else {
            mFl.setVisibility(View.GONE);
        }
        mPtr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                /**
                 *检查是否可以刷新，这里使用默认的PtrHandler进行判断
                 */
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                /**
                 * 在刷新前需要准备什么工作
                 */
                getData();
            }
        });
        getData();

    }

    private void getData() {
        RetrofitClient.getInstance().getRecordInfo(mRecordId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RecordInfoBean>(this) {
                    @Override
                    public void onNext(RecordInfoBean recordInfoBean) {

                        mBodyBean = recordInfoBean.getBody();
                        RecordInfoBean.BodyBean.TaskRecordBean taskRecord = mBodyBean.getTaskRecord();
                        String[] split = taskRecord.getDeadline().split(" ")[1].split(":");

                        mTvSqr.setText(taskRecord.getCreateUserName());

                        mTvTime.setText(split[0] + " " + split[1]);
                        mTvTaskType.setText(taskRecord.getTaskClassName());
                        mTvStartArea.setText(taskRecord.getStartPlaceName());
                        mTvEndArea.setText(taskRecord.getEndPlaceName());
                        mTvArriveTime.setText(taskRecord.getDeadline());

                        if (taskRecord.getStatus() == 1) {
                            mRlCancel.setVisibility(View.GONE);
                            mBtReceive.setVisibility(View.VISIBLE);
                            mTvState.setText(taskRecord.getStatusText());
                        } else {

                            mTvState.setText(SPUtil.getUserName(WaybillInTransActivity.this) + " " + taskRecord.getStatusText());
                        }

                        if (taskRecord.getStatus() == 2) {
                            mRlCancel.setVisibility(View.VISIBLE);
                            mBtReceive.setVisibility(View.GONE);
                        }


                        mTvTransNum.setText(taskRecord.getRecordNum());

                        if (TextUtils.isEmpty(taskRecord.getRemark())) {
                            mLlRemark.setVisibility(View.GONE);
                            mTvRemark.setVisibility(View.GONE);
                        } else {
                            mTvRemark.setText(taskRecord.getRemark());
                        }
                        List<RecordInfoBean.BodyBean.ExtListBean> extList = mBodyBean.getExtList();
                        if (extList.size() != 0) {
                            mRvTask.setVisibility(View.VISIBLE);
                            mRvTask.setLayoutManager(new LinearLayoutManager(WaybillInTransActivity.this));
                            mRvTask.setAdapter(new CommonAdapter<RecordInfoBean.BodyBean.ExtListBean>(WaybillInTransActivity.this, R.layout.item_text_text, extList) {
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
                        if (taskRecord.getStatus() >= 3) {         //可点击看详情
                            mRlYs.setClickable(true);
                            mRlYs.setEnabled(true);
                            mIvDetail.setVisibility(View.VISIBLE);
                            mRlCancel.setVisibility(View.GONE);
                            mBtReceive.setVisibility(View.GONE);
                        } else {
                            mRlYs.setClickable(false);
                            mRlYs.setEnabled(false);
                            mIvDetail.setVisibility(View.GONE);
                        }
                        mPtr.refreshComplete();
                        mAvi.hide();
                    }
                });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Content.STATE_REQUEST_CODE) {
            initData();
        }

    }

    @OnClick({R.id.tv_cancel, R.id.bt_receive, R.id.tv_sure, R.id.rl_ys, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:

                new AlertUtil().showDialog(WaybillInTransActivity.this, "确认取消吗?", new AlertUtil.onBackResult() {
                            @Override
                            public void backResult() {
                                if (mRecordId != -1) {
                                    RetrofitClient.getInstance().cancelReceive(mRecordId)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new BaseSubscriber<ServiceBean>(WaybillInTransActivity.this) {
                                                @Override
                                                public void onNext(ServiceBean bean) {
                                                    mAvi.setVisibility(View.VISIBLE);
                                                    mAvi.show();
                                                    ToastUtil.showToast("取消成功");
                                                    RxBus.getInstance().post(new ReceiveEvent());
                                                    initData();
                                                }
                                            });
                                }
                            }});
                break;
            case R.id.bt_receive:
                new AlertUtil().showDialog(WaybillInTransActivity.this, "确认接收吗?", new AlertUtil.onBackResult() {
                    @Override
                    public void backResult() {

                        if (mRecordId != -1) {
                            RetrofitClient.getInstance().receiveRecord(mRecordId)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread()).
                                    subscribe(new BaseSubscriber<ServiceBean>(BaseApplication.sContext) {
                                        @Override
                                        public void onNext(ServiceBean bean) {
                                            ToastUtil.showToast("接收成功");
                                            mAvi.setVisibility(View.VISIBLE);
                                            mAvi.show();
                                            RxBus.getInstance().post(new ReceiveEvent());
                                            initData();
                                            mRlCancel.setVisibility(View.VISIBLE);
                                            mBtReceive.setVisibility(View.GONE);
                                        }
                                    });
                        }
                    }
                });
                break;

            case R.id.tv_sure:
                new AlertUtil().showDialog(WaybillInTransActivity.this, "是否确认?", new AlertUtil.onBackResult() {
                    @Override
                    public void backResult() {
                        if (mRecordId != -1) {
                            RetrofitClient.getInstance().startRecord(mRecordId)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new BaseSubscriber<ServiceBean>(WaybillInTransActivity.this) {
                                        @Override
                                        public void onNext(ServiceBean bean) {
                                            ToastUtil.showToast("确认成功");
                                            Intent it = new Intent().setClass(WaybillInTransActivity.this, BlueService.class);
                                            startService(it);
                                            //bindService(it, mConnection, BIND_AUTO_CREATE);
                                            mAvi.setVisibility(View.VISIBLE);
                                            mAvi.show();
                                            RxBus.getInstance().post(new ReceiveEvent());
                                            initData();
                                        }
                                    });
                        }

                    }
                });


                break;
            case R.id.rl_ys:
                Intent intent = new Intent(this, TransStateActivity.class);
                ArrayList<RecordInfoBean.BodyBean.DetailListBean> detailList = (ArrayList<RecordInfoBean.BodyBean.DetailListBean>) mBodyBean.getDetailList();
                RecordInfoBean.BodyBean.DetailListBean detailListBean = detailList.get(0);
                detailListBean.setPlaceName(detailListBean.getUserName() + "接单");
                detailListBean.setDetailTime(detailListBean.getDetailTime());


                RecordInfoBean.BodyBean.DetailListBean detailListBean1 = detailList.get(1);
                detailListBean1.setPlaceName(detailListBean1.getUserName() + "取单");
                detailListBean.setDetailTime(detailListBean.getDetailTime());


                RecordInfoBean.BodyBean.DetailListBean lastDetailBean = detailList.get(detailList.size() - 1);

                RecordInfoBean.BodyBean.DetailListBean stateBean = new RecordInfoBean.BodyBean.DetailListBean();

                String detailTime = lastDetailBean.getDetailTime();

                stateBean.setDetailTime(detailTime.substring(0, detailTime.lastIndexOf(":")));

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
                    dealTime = date;
                }
                stateBean.setType(lastDetailBean.getType());
                detailList.add(stateBean);

                intent.putExtra("recordId", mRecordId);
                intent.putExtra("detailList", detailList);
                intent.putExtra("taskFirst", mBodyBean.getTaskRecord());
                startActivityForResult(intent, Content.STATE_REQUEST_CODE);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

  /*  private BlueService.MyBinder myBinder;
    private BlueService          mService;

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
    };*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
