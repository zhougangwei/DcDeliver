package com.aihui.dcdeliver.rxbus.event;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/3/20 16:24
 * @ 描述    ${是否需要打卡}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/3/20$
 * @ 更新描述  ${TODO}
 */

public class AlertEvent {

    boolean isAlertDaka;

    public AlertEvent(boolean isAlertDaka) {
        this.isAlertDaka = isAlertDaka;
    }
}
