package com.aihui.dcdeliver.base;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.application.ActivityManager;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.aihui.dcdeliver.application.BaseApplication.isActive;

/**
 * Created by renlei on 2016/5/23.
 */
public abstract class AppActivity extends BaseActivity {

    private static final String  LAYOUT_LINEARLAYOUT   = "LinearLayout";
    private static final String  LAYOUT_FRAMELAYOUT    = "FrameLayout";
    private static final String  LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    public static final  Integer BAR_CODE              = 1;
    Unbinder mUnbinder;

    public static final SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        mUnbinder = ButterKnife.bind(this);

        if (null != getIntent()) {
            handleIntent(getIntent());
        }
        addColorStatus();
        ActivityManager.getInstance().addActivity(this);

        initView();
        initData();


    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();
        if (!isActive) {
            //app 从后台唤醒，进入前台
            isActive = true;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            //app 进入后台
            isActive = false;
            //全局变量isActive = false 记录当前已经进入后台
        /*    if (IsNeedRestart) {
                SophixManager.getInstance().killProcessSafely();
            }*/
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mUnbinder = null;

        ActivityManager.getInstance().finishActivity(this);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

    }

  /*  //获取第一个fragment*/
  /*  protected abstract BaseFragment getFirstFragment();*/

    //获取Intent
    protected void handleIntent(Intent intent) {

    }

    /**
     * 初始化视图
     */
    protected abstract void initView();

    //沉浸式
    private void setImmersionStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            //			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    /*@Override
    protected int getFragmentContentId() {
        return 0;
    }*/

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }

        if (view != null)
            return view;

        return super.onCreateView(name, context, attrs);
    }


    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        android.app.ActivityManager activityManager = (android.app.ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    public void gotoActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


}
