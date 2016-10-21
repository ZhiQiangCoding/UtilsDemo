package com.czq.utilsdemo.utils;

import java.util.Locale;

/**
 * 公司:westsoft
 * 作者:程志强
 * 创建时间:2016/8/21 16:08
 * 描述: 字符串工具类
 */
public class StringUtils {
    public StringUtils() {
        throw new UnsupportedOperationException("实例化异常!");
    }


    /**
     * 判断字符串为null或长度为0
     *
     * @param s 待检验的字符串
     * @return true:null,false:不为null
     */
    public static boolean isEmpty(CharSequence s) {
        return (s == null || s.length() == 0);
    }

    /**
     * 判断字符串为null或全为空格
     *
     * @param s 待检验的字符串
     * @return true:null或者全为空格,false:不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 生成时间格式
     *
     * @param time
     * @return
     */
    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }
}
