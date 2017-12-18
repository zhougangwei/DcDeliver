package com.aihui.dcdeliver.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/7/12 16:08
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/7/12$
 * @ 更新描述  ${TODO}
 */

public class InspectPagerAdapter extends PagerAdapter {

    List<FrameLayout> mViewList;
    String[] mTitleStrings = new String[]{};
    Context            mContext;


    public InspectPagerAdapter( String[] titleStrings, List<FrameLayout> datas, Context context) {
        mViewList = datas;
        this.mTitleStrings = titleStrings;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mViewList == null ? 0 : mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));//添加页卡
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));//删除页卡
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleStrings[position];//页卡标题
    }

}
