package deliver.aihui.com.dcdeliver.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Method;

/**
 * @ 创建者   zhou
 * @ 创建时间   2017/12/7 0007 21:45
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2017/12/7 0007$
 * @ 更新描述  ${TODO}
 */

public class Inpututils {

        private static final String TAG = Inpututils.class.getSimpleName();
        /**
         * 关闭键盘
         *
         * @param activity
         */
        public static void closeKeyboard(Activity activity) {
            if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                if (activity.getCurrentFocus() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }

        /**
         * 判断是否有虚拟底部按钮
         *
         * @return
         */
        public static boolean checkDeviceHasNavigationBar(Context context) {
            boolean hasNavigationBar = false;
            Resources rs = context.getResources();
            int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id);
            }
            try {
                Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
                Method m = systemPropertiesClass.getMethod("get", String.class);
                String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
                if ("1".equals(navBarOverride)) {
                    hasNavigationBar = false;
                } else if ("0".equals(navBarOverride)) {
                    hasNavigationBar = true;
                }
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());

           }
            return hasNavigationBar;
        }

        /**
         * 获取底部虚拟按键高度
         *
         * @return
         */
        public static int getNavigationBarHeight(Context context) {
            int navigationBarHeight = 0;
            Resources rs = context.getResources();
            int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
            if (id > 0 && checkDeviceHasNavigationBar(context)) {
                navigationBarHeight = rs.getDimensionPixelSize(id);
            }
            return navigationBarHeight;
        }



}
