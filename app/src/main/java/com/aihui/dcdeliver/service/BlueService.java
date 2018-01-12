package com.aihui.dcdeliver.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteException;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.bean.BeaconBean;
import com.aihui.dcdeliver.bean.PlaceBackBean;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.aihui.dcdeliver.rxbus.RxBus;
import com.aihui.dcdeliver.ui.activity.MainActivity;
import com.aihui.dcdeliver.util.GsonUtil;
import com.aihui.dcdeliver.util.LogUtil;
import com.aihui.dcdeliver.util.ToastUtil;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BlueService extends Service implements BeaconConsumer, RangeNotifier {
    private static final long DEFAULT_BACKGROUND_SCAN_PERIOD         = 5000L;
    private static final long DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD = 5000L;

    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private Subscription mSubscribe;

    public BlueService() {
    }


    //   private MyBinder mBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // flags = START_STICKY;
      /*  if (mSubscribe== null) {
            mSubscribe = RxBus.getInstance()
                    .toObservable(BlueEvent.class)
                    .observeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            LogUtil.d("MyBinder", "11111");
                        }
                    })
                    .flatMap(new Func1<BlueEvent, Observable<PlaceBackBean>>() {
                        @Override
                        public Observable<PlaceBackBean> call(BlueEvent blueEvent) {
                            //
                            try {
                                return RetrofitClient.getInstance().transporting(GsonUtil.parseListToJson(blueEvent.beacons));
                            }catch (Exception e){
                                return Observable.error(e);
                            }
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new BaseSubscriber<PlaceBackBean>(getApplicationContext()) {
                                @Override
                                public void onNext(PlaceBackBean bean) {
                                    ToastUtil.showToast(GsonUtil.parseObjectToJson(bean));
                                    LogUtil.d("MyBinder", GsonUtil.parseObjectToJson(bean));
                                   *//* if (!bean.getBody()) {
                                        mSubscribe.unsubscribe();
                                        stopSelf();
                                    }*//*
                                }
                                @Override
                                public void onError(Throwable e) {
                                    super.onError(e);
                                }
                            });
            RxBus.getInstance().addSubscription(this, mSubscribe);
        }*/
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

        initBeacon();
        beaconManager.bind(this);

        super.onCreate();
    }

    private void initBeacon() {
        beaconManager.setBackgroundScanPeriod(DEFAULT_BACKGROUND_SCAN_PERIOD);
        beaconManager.setBackgroundBetweenScanPeriod(DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD);
    }

    @Override
    public void onDestroy() {
        if (beaconManager != null)
            beaconManager.removeRangeNotifier(this);
        RxBus.getInstance().unSubscribe(this);  //注销
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通 知
        super.onDestroy();
    }

    @Override
    public void onBeaconServiceConnect() {
        Region region = new Region("myRangingUniqueId", null, null, null);
        beaconManager.addRangeNotifier(this);
        try {
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> collections, Region region) {
        List beacons = new ArrayList<>();
        for (Beacon beacon : collections) {
            BeaconBean beaconBean = new BeaconBean(beacon);
            beaconBean.setType(1);
            beacons.add(beaconBean);
        }
        /*Intent intent = new Intent(TestActivity.BEACON_ACTION);
        intent.putParcelableArrayListExtra("beacon", (ArrayList<? extends Parcelable>) beacons);//因为Beacon继承了Parcelable,
        sendBroadcast(intent);  */                                                                 // 所以能通过这个方式来传递数据
       /* RxBus.getInstance()
                .post(new BlueEvent(beacons));*/

        gotoUPdata(beacons);

        /*Subscription subscribe = RxBus.getInstance()
                .toObservable(BlueEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BlueEvent>() {
                    @Override
                    public void call(BlueEvent blueEvent) {
                        ToastUtil.showToast(blueEvent.getLocation());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        RxBus.getInstance().addSubscription(this, subscribe);*/


    }

    private void gotoUPdata(final List<Beacon> beacons) {


        LogUtil.d("MyBinder1", GsonUtil.parseListToJson(beacons));

        Observable.just(beacons)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Beacon>>() {
                    @Override
                    public void call(List<Beacon> beacons) {
                        ToastUtil.showToast(GsonUtil.parseObjectToJson(beacons));
                    }
                });


        //GsonUtil.parseListToJson(beacons)

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/application/json; charset=utf-8"), GsonUtil.parseListToJson(beacons));
        RetrofitClient.getInstance().transporting(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PlaceBackBean>(getApplicationContext()) {
                            @Override
                            public void onNext(PlaceBackBean bean) {

                               // LogUtil.d("MyBinder", GsonUtil.parseObjectToJson(bean));
                                if (!bean.getBody()) {
                                   // mSubscribe.unsubscribe();
                                         stopSelf();
                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                            }
                        });
    }


/*
    public class MyBinder extends Binder {
        public BlueService GetService() {
            return BlueService.this;
        }

        public void startSendLocation() {
            //两秒发送一次
            *//**//*mSubscription = Observable.interval(2, 2, TimeUnit.SECONDS)
                    .subscribe(new Subscriber<Long>() {
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

                        }
                    });*//**//*
            // 执行具体的下载任务
        }

    }*/
}
