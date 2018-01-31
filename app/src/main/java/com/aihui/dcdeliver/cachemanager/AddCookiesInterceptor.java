package com.aihui.dcdeliver.cachemanager;

import android.content.Context;

import com.aihui.dcdeliver.util.SPUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;

public class AddCookiesInterceptor implements Interceptor {
    private Context context;

    public AddCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();
        String cookie = SPUtil.getString(context, "cookie", "");
        Observable.just(cookie)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String cookie) {
                        //添加cookie
                        builder.addHeader("Cookie", cookie);
                        try {
                            String tokenId = cookie.split("sid=")[1];
                            builder.addHeader("tokenId",tokenId );
                        }catch (Exception e){
                            builder.addHeader("tokenId",cookie );
                        }
                    }
                });
        return chain.proceed(builder.build());
    }
}