package com.aihui.dcdeliver.bean;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.aihui.dcdeliver.R;
import com.blankj.utilcode.utils.SizeUtils;
import com.fynn.fluidlayout.FluidLayout;

import java.util.List;


/**
 * @ 创建者   zhou
 * @ 创建时间   2017/7/6 15:46
 * @ 描述    ${底部弹出的dialog}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/7/6$
 * @ 更新描述  ${TODO}
 */

public class CancelBottomDialog extends Dialog {

    Context mContext;

    public CancelBottomDialog(Context context) {
        super(context, R.style.bottom_dialog);
        this.mContext = context;

    }

    public CancelBottomDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public CancelBottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public interface onBackResult {
        void backResult(String cancelReason);
    }


    public void showDialog(List<String> mDatas, final onBackResult result) {

        final CancelBottomDialog dialog = new CancelBottomDialog(mContext, R.style.bottom_dialog);
        View inflate = View.inflate(mContext, R.layout.dialog_cancel_reason, null);


        Button btSubmit = inflate.findViewById(R.id.bt_submit);
        FluidLayout fluidLayout = inflate.findViewById(R.id.fluid_layout);


     /*
       RecyclerView mRv = inflate.findViewById(R.id.rv);
      mRv.setBackgroundColor(Color.WHITE);
        int spanCount = 2;
        StaggeredGridLayoutManager flowLayoutManager = new StaggeredGridLayoutManager(
                spanCount,
                StaggeredGridLayoutManager.HORIZONTAL
        );

        mRv.setLayoutManager(flowLayoutManager);*/

     /*   if (mDatas.size() > 8) {
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mRv.setLayoutParams(layoutParams);
        } else {
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mRv.setLayoutParams(layoutParams);
        }

        mRv.setAdapter(new CommonAdapter<String>(mContext, R.layout.item_text_mid, mDatas) {
            @Override
            protected void convert(ViewHolder holder, final String cancelReason, int position) {
                TextView textView = holder.getView(R.id.tv_name);
                textView.setEnabled(false);
                textView.setText(cancelReason);
                holder.setOnClickListener(R.id.tv_name, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        result.backResult(cancelReason);
                    }
                });

            }
        });*/

        genTag(fluidLayout,mDatas,true);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
      //  params.height = (int) (ScreenUtils.getScreenHeight(mContext) * 0.45);
        // params.height = (int) (ScreenUtils.getScreenHeight(mContext) *0.6);
        //params.height = WindowManager.LayoutParams.WRAP_CONTENT;
              //  (int) (ScreenUtils.getScreenHeight(mContext) *0.8);
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();

       /* Window window2 = dialog.getWindow();
        WindowManager.LayoutParams lp = window2.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;//宽高可设置具体大小
        lp.height = (int) (ScreenUtils.getScreenHeight(mContext) *0.3);
        dialog.getWindow().setAttributes(lp);*/


    }

    private void genTag(FluidLayout fluidLayout, List<String> mDatas, boolean hasBg) {
        fluidLayout.removeAllViews();
        fluidLayout.setGravity(Gravity.CENTER);
        for (int i = 0; i < mDatas.size(); i++) {
            TextView tv = new TextView(mContext);
            tv.setText(mDatas.get(i));
            tv.setEnabled(false);
            tv.setBackground(mContext.getResources().getDrawable(R.drawable.text_selector));

            int eighteenDp = SizeUtils.dp2px(mContext, 13);
            tv.setTextSize(SizeUtils.sp2px(mContext,7));
            tv.setPadding(eighteenDp,eighteenDp,eighteenDp,eighteenDp);

            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(eighteenDp, 0, 0, eighteenDp);
            fluidLayout.addView(tv, params);
        }
    }


}
