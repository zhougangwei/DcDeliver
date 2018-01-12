package com.aihui.dcdeliver.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;

import com.aihui.dcdeliver.base.TestActivity;
import com.aihui.dcdeliver.rxbus.RxBus;
import com.aihui.dcdeliver.rxbus.event.BlueEvent;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeaconService extends Service implements BeaconConsumer, RangeNotifier {
  
    private static final long DEFAULT_BACKGROUND_SCAN_PERIOD = 5000L;
    private static final long DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD = 5000L;
  
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
  
    public BeaconService() {  
  
    }  
  
    @Override  
    public void onCreate() {  
        super.onCreate();  
        initBeacon();  
        beaconManager.bind(this);  
    }  
  
    private void initBeacon() {  
        beaconManager.setBackgroundScanPeriod(DEFAULT_BACKGROUND_SCAN_PERIOD);  
        beaconManager.setBackgroundBetweenScanPeriod(DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD);  
    }  
  
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
        if (beaconManager != null)  
            beaconManager.removeRangeNotifier(this);  
    }  
    @Override  
    public IBinder onBind(Intent intent) {
        return null;  
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
        List<Beacon> beacons = new ArrayList<>();
        for (Beacon beacon : collections) {  
            beacons.add(beacon);  
        }  
        Intent intent = new Intent(TestActivity.BEACON_ACTION);
        intent.putParcelableArrayListExtra("beacon", (ArrayList<? extends Parcelable>) beacons);//因为Beacon继承了Parcelable,
        sendBroadcast(intent);                                                                   // 所以能通过这个方式来传递数据  
        RxBus.getInstance().post(new BlueEvent(beacons));

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
}  