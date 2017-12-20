package com.aihui.dcdeliver.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AutoTextView extends TextView {
    public AutoTextView(Context context) {
        super(context);
    }

    public AutoTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTextSize(float size) {
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) getLayoutParams();
            setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
        } else if (params instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) getLayoutParams();
            setLayoutParams(new RelativeLayout.LayoutParams(layoutParams));
        } else if (params instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) getLayoutParams();
            setLayoutParams(new FrameLayout.LayoutParams(layoutParams));
        }
        setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }
}
