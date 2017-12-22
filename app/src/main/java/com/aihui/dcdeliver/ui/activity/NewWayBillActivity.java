package com.aihui.dcdeliver.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.base.AppActivity;
import com.aihui.dcdeliver.bean.PlaceBean;
import com.aihui.dcdeliver.bean.SaveBean;
import com.aihui.dcdeliver.bean.ServiceBean;
import com.aihui.dcdeliver.bean.TaskDetailBean;
import com.aihui.dcdeliver.bean.TaskTypeBeanList;
import com.aihui.dcdeliver.commponent.jdaddressselector.ISelectAble;
import com.aihui.dcdeliver.commponent.jdaddressselector.SelectAbleDataHelper;
import com.aihui.dcdeliver.commponent.jdaddressselector.SelectDecotor;
import com.aihui.dcdeliver.commponent.jdaddressselector.SelectDecotorDl;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.MyService;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.aihui.dcdeliver.util.GsonUtil;
import com.aihui.dcdeliver.util.ToastUtil;
import com.bigkoo.pickerview.OptionsPickerView;
import com.blankj.utilcode.utils.TimeUtils;
import com.google.gson.JsonObject;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
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
    @BindView(R.id.tv_ysgj)
    TextView       mTvYsgj;
    @BindView(R.id.ll_ysgj)
    LinearLayout   mLlYsgj;
    @BindView(R.id.tv_bz)
    TextView       mTvBz;
    @BindView(R.id.ll_bz)
    LinearLayout   mLlBz;

    @BindView(R.id.rv_detail)
    RecyclerView mRvDetail;
    private SelectDecotor mTaskDecotor;

    //接收的地理位子
    private SelectDecotorDl mDlwzAccetDector;

    //输送的地理位子
    private SelectDecotorDl mDlwzOutDector;
    private SaveBean.TaskRecordBean mTaskRecordBean;
    private SaveBean mSaveBean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_new_waybill;
    }

    @Override
    protected void initData() {

        mSaveBean = new SaveBean();
        mTaskRecordBean = new SaveBean.TaskRecordBean();
        mSaveBean.setTaskRecord(mTaskRecordBean);
        //默认的
        mTaskRecordBean.setRecordFrom(2);

        RetrofitClient.getRetrofit()
                .create(MyService.class)
                .getTasksMap()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<TaskTypeBeanList>(NewWayBillActivity.this) {
                    @Override
                    public void onNext(TaskTypeBeanList bean) {

                        //初始化任务选择器
                        mTaskDecotor = new SelectDecotor(NewWayBillActivity.this, bean.getBody());
                        mTaskDecotor.setOnBackSelectListener(new SelectDecotor.OnBackSelectListener() {
                            @Override
                            public void onBackSelect(ArrayList<ISelectAble> selectAbles) {
                                SelectAbleDataHelper manger = new SelectAbleDataHelper();
                                manger.solveDatas(selectAbles);
                                //回传数据 回显数据
                                mTvTaskType.setText(manger.getLongName());
                                mTaskRecordBean.setTaskClassId(manger.getShortIds());
                                mTaskRecordBean.setTaskClassName(manger.getShortName());

                                RetrofitClient.getRetrofit().create(MyService.class)
                                        .getTaskDetail(manger.getShortIds()).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new BaseSubscriber<TaskDetailBean>(NewWayBillActivity.this) {
                                            @Override
                                            public void onNext(TaskDetailBean bean) {
                                                //显示明细数据 同时回显接收区域
                                                List<TaskDetailBean.BodyBean.ExtListBean> extList = bean.getBody().getExtList();
                                                mSaveBean.setExtList(extList);
                                                if (extList.size() != 0) {
                                                    mRvDetail.setVisibility(View.VISIBLE);
                                                    mRvDetail.setLayoutManager(new LinearLayoutManager(NewWayBillActivity.this));
                                                    mRvDetail.setAdapter(new CommonAdapter<TaskDetailBean.BodyBean.ExtListBean>(NewWayBillActivity.this, R.layout.item_text_edit, extList) {
                                                        @Override
                                                        protected void convert(final ViewHolder holder, final TaskDetailBean.BodyBean.ExtListBean bean, int position) {
                                                            holder.setText(R.id.tv_name, bean.getColName());
                                                            final EditText et = (EditText) holder.getView(R.id.et_input);

                                                            if (!TextUtils.isEmpty(bean.getColValue())) {
                                                                et.setText(bean.getColValue());
                                                            } else {
                                                                et.setText("");
                                                            }
                                                            //实时保存 不然ListView不断的被复用数据会错误
                                                            et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                                @Override
                                                                public void onFocusChange(View v, boolean hasFocus) {
                                                                    if (!hasFocus) {
                                                                        Editable text = et.getText();
                                                                        if (!TextUtils.isEmpty(text)) {
                                                                            bean.setColValue(text.toString());
                                                                        } else {
                                                                            bean.setColValue("");       //变空是为了防止ListView的复用错误
                                                                        }
                                                                    }
                                                                }
                                                            });

                                                        }
                                                    });
                                                } else {
                                                    mRvDetail.setVisibility(View.GONE);
                                                }
                                                List<TaskDetailBean.BodyBean.PlaceListBean> placeList = bean.getBody().getPlaceList();
                                                if (placeList != null && placeList.size() > 0) {
                                                    mTvJsqy.setText(placeList.get(0).getPlaceName());
                                                    mTaskRecordBean.setEndPlaceId(placeList.get(0).getPlaceId()+"");
                                                    mTaskRecordBean.setEndPlaceName(placeList.get(0).getPlaceName());
                                                }

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
                .subscribe(new BaseSubscriber<Map<String, Object>>(NewWayBillActivity.this) {
                    @Override
                    public void onNext(Map<String, Object> bean) {
                        //初始化地址选择器
                        mDlwzOutDector = new SelectDecotorDl(NewWayBillActivity.this, (JsonObject) bean.get("body"), PlaceBean.class);
                        mDlwzOutDector.setOnBackSelectListener(new SelectDecotorDl.OnBackSelectListener() {
                            @Override
                            public void onBackSelect(ArrayList<ISelectAble> selectAbles) {
                                SelectAbleDataHelper manger = new SelectAbleDataHelper();
                                manger.solveDatas(selectAbles);
                                mTaskRecordBean.setStartPlaceId(manger.getShortIds());
                                mTaskRecordBean.setStartPlaceName(manger.getLongName());
                                mTvYsqy.setText(manger.getLongName());
                            }
                        });

                        mDlwzAccetDector = new SelectDecotorDl(NewWayBillActivity.this, (JsonObject) bean.get("body"), PlaceBean.class);
                        mDlwzAccetDector.setOnBackSelectListener(new SelectDecotorDl.OnBackSelectListener() {
                            @Override
                            public void onBackSelect(ArrayList<ISelectAble> selectAbles) {
                                SelectAbleDataHelper manger = new SelectAbleDataHelper();
                                manger.solveDatas(selectAbles);
                                mTaskRecordBean.setEndPlaceId(manger.getShortIds());
                                mTaskRecordBean.setEndPlaceName(manger.getLongName());
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

    @OnClick({R.id.ll_task_type,  R.id.ll_ysrq, R.id.ll_ysqy, R.id.ll_jsqy,  R.id.ll_bz, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //任务类型
            case R.id.ll_task_type:
                mTaskDecotor.showDialog();
                break;
            //运送日期
            case R.id.ll_ysrq:
                gotoYsrq();
                break;
            //运送区域
            case R.id.ll_ysqy:
                mDlwzOutDector.showDialog();
                break;
            //接收区域
            case R.id.ll_jsqy:
                mDlwzAccetDector.showDialog();
                break;
            case R.id.ll_bz:
                break;
            //上传
            case R.id.tv_submit:
                gotoSubmit();
                break;
        }
    }

    private void gotoSubmit() {

        mTvSubmit.requestFocus();
        if (TextUtils.isEmpty(mTvYsrq.getText().toString())) {
            ToastUtil.showToast("运送时间必填");
            return;
        }
        if (TextUtils.isEmpty(mTvYsqy.getText().toString())) {
            ToastUtil.showToast("运送区域必填");
            return;
        }
        if (TextUtils.isEmpty(mTvJsqy.getText().toString())) {
            ToastUtil.showToast("接收区域必填");
            return;
        }
        if(TimeUtils.getCurTimeMills()>TimeUtils.string2Milliseconds(mTvYsrq.getText().toString())){
            mTvYsrq.setTextColor(getResources().getColor(R.color.red));
            ToastUtil.showToast("运送时间不能小于当前时间");
            return;
        }else{
            mTvYsrq.setTextColor(getResources().getColor(R.color.textGrey666));
        }
        if (TextUtils.isEmpty(mTvTaskType.getText().toString())) {
            ToastUtil.showToast("任务类型必填");
            return;
        }

       mTaskRecordBean.setRemark(mTvBz.getText().toString());
       mTaskRecordBean.setRecordNum(mTvWaybillNum.getText().toString());
        String s = GsonUtil.parseObjectToJson(mSaveBean);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),s);
        RetrofitClient.getInstance().saveTaskRecord(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ServiceBean>(this) {
            @Override
            public void onNext(ServiceBean bean) {
                Log.d("NewWayBillActivity", GsonUtil.parseObjectToJson(bean));
            }
        });


    }

    private void gotoYsr() {
      /*  new Intent(this,);
     startActivity();*/
    }

    private void gotoYsrq() {

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       /* TimePickerDialog build = new TimePickerDialog.Builder()
                .setCurrentMillseconds(TimeUtils.string2Milliseconds(mTvYsrq.getText().toString() == null ? TimeUtils.getCurTimeString(format) : mTvYsrq.getText().toString(), format))
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(15)
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        mTaskRecordBean.setDeadline(TimeUtils.milliseconds2String(millseconds, format));
                        mTvYsrq.setText(TimeUtils.milliseconds2String(millseconds));
                        if(TimeUtils.getCurTimeMills()>millseconds){
                            mTvYsrq.setTextColor(getResources().getColor(R.color.red));
                            ToastUtil.showToast("运送时间不能小于当前时间");
                        }else{
                            mTvYsrq.setTextColor(getResources().getColor(R.color.textGrey666));
                        }
                    }
                })
                .build();
        build.show(getSupportFragmentManager(), "1");*/


        getNoLinkData();

        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = day.get(options1) +" "
                        + hours.get(option2)+":"
                        + minutes.get(options3);
                mTvYsrq.setText(tx);
            }
        }).build();
        pvOptions.setNPicker(day, hours, minutes);
        pvOptions.show();


    }
    private ArrayList<String> day     = new ArrayList<>();
    private ArrayList<String> hours   = new ArrayList<>();
    private ArrayList<String> minutes = new ArrayList<>();

    private void getNoLinkData() {
        day.add("今天");
        day.add("明天");
        day.add("后天");
        day.add("三天后");
        day.add("四天后");
        day.add("五天后");



        hours.add("01");
        hours.add("02");
        hours.add("03");
        hours.add("04");
        hours.add("05");
        hours.add("06");
        hours.add("07");
        hours.add("08");
        hours.add("09");
        hours.add("10");
        hours.add("11");
        hours.add("12");

        for (int i = 0; i <60 ; i++) {
            if (i<10){
                minutes.add("0"+i);
            }else{
                minutes.add(i+"");
            }
        }
    }
}
