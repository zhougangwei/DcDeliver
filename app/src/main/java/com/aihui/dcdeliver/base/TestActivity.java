package com.aihui.dcdeliver.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.aihui.dcdeliver.R;
import com.aihui.dcdeliver.service.BeaconService;

import org.altbeacon.beacon.Beacon;

import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2018/1/3 13:50
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2018/1/3$
 * @ 更新描述  ${TODO}
 */

public class TestActivity extends AppActivity {


    private BeaconBroadcastReceiver beaconBroadcastReceiver  ;
    private static final String TAG           = "MainActivity";
    public static final  String BEACON_ACTION = "com.juju.beacontest.beacon.action";


    @Override
    protected int getContentViewId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        beaconBroadcastReceiver = new BeaconBroadcastReceiver();
        Intent intent = new Intent(TestActivity.this, BeaconService.class);
        startService(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(beaconBroadcastReceiver, getBeaconIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (beaconBroadcastReceiver != null)
            unregisterReceiver(beaconBroadcastReceiver);
    }
    IntentFilter getBeaconIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BEACON_ACTION);
        return intentFilter;
    }
     class BeaconBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BEACON_ACTION.equals(action)) {
                List<Beacon> beacons = intent.getParcelableArrayListExtra("beacon");
                for (Beacon beacon : beacons) {
                    Log.i(TAG, "onReceive: " + beacon.getBluetoothAddress()
                            +"    onDistance"+beacon.getDistance()
                    );
                }
            }
        }
    }

}
