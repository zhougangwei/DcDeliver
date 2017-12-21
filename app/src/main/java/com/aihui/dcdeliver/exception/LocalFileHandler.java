package com.aihui.dcdeliver.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import com.aihui.dcdeliver.util.FileUtil;
import com.aihui.dcdeliver.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Date;


/**
 * 本地异常处理类
 *
 * @author PLUTO
 */
public class LocalFileHandler extends BaseExceptionHandler {

    private Context context;

    public LocalFileHandler(Context context) {
        this.context = context;
    }

    /**
     * 自定义错误处理,手机错误信息,发送错误报告操作均在此完成,开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return
     */
    @Override
    public boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                LogUtil.e(ex.getMessage());
                Toast.makeText(context, "很抱歉，程序出现异常，正在从错误中恢复", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        //保存错误日志
        saveLog(ex);

        return true;
    }
    /**
     * 保存错误日志到本地
     *
     * @param ex
     */
    private void saveLog(Throwable ex) {
        try {

            File path = new File(FileUtil.getDiskCacheDir(context) + "/dclog");
            if (!path.exists()) {
                path.mkdirs();
            }
            File errorFile = new File(path + "/crash.txt");

            if (!errorFile.exists()) {
                errorFile.createNewFile();
            }
            OutputStream out = new FileOutputStream(errorFile, true);
            out.write((("\n-----错误分割线" + dateFormat.format(new Date()) + "-----") +
                    ("\n版本号:" + getVersionInfo()) +
                    "\n手机信息:" + getMobileInfo() +
                    "\n错误信息:" + getErrorInfo(ex)).getBytes());

            PrintStream stream = new PrintStream(out);
            ex.printStackTrace(stream);
            stream.flush();
            out.flush();
            stream.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取错误的信息
     *
     * @param arg1
     * @return
     */
    private String getErrorInfo(Throwable arg1) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        arg1.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }

    private String getVersionInfo() {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }

    /**
     * 获取手机的硬件信息
     *
     * @return
     */
    private String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        //通过反射获取系统的硬件信息
        try {

            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                //暴力反射 ,获取私有的信息
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}