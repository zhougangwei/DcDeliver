package com.aihui.dcdeliver.base;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/3/23 11:57
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/3/23$
 * @ 更新描述  ${TODO}
 */

public class Content {

    public static final String PASSWORD     = "password";             //密码
    public static final String USER_ACCOUNT = "user_account";      //账号
    // public static final String WS_ADDRESS   = "http://192.168.1.198:8080/";      //账号
    public static final String WS_ADDRESS   = "http://120.27.241.2:7777/";      //账号

    public static final String HAS_RECEIVE  = "hasReceive";      //接收权限
    public static final String HAS_SAVE     = "hasSave";      //接收权限
    public static final String HAS_SIGN     = "hasSignIn";      //是否打卡
    public static final String IS_ALERT_DAKA     = "isAlertDaka";      //时效过期
    public static final String CANCEL_ALERT_TIME="cancelAlertTime";     //取消打卡的时间


    public static final String LOGIN_TIME = "isLogin";      //接收权限

    public static final int NEW_WAY_REQUEST_CODE = 1;       //新建request


    public static final int STATE_REQUEST_CODE = 2;          //状态


    //成功
    public static final int WEB_RESP_CODE_SUCCESS = 200;
    //token
    public static final int TOKEN_EXPRIED         = 704;


    public static final String HAS_NEXT_RECORD="hasNextRecord"; //是否有下调记录
}
