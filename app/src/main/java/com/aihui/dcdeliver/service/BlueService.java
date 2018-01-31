package com.aihui.dcdeliver.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.aihui.dcdeliver.util.ToastUtil;
import com.daoyixun.location.ipsmap.IpsNavigation;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.RequestBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class BlueService extends Service implements BeaconConsumer, RangeNotifier {
    private static final long DEFAULT_BACKGROUND_SCAN_PERIOD         = 5000L;
    private static final long DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD = 5000L;

    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private Subscription mSubscribe;
    private IpsNavigation ipsNavigation;

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

        initBlueTooth();


        super.onCreate();
    }

    private void initBlueTooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //注册蓝牙广播
        registBluetoothAction();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBtIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            this.getApplicationContext().startActivity(enableBtIntent);
        }
    }



    private void initBeacon() {
        beaconManager.setBackgroundScanPeriod(DEFAULT_BACKGROUND_SCAN_PERIOD);
        beaconManager.setBackgroundBetweenScanPeriod(DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD);
        beaconManager.bind(this);


    }

    //蓝牙广播拦截器
    private void registBluetoothAction(){
        IntentFilter action_found = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, action_found);
    }



    //创建广播
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    ToastUtil.showToast("扫描成功,请点击连接!");
            }     else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                ToastUtil.showToast("扫描是啊比11111!");
            }
        }
    };




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
            beaconBean.setDistance();
            beacons.add(beaconBean);
        }

        gotoUPdata(beacons);

    }

    private void gotoUPdata(final List<Beacon> beacons) {
        if (beacons==null||beacons.size()==0){
            return;
        }
        //GsonUtil.parseListToJson(beacons)

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/application/json; charset=utf-8"), GsonUtil.parseListToJson(beacons));
        RetrofitClient.getInstance().transporting(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PlaceBackBean>(getApplicationContext()) {
                            @Override
                            public void onNext(PlaceBackBean bean) {
                                if (!"0".equals(bean.getBody())) {
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
