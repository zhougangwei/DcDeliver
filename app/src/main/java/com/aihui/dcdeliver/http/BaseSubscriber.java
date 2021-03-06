package com.aihui.dcdeliver.http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.application.ActivityManager;
import com.aihui.dcdeliver.service.ServiceFactory;
import com.aihui.dcdeliver.ui.activity.LoginActivity;
import com.aihui.dcdeliver.ui.activity.MainActivity;
import com.aihui.dcdeliver.util.LogUtil;
import com.aihui.dcdeliver.util.SPUtil;
import com.blankj.utilcode.utils.TimeUtils;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public abstract class BaseSubscriber<T> extends Subscriber<T> {
    protected     Context mContext;
    public static long    currentTime;       //记录当前时间  短期内 不准 重复跳转

    public BaseSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(final Throwable e) {
        LogUtil.e(Thread.currentThread().getName());
        Log.w("Subscriber onError", e);
        if (e instanceof HttpException) {
            // We had non-2XX http error
            Toast.makeText(mContext, mContext.getString(R.string.server_internal_error), Toast.LENGTH_SHORT).show();
        } else if (e instanceof IOException) {
            // A network or conversion error happened
            Toast.makeText(mContext, mContext.getString(R.string.cannot_connected_server), Toast.LENGTH_SHORT).show();
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            try {
                if (exception.isCookieExpried()) {
                    if(mContext.getClass() != LoginActivity.class&&( TimeUtils.getCurTimeMills()-currentTime)>500){
                        currentTime = TimeUtils.getCurTimeMills();
                        Toast.makeText(mContext, "登录过期", Toast.LENGTH_SHORT).show();
                        //处理token失效对应的逻辑 重新登录 重新登录后 还是在原来的页面 然后重新提交下数据就好了
                        SPUtil.clear(mContext);

                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.putExtra("where",1);
                        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                         mContext.startActivity(intent);
                         ActivityManager.getInstance().finishActivity(MainActivity.class);
                        Intent serviceIntent = new Intent(mContext, ServiceFactory.getSeviceClass());
                         mContext.stopService(serviceIntent);
                    }
                    //    ActivityManager.getInstance().finishActivity(MainActivity.class);
                } else {
                    Toast.makeText(mContext, "失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


}

