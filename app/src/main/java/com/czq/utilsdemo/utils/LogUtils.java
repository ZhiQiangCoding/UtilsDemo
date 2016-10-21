package com.czq.utilsdemo.utils;

import android.util.Log;

/**
 * 公司:westsoft
 * 作者:程志强
 * 创建时间:2016/9/6 14:21
 * 描述:log工具类,android.util.Log常用的方法有以下5个：Log.v() ,Log.d() ,Log.i() ,Log.w() ,Log.e()
 * 按照日志级别从高到低为ERROR, WARN, INFO, DEBUG, VERBOSE.至于日志级别本身的含义.
 * 注意:项目上线的时候需要将debug改为false
 */
public final class LogUtils {
    public static final String TAG = "===utilsdemo==";
    public static final boolean DEBUG = true;

    public LogUtils() {
        throw new UnsupportedOperationException("实例化异常!");
    }

    /**
     * 黑色，输出大于或等于verbose日志级别的信息
     *
     * @param desc
     */
    public static void v(String desc) {
        if (DEBUG) {
            Log.v(TAG, desc);
        }
    }

    public static void v(Object desc) {
        if (DEBUG) {
            Log.v(TAG, desc + "");
        }
    }

    /**
     * 蓝色，输出大于或等于debug日志级别的信息
     *
     * @param desc
     */

    public static void d(String desc) {
        if (DEBUG) {
            Log.d(TAG, desc);
        }
    }

    public static void d(Object desc) {
        if (DEBUG) {
            Log.d(TAG, desc + "");
        }
    }

    /**
     * 绿色，输出大于或等于info日志级别的信息
     *
     * @param desc
     */
    public static void i(String desc) {
        if (DEBUG) {
            Log.i(TAG, desc);
        }
    }

    public static void i(Object desc) {
        if (DEBUG) {
            Log.i(TAG, desc + "");
        }
    }

    /**
     * 橙色, 输出大于或等于warn日志级别的信息
     *
     * @param desc
     */
    public static void w(String desc) {
        if (DEBUG) {
            Log.w(TAG, desc);
        }
    }

    public static void w(Object desc) {
        if (DEBUG) {
            Log.w(TAG, desc + "");
        }
    }

    /**
     * 红色，仅输出error日志级别的信息.
     *
     * @param desc
     */
    public static void e(String desc) {
        if (DEBUG) {
            Log.e(TAG, desc);
        }
    }

    public static void e(Object desc) {
        if (DEBUG) {
            Log.e(TAG, desc + "");
        }
    }
}
