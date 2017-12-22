package com.aihui.dcdeliver.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.ui.activity.MainActivity;
import com.aihui.dcdeliver.util.LogUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

public class BlueService extends Service {
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
            Observable.interval(2, 2, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
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
                    LogUtil.e("Next.");
                }
            });
            // 执行具体的下载任务
        }

    }
}
