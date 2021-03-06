package com.aihui.dcdeliver.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.aihui.dcdeliver.BuildConfig;
import com.aihui.dcdeliver.cachemanager.AddCookiesInterceptor;
import com.aihui.dcdeliver.cachemanager.ReceivedCookiesInterceptor;
import com.aihui.dcdeliver.service.ServiceFactory;
import com.aihui.dcdeliver.util.LogUtil;
import com.aihui.dcdeliver.util.ProperTies;
import com.daoyixun.ipsmap.IpsMapSDK;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.aihui.dcdeliver.service.ServiceFactory.BLUE_THOOTH;
import static com.aihui.dcdeliver.service.ServiceFactory.DYX_THOOTH;

public class BaseApplication extends Application implements BootstrapNotifier{


    //定义一个全局上下文(dialog中不可以使用)
    public static Context sContext;
    public static boolean isActive;
    public static String SCOOKIE = null;
    private RegionBootstrap mRegionBootstrap;
    private BackgroundPowerSaver mBackgroundPowerSaver;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        LogUtil.isDebug = true;
        //配置程序异常退出处理
      //  Thread.setDefaultUncaughtExceptionHandler(new LocalFileHandler(this));
        //配置自适应
        AutoLayoutConifg.getInstance().useDeviceSize().init(this);

    //    IpsMapSDK.init(sContext, Constants.IPSMAP_APP_KEY);

              //  定制配置信息 ,使用微信分享功能请实现相关的接口
/*   IpsMapSDK.init(new IpsMapSDK().Configuration.Builder(sContext)
                .appKey(Constants.IPSMAP_APP_KEY)
                //正式版请关闭 默认是关闭的
                .debug(false)
                .build());*/



      /*  mBackgroundPowerSaver = new BackgroundPowerSaver(this);
        IpsLocationSDK.init(new IpsLocationSDK.Configuration.Builder(this)
                .appKey(Constants.IPSMAP_APP_KEY)
                .debug(false)
                .build());*/



        Properties proper = ProperTies.getProperties(getApplicationContext());
        String serviceUrl = proper.getProperty("serverUrl");
        String serviceType = proper.getProperty("serviceType");
        switch (serviceType) {
            case BLUE_THOOTH:
                intBeaCon();
                ServiceFactory.setCurrentType(BLUE_THOOTH);
                break;
            case   DYX_THOOTH:
                initDyx();
                ServiceFactory.setCurrentType(DYX_THOOTH);
                break;
        }


    }

    private void intBeaCon() {
        BeaconManager beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        Region region = new Region("all-region-beacon",null,null,null);
        mRegionBootstrap = new RegionBootstrap(this,region);
    }

    private void initDyx() {
        IpsMapSDK.init(new IpsMapSDK.Configuration.Builder(sContext)
                .appKey(Constants.IPSMAP_APP_KEY)
                .debug(BuildConfig.DEBUG)
                .build());
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

    @Override
    public void didEnterRegion(Region region) {

    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }

    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
