package com.aihui.dcdeliver.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.bean.RecordListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

public class WaitAdapter extends BaseQuickAdapter<RecordListBean.BodyBean.ListBean, BaseViewHolder> {

    public boolean mIsShowRecieve;
    public WaitAdapter(int layoutResId, List data, boolean isShowReceive) {
        super(layoutResId, data);
        mIsShowRecieve = isShowReceive;
    }

    @Override
    protected void convert(BaseViewHolder helper, final RecordListBean.BodyBean.ListBean item) {

        helper.setText(R.id.tv_type, item.getStatusText());
        if(item.getStatus()==1){
            helper.setBackgroundRes(R.id.tv_type,R.drawable.shape_icon_sqz);
        }else if(item.getStatus()==2){
            helper.setBackgroundRes(R.id.tv_type,R.drawable.shape_icon_yjs);
        }else if (item.getStatus()==3){
            helper.setBackgroundRes(R.id.tv_type,R.drawable.shape_icon_zzjx);
        }

        helper.addOnClickListener(R.id.bt_receive);

        helper.setText(R.id.tv_time, item.getDeadline());
        helper.setText(R.id.tv_dlwz_before, item.getEndPlaceName());
        helper.setText(R.id.tv_dlwz_after, item.getStartPlaceName());
        helper.setText(R.id.tv_task_class,item.getTaskClassName());

        String remark = item.getRemark();
        if (TextUtils.isEmpty(remark)){
            helper.getView(R.id.tv_bz).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.tv_bz).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_bz, remark);
        }
        /*
        *
        * 是否显示接受按钮
        * */
        if (mIsShowRecieve){
            helper.getView(R.id.bt_receive).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.bt_receive).setVisibility(View.GONE);
        }

       // helper.setImageResource(R.id.icon, item.getImageResource());
        // 加载网络图片

    }

    public void setIsShowRecieve (boolean isShowRecieve){
      this.mIsShowRecieve  = isShowRecieve;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = super.onCreateViewHolder(parent, viewType);
        AutoUtils.autoSize(baseViewHolder.getConvertView());
        return baseViewHolder;
    }
}

