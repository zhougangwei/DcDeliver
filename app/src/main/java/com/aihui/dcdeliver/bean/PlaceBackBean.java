package com.aihui.dcdeliver.bean;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/25 9:23
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/25$
 * @ 更新描述  ${TODO}
 */

public class PlaceBackBean {
    /**
     * msg : success
     * body : true
     * code : 200
     */

    private String msg;
    private boolean body;
    private int     code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getBody() {
        return body;
    }

    public void setBody(boolean body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
