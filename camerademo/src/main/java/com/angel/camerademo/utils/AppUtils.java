package com.angel.camerademo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.angel.camerademo.MyApp;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/04
 *     version: 1.0
 *     desc   : app相关的工具类
 * </pre>
 */
public class AppUtils {
    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getContext() {
        return MyApp.getContext();
    }

    /**
     * 得到resources对象
     *
     * @return
     */
    public static Resources getResource() {
        return getContext().getResources();
    }

    /**
     * 得到string.xml中的字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    /**
     * 得到string.xml中的字符串，带占位符
     *
     * @param id
     * @param formatArgs
     * @return
     */
    public static String getString(int id, Object... formatArgs) {
        return getResource().getString(id, formatArgs);
    }

    /**
     * 得到string.xml中和字符串数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArr(int resId) {
        return getResource().getStringArray(resId);
    }

    /**
     * 得到colors.xml中的颜色
     *
     * @param colorId
     * @return
     */
    @Deprecated
    public static int getColor(int colorId) {
        return getResource().getColor(colorId);
    }

    /**
     * 得到colors.xml中的颜色
     *
     * @param colorId
     * @return
     */
    public static int getColorWithV4(int colorId) {
        return ContextCompat.getColor(getContext(), colorId);
    }

    /**
     * 得到应用程序的包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }


    /**
     * 收起软键盘
     *
     * @param view
     */
    public static void hideInput(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 打开软键盘
     *
     * @param view
     */
    public static void showInput(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    /**
     * 判断listView是否到达顶部
     *
     * @param listView
     * @return
     */
    public static boolean isListViewReachTopEdge(ListView listView) {
        boolean result = false;
        if (listView.getFirstVisiblePosition() == 0) {
            View topChildView = listView.getChildAt(0);
            if (topChildView != null) {
                result = topChildView.getTop() == 0;
            } else {
                result = true;
            }
        }
        return result;
    }

    /**
     * 判断listView是否到达底部
     *
     * @param listView
     * @return
     */
    public static boolean isListViewReachBottomEdge(ListView listView) {
        boolean result = false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
            result = (listView.getHeight() >= bottomChildView.getBottom());
        }
        return result;
    }
}
