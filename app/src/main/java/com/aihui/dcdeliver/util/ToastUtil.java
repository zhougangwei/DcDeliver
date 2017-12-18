package com.aihui.dcdeliver.util;

import android.widget.Toast;

import static com.aihui.dcdeliver.application.BaseApplication.sContext;


/**
 * @ 创建者   zhou
 * @ 创建时间   2017/3/16 17:50
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/3/16$
 * @ 更新描述  ${TODO}
 */
public class ToastUtil {

    private static Toast toast;
    /**
     *  弹吐司的  因为只有一个对象 可以不断的弹
     */
    public static void showToast(String text){
       if(toast==null){
            toast = Toast.makeText(sContext, text, Toast.LENGTH_SHORT);
        }else {
            toast.setText(text);//如果不为空，则直接改变当前toast的文本
        }
        toast.show();
    }
}