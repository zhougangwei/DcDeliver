package com.aihui.dcdeliver.bean;

import org.altbeacon.beacon.Beacon;

/**
 * @ 创建者   zhou
 * @ 创建时间   2018/1/3 18:25
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2018/1/3$
 * @ 更新描述  ${TODO}
 */

public class BeaconBean extends Beacon {
    int type = 1;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public BeaconBean(Beacon otherBeacon) {
        super(otherBeacon);
    }


    public void setDistance(){
          mDistance=null;
    }

}
