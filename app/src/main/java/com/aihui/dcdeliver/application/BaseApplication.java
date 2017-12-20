package com.aihui.dcdeliver.application;

import android.app.Application;
import android.content.Context;

import com.aihui.dcdeliver.cachemanager.AddCookiesInterceptor;
import com.aihui.dcdeliver.cachemanager.ReceivedCookiesInterceptor;
import com.aihui.dcdeliver.exception.LocalFileHandler;
import com.aihui.dcdeliver.util.LogUtil;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class BaseApplication extends Application {


    //定义一个全局上下文(dialog中不可以使用)
    public static Context sContext;
    public static boolean isActive;
    public static String SCOOKIE = null;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        LogUtil.isDebug = true;


        //配置程序异常退出处理
        Thread.setDefaultUncaughtExceptionHandler(new LocalFileHandler(this));

        //配置自适应
        AutoLayoutConifg.getInstance().useDeviceSize().init(this);

    }


    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        super.onTerminate();
    }

    public static OkHttpClient defaultOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                //接收Cookie
                .addInterceptor(new ReceivedCookiesInterceptor(sContext))
                //添加Cookie
                .addInterceptor(new AddCookiesInterceptor(sContext))
                .build();
        return client;
    }

}
