package com.aihui.dcdeliver.rxbus.event;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/3/20 16:24
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/3/20$
 * @ 更新描述  ${TODO}
 */

public class DownEvent {
    private int Id;

    public DownEvent(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
