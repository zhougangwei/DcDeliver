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

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.application.Constants;
import com.aihui.dcdeliver.base.Content;
import com.aihui.dcdeliver.bean.PlaceBackBean;
import com.aihui.dcdeliver.http.BaseSubscriber;
import com.aihui.dcdeliver.http.RetrofitClient;
import com.aihui.dcdeliver.rxbus.RxBus;
import com.aihui.dcdeliver.ui.activity.MainActivity;
import com.aihui.dcdeliver.util.GsonUtil;
import com.aihui.dcdeliver.util.LogUtil;
import com.aihui.dcdeliver.util.SPUtil;
import com.aihui.dcdeliver.util.ToastUtil;
import com.daoyixun.location.ipsmap.IpsNavigation;
import com.daoyixun.location.ipsmap.UserToTargetLocationListener;
import com.daoyixun.location.ipsmap.model.bean.InitNavErrorException;
import com.daoyixun.location.ipsmap.model.bean.UserToTargetData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.aihui.dcdeliver.application.BaseApplication.sContext;

public class DyxService extends Service {


    private IpsNavigation ipsNavigation;
    private Subscription mSubscribe;
    private String mRecord;

    public DyxService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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

        mRecord = SPUtil.getString(this, Content.HAS_NEXT_RECORD, "0");



        mSubscribe = Observable.interval(2, 2, TimeUnit.SECONDS)
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
                        gotoUPdata(mRecord);
                    }
                });


        // initDyxService();
        initBlueTooth();

        initIpsNavigation();

        super.onCreate();
    }




   /* private void initDyxService() {
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

                                    List<Object> objects = new ArrayList<>();
                                    objects.add(ipsLocation);

                                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/application/json; charset=utf-8"), GsonUtil.parseListToJson(objects));

                                    GsonUtil.parseObjectToJson(ipsLocation);
                                    RetrofitClient.getInstance().transporting(body)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(Schedulers.io())
                                            .subscribe(new BaseSubscriber<PlaceBackBean>(sContext) {
                                                @Override
                                                public void onNext(PlaceBackBean placeBackBean) {
                                                    if ("0".equals(placeBackBean.getBody())) {
                                                        ipsClient.stop();
                                                    } else {
                                                        gotoUPdata(placeBackBean.getBody());
                                                    }
                                                }
                                                @Override
                                                public void onError(Throwable e) {
                                                    super.onError(e);
                                                }
                                            });
                                }
                            }
                        });
            }
        });
        //是否在Map内
        ipsClient.start();
        // 执行具体的下载任务
    }*/

    ;

    private void initBlueTooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //注册蓝牙广播
        //registBluetoothAction();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBtIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            this.getApplicationContext().startActivity(enableBtIntent);
        }
    }

    private void initIpsNavigation() {

        ipsNavigation = new IpsNavigation(getBaseContext(), Constants.IPSMAP_MAP_ID);
        ipsNavigation.registerUserToTargetLocationListener(new UserToTargetLocationListener() {
            @Override
            public void onError(InitNavErrorException errorException) {
                LogUtil.e("ddddd", "error " + errorException.toString());
            }
        });

    }


    //蓝牙广播拦截器
    private void registBluetoothAction() {
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
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                ToastUtil.showToast("扫描是啊比11111!");
            }
        }
    };


    @Override
    public void onDestroy() {

        if(mSubscribe!=null){
           mSubscribe.unsubscribe();
        }
        RxBus.getInstance().unSubscribe(this);  //注销
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通 知
        super.onDestroy();
    }


    private void gotoUPdata(String body) {

        if ("0".equals(body)){
            mSubscribe.unsubscribe();
            stopSelf();
            return;
        }
        List<String> strings = Arrays.asList(body.split(","));
        ArrayList targetIdList = new ArrayList<>();
        targetIdList.addAll(strings);
        ipsNavigation.setTargetId(targetIdList);

        ArrayList<UserToTargetData> userToTargetDataList = ipsNavigation.startRouting();
        if (userToTargetDataList != null) {
            Observable.from(userToTargetDataList)
                    .filter(new Func1<UserToTargetData, Boolean>() {
                        @Override
                        public Boolean call(UserToTargetData userToTargetData) {
                            LogUtil.e("dddd", userToTargetData.toString());
                            return userToTargetData!=null;
                        }
                    })
                    .observeOn(Schedulers.io())
                    .subscribe(new Action1<UserToTargetData>() {
                        @Override
                        public void call(UserToTargetData userToTargetData) {
                            boolean success = userToTargetData.isSuccess();
                            String content = null;
                            if (success) {
                                List<Object> objects = new ArrayList<>();
                                objects.add(userToTargetData);
                                RequestBody dataBody = RequestBody.create(okhttp3.MediaType.parse("application/application/json; charset=utf-8"), GsonUtil.parseListToJson(objects));
                                RetrofitClient.getInstance().transporting(dataBody)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(Schedulers.io())
                                        .subscribe(new BaseSubscriber<PlaceBackBean>(sContext) {
                                            @Override
                                            public void onNext(PlaceBackBean placeBackBean) {
                                                if ("0".equals(placeBackBean.getBody())) {
                                                    mSubscribe.unsubscribe();
                                                    stopSelf();
                                                }else{
                                                    mRecord=placeBackBean.getBody();
                                                }
                                            }
                                            @Override
                                            public void onError(Throwable e) {
                                                super.onError(e);
                                            }
                                        });

                                String cont = "目的地:" + userToTargetData.getTarget() + " 距离 " + userToTargetData.getToTargetDistance() + "楼层:"
                                        + userToTargetData.getTargetFloor() +
                                        "location " + userToTargetData.getNearLocationRegionName() +
                                        "\r\n";
                                content += cont;
                                LogUtil.e(content);
                            } else {
                                String cont =  "   " + "flase " + "  " + userToTargetData.getErrorMessage() + "\r\n";
                                content += cont;
                                LogUtil.e(content);
                            }
                        }
                    });
        }
    }


}
