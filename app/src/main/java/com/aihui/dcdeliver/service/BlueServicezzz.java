package com.aihui.dcdeliver.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.application.Constants;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.ui.activity.MainActivity;
import com.aihui.dcdeliver.util.GsonUtil;
import com.aihui.dcdeliver.util.LogUtil;
import com.aihui.dcdeliver.util.ToastUtil;
import com.daoyixun.location.ipsmap.IpsClient;
import com.daoyixun.location.ipsmap.IpsLocation;
import com.daoyixun.location.ipsmap.IpsLocationListener;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.aihui.dcdeliver.application.BaseApplication.sContext;

public class BlueServicezzz extends Service {


    private Subscription mSubscription;

    public BlueServicezzz() {
    }

    private MyBinder mBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器
        Intent nfIntent = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_checkbox))
                .setContentTitle("正在同步地理位子")
                .setContentText("正在同步地理位子");

        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        startForeground(110, notification);// 开始前台服务
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        public BlueServicezzz GetService() {
            return BlueServicezzz.this;
        }

        public void startSendLocation() {

            final IpsClient ipsClient = new IpsClient(sContext, Constants.IPSMAP_MAP_ID);
            ipsClient.registerLocationListener(new IpsLocationListener() {
                @Override
                public void onReceiveLocation(IpsLocation ipsLocation) {
                    if (ipsLocation == null) {
                        return;
                    }
                    Observable.just(ipsLocation)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .filter(new Func1<IpsLocation, Boolean>() {
                                @Override
                                public Boolean call(IpsLocation ipsLocation) {
                                    if (ipsLocation == null) {
                                        ToastUtil.showToast("定位失败!");
                                        //定位失败;
                                        return false;
                                    }
                                    return true;
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(new Action1<IpsLocation>() {
                                @Override
                                public void call(IpsLocation ipsLocation) {
                                    ToastUtil.showToast(GsonUtil.parseObjectToJson(ipsLocation));
                                }
                            }).observeOn(Schedulers.io())
                            .subscribe(new Subscriber<IpsLocation>() {
                                @Override
                                public void onCompleted() {

                                }
                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(IpsLocation ipsLocation) {
                                    if (ipsLocation.isInThisMap()) {
                                       /* RetrofitClient.getInstance().transporting(GsonUtil.parseObjectToJson(ipsLocation))
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(Schedulers.io())
                                                .subscribe(new BaseSubscriber<PlaceBackBean>(sContext) {
                                                    @Override
                                                    public void onNext(PlaceBackBean placeBackBean) {

                                                        LogUtil.d("MyBinder", GsonUtil.parseObjectToJson(placeBackBean));
                                                        if (!placeBackBean.getBody()) {
                                                            mSubscription.unsubscribe();
                                                            // stopSelf();
                                                            ipsClient.stop();
                                                        }
                                                        //两秒发送一次
                                                        //RxBus.getInstance().post(new BlueEvent(TimeUtils.getCurTimeString()));
                                                    }
                                                    @Override
                                                    public void onError(Throwable e) {
                                                        super.onError(e);
                                                    }
                                                });*/
                                    }
                                }
                            });
                }
            });
            //是否在Map内


            mSubscription = Observable.interval(2, 2, TimeUnit.SECONDS)
                    .observeOn(Schedulers.io())
                    .subscribe(new BaseSubscriber<Long>(getApplicationContext()) {
                        @Override
                        public void onCompleted() {
                            LogUtil.e("Sequence complete.");
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.e("error.");
                        }

                        @Override
                        public void onNext(Long aLong) {
                            //两秒发送一次
                            ipsClient.start();
                        }
                    });
            // 执行具体的下载任务
        }

    }
}
