package deliver.aihui.com.dcdeliver.application;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.TimeUnit;

import deliver.aihui.com.dcdeliver.exception.LocalFileHandler;
import deliver.aihui.com.dcdeliver.util.LogUtil;
import okhttp3.OkHttpClient;

public class BaseApplication extends Application {


    //定义一个全局上下文(dialog中不可以使用)
    public static Context sContext;
    public static boolean isActive;


    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        LogUtil.isDebug = false;
        //配置程序异常退出处理
        Thread.setDefaultUncaughtExceptionHandler(new LocalFileHandler(this));
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
                .build();
        return client;
    }

}
