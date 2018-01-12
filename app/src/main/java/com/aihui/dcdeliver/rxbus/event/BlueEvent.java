package com.aihui.dcdeliver.rxbus.event;

import org.altbeacon.beacon.Beacon;

import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/24 15:23
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/24$
 * @ 更新描述  ${TODO}
 */

public class BlueEvent {

    public List<Beacon> beacons;
    public BlueEvent(List<Beacon> beacons) {
        this.beacons=beacons;
    }
}
