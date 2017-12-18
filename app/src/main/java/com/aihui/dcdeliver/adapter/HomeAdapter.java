package com.aihui.dcdeliver.adapter;

import android.view.ViewGroup;

import com.aihui.dcdeliver.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HomeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_type, item);
        helper.setText(R.id.tv_time, item);
        helper.setText(R.id.tv_dlwz_before, item);
        helper.setText(R.id.tv_dlwz_after, item);
        helper.setText(R.id.tv_bz, item);

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

