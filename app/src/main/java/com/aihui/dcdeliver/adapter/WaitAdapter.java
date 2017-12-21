package com.aihui.dcdeliver.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.application.BaseApplication;
import com.aihui.dcdeliver.bean.RecordListBean;
import com.aihui.dcdeliver.bean.ServiceBean;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.aihui.dcdeliver.rxbus.RxBus;
import com.aihui.dcdeliver.rxbus.event.ReceiveEvent;
import com.aihui.dcdeliver.util.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WaitAdapter extends BaseQuickAdapter<RecordListBean.BodyBean.ListBean, BaseViewHolder> {
    public WaitAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final RecordListBean.BodyBean.ListBean item) {

        helper.setText(R.id.tv_type, item.getStatusText());
        helper.setText(R.id.tv_time, item.getDeadline());
        helper.setText(R.id.tv_dlwz_before, item.getEndPlaceName());
        helper.setText(R.id.tv_dlwz_after, item.getStartPlaceName());
        helper.setTag(R.id.tv_task_class,item.getTaskClassName());

        String remark = item.getRemark();
        if (TextUtils.isEmpty(remark)){
            helper.setVisible(R.id.tv_bz,false);
        }else{
            helper.setText(R.id.tv_bz, remark);
        }
        helper.setOnClickListener(R.id.bt_receive, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().receiveRecord(item.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new BaseSubscriber<ServiceBean>(BaseApplication.sContext) {
                            @Override
                            public void onNext(ServiceBean bean) {
                                ToastUtil.showToast("接收成功");
                                RxBus.getInstance().post(new ReceiveEvent());
                            }
                        });
            }
        });

       // helper.setImageResource(R.id.icon, item.getImageResource());
        // 加载网络图片

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = super.onCreateViewHolder(parent, viewType);
        AutoUtils.autoSize(baseViewHolder.getConvertView());
        return baseViewHolder;
    }
}

