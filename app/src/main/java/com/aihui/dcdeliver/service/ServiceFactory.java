package com.aihui.dcdeliver.service;

import android.support.annotation.StringDef;

/**
 * @ 创建者   zhou
 * @ 创建时间   2018/1/31 14:03
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2018/1/31$
 * @ 更新描述  ${TODO}
 */

public class ServiceFactory {

    public static final String BLUE_THOOTH ="blue";
    public static final String DYX_THOOTH  ="dyx";
    @StringDef({BLUE_THOOTH, DYX_THOOTH})
    public @interface ServiceType {}


    public static String CURRENT_TYPE="";

    public static Class getSeviceClass(){
        switch (CURRENT_TYPE) {
            case BLUE_THOOTH:
                return BlueService.class;
            case DYX_THOOTH:
                return   DyxService.class;
            default:
                throw new RuntimeException("是否忘记setCurrentType了");
        }
    }

    public static void setCurrentType( @ServiceType  String type){
        CURRENT_TYPE = type;
    }

}
