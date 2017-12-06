package deliver.aihui.com.dcdeliver.exception;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

import deliver.aihui.com.dcdeliver.util.FileUtil;
import deliver.aihui.com.dcdeliver.util.LogUtil;
import deliver.aihui.com.dcdeliver.util.SDCardUtil;


/**
 * 本地异常处理类
 *
 * @author PLUTO
 */
public class LocalFileHandler extends BaseExceptionHandler {


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
        if (ex == null)
            return false;

        new Thread() {
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "很抱歉，程序出现异常，正在从错误中恢复", Toast.LENGTH_LONG).show();
                LogUtil.d(ex.getMessage() + "");
                ex.printStackTrace();

                if(SDCardUtil.isSDCardEnable()){
                    File file = new File(Environment.getExternalStorageDirectory()
                            .getAbsoluteFile(), "error15.log");
                    PrintStream err;
                    try {
                        err = new PrintStream(file);
                        ex.printStackTrace(err);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else{
                    File file = new File(Environment.getDataDirectory().getAbsoluteFile(), "error15.log");
                    PrintStream err;
                    try {
                        err = new PrintStream(file);
                        ex.printStackTrace(err);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                //不能让人看出是报错了

                android.os.Process.killProcess(android.os.Process.myPid());
             /*   Intent intent = new Intent(sContext, MainActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                sContext.startActivity(intent)*/;


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

            File path = new File(FileUtil.getDiskCacheDir(context) + "/log");
            if (!path.exists()) {
                path.mkdirs();
            }

            File errorFile = new File(path + "/crash.txt");

            if (!errorFile.exists()) {
                errorFile.createNewFile();
            }

            OutputStream out = new FileOutputStream(errorFile, true);
            out.write(("\n\n-----错误分割线" + dateFormat.format(new Date()) + "-----\n\n").getBytes());
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

}
