package com.aihui.dcdeliver.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.util.LogUtil;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public abstract  class BaseSubscriber<T> extends Subscriber<T> {
    protected Context mContext;

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
            if (exception.isCookieExpried()) {
                //处理token失效对应的逻辑 重新登录 重新登录后 还是在原来的页面 然后重新提交下数据就好了
              //  SPUtil.saveString(mContext, Content.IS_LOGIN, TimeUtils.getCurTimeString(mFormat));
              //  Intent intent = new Intent(mContext, LoginActivity.class);
              //  mContext.startActivity(intent);
                //    ActivityManager.getInstance().finishActivity(MainActivity.class);

            } else {
               Toast.makeText(mContext,"失败"+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } 
    }



}

