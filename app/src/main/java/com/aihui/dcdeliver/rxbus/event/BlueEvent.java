package com.aihui.dcdeliver.rxbus.event;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/24 15:23
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/24$
 * @ 更新描述  ${TODO}
 */

public class BlueEvent {
    String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BlueEvent(String location) {
        this.location = location;
    }
}
