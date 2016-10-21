package com.czq.utilsdemo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公司:westsoft
 * 作者:angelCheng
 * 创建时间:2016/9/21 10:49
 * 描述:app闪退信息保存在本地或者服务器的工具类
 */

public class FlashBackUtils implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "==FlashBackUtils=";

    private static final boolean DEBUG = true;
    //保存路径
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/FlashBack/Log/";
    //文件名
    private static final String FILE_NAME = "flashback";
    //文件名后缀
    private static final String FILE_NAME_SUFFIX = ".txt";
    //实例化FlashBackUtils
    private static FlashBackUtils mInstance = new FlashBackUtils();
    //上下文对象
    private Context mContext;
    //默认异常处理对象
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;


    public FlashBackUtils() {
    }

    /**
     * 获取FlashBackUtils对象
     *
     * @return 返回FlashBackUtils对象
     */
    public static FlashBackUtils getInstance() {
        return mInstance;
    }

    /**
     * 初始化方法
     *
     * @param context
     */
    public void init(Context context) {
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置未捕获异常处理程序
        Thread.setDefaultUncaughtExceptionHandler(this);
        //获取上下文对象
        mContext = context.getApplicationContext();
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用uncaughtException方法
     *
     * @param thread 出现未捕获异常的线程
     * @param ex     未捕获的异常,有了这个ex，我们就可以得到异常信息
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            //导出异常信息到sd卡
            dumpExceptionToSdCard(ex);
            //这里可以上传异常信息到服务器，便于开发人员分析日志从而解决bug
            uploadExceptionToServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ex.printStackTrace();
        //如果系统提供默认的异常处理器，则交给系统去结束程序，否则就由自己结束自己
        if (mDefaultUncaughtExceptionHandler != null) {
            mDefaultUncaughtExceptionHandler.uncaughtException(thread, ex);
        } else {
            //自己处理
            try {
                //延迟2秒杀进程
                Thread.sleep(2000);
                Toast.makeText(mContext, "程序出错了...", Toast.LENGTH_SHORT).show();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e(TAG, "error: ", ex);
            }
            //杀死进程
            Process.killProcess(Process.myPid());
        }
    }

    /**
     * 导出异常信息到sd卡
     *
     * @param ex
     */
    private void dumpExceptionToSdCard(Throwable ex) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.e(TAG, "SDCard未安装 ");
                return;
            }
        }
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String time = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss").format(new Date(System.currentTimeMillis()));
        File file = new File(PATH + FILE_NAME + "_" + time + FILE_NAME_SUFFIX);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println("Log打印时间:" + time);
            dumpPhoneInfo(pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
            Log.e(TAG, "保存异常信息成功!");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            Log.e(TAG, "保存异常信息失败!");
        }
    }

    /**
     * 导出手机信息
     *
     * @param pw
     */
    private void dumpPhoneInfo(PrintWriter pw) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            pw.print("APP Version:  ");
            pw.print(pi.versionName);
            pw.print("_");
            pw.print(pi.versionCode);
            pw.println("");
            //Android 版本号
            pw.println("Android 版本号:");
            pw.print("OS Version:  ");
            pw.print(Build.VERSION.RELEASE);
            pw.print("-");
            pw.print(Build.VERSION.SDK_INT);
            pw.println("");
            //手机运行商
            pw.println("手机运行商:");
            pw.print("Vendor:  ");
            pw.print(Build.MANUFACTURER);
            pw.println("");
            //手机型号
            pw.println("手机型号:");
            pw.print("Model:  ");
            pw.print(Build.MODEL);
            pw.println("");
            //CUP架构
            pw.println("CPU架构:");
            pw.print("CPU ABI:  ");
            pw.print(Build.CPU_ABI + "   ");
            pw.print(Build.CPU_ABI2);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 上传异常信息到服务器，便于开发人员分析日志从而解决bug
     */
    private void uploadExceptionToServer() {
    }

}
