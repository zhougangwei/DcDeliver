package deliver.aihui.com.dcdeliver.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;

import deliver.aihui.com.dcdeliver.util.LogUtil;

/**
 * 系统异常处理类
 * @author PLUTO
 *
 */
public abstract class BaseExceptionHandler implements UncaughtExceptionHandler {


	public Context context;
	public static final String TAG = "CrashHandler";
	
	protected static final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);

	/**
	 * 未捕获异常跳转
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		//如果正确处理的为捕获异常
		if (handleException(ex)) {
			try {

/*				// 1.获取当前程序的版本号. 版本的id
				String versioninfo = getVersionInfo();
				// 2.获取手机的硬件信息.
				String mobileInfo  = getMobileInfo();*/

				//3.把错误的堆栈信息 获取出来
				String errorinfo = getErrorInfo(ex);

				LogUtil.e(errorinfo);

				//如果处理了,让程序继续运行3秒后退出,保证错误日志的保存
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//退出程序
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 自定义错误处理,手机错误信息,发送错误报告操作均在此完成,开发者可以根据自己的情况来自定义异常处理逻辑
	 * @param ex
	 * @return
	 */
	public abstract boolean handleException(Throwable ex);


	/**
	 * 获取错误的信息
	 * @param arg1
	 * @return
	 */
	private String getErrorInfo(Throwable arg1) {
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		arg1.printStackTrace(pw);
		pw.close();
		String error= writer.toString();
		return error;
	}

	private String getVersionInfo(){
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo info =pm.getPackageInfo(context.getPackageName(), 0);
			return  info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "版本号未知";
		}
	}

	/**
	 * 获取手机的硬件信息
	 * @return
	 */
	private String getMobileInfo() {
		StringBuffer sb = new StringBuffer();
		//通过反射获取系统的硬件信息
		try {

			Field[] fields = Build.class.getDeclaredFields();
			for(Field field: fields){
				//暴力反射 ,获取私有的信息
				field.setAccessible(true);
				String name = field.getName();
				String value = field.get(null).toString();
				sb.append(name+"="+value);
				sb.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}







}
