package com.aihui.dcdeliver.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.bean.PlaceBackBean;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.aihui.dcdeliver.rxbus.RxBus;
import com.aihui.dcdeliver.rxbus.event.BlueEvent;
import com.aihui.dcdeliver.ui.activity.MainActivity;
import com.aihui.dcdeliver.util.GsonUtil;
import com.aihui.dcdeliver.util.LogUtil;
import com.blankj.utilcode.utils.TimeUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BlueService extends Service {

    private Subscription mSubscription;

    public BlueService() {
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
        public BlueService GetService() {
            return BlueService.this;
        }
        public void startSendLocation() {
            //两秒发送一次
            mSubscription = Observable.interval(2, 2, TimeUnit.SECONDS)
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

                            RetrofitClient.getInstance().transporting("D000000"+ new Random().nextInt(12))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(Schedulers.io())
                                    .subscribe(new Action1<PlaceBackBean>() {
                                        @Override
                                        public void call(PlaceBackBean bean) {
                                            LogUtil.d("MyBinder", GsonUtil.parseObjectToJson(bean));
                                            if (!bean.getBody()) {
                                                mSubscription.unsubscribe();
                                               // stopSelf();
                                            }
                                            RxBus.getInstance().post(new BlueEvent(TimeUtils.getCurTimeString()));
                                        }
                                    });
                        }
                    });
            // 执行具体的下载任务
        }

    }
}
