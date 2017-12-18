package com.aihui.dcdeliver.http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.ui.activity.LoginActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class BaseSubscriber<T> extends Subscriber<T> {
    protected Context mContext;

    public BaseSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(final Throwable e) {
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
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } 
    }

    @Override
    public void onNext(T t) {

    }

}

