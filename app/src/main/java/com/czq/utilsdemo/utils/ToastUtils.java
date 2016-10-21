package com.czq.utilsdemo.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * 公司:westsoft
 * 作者:程志强
 * 创建时间:2016/9/19 22:32
 * 描述:吐司的帮助类
 */
public class ToastUtils {
    public ToastUtils() {
        throw new UnsupportedOperationException("实例化异常!");
    }

    /**
     * 显示toast
     *
     * @param context 上下文
     * @param desc    显示的文本
     */
    public static void show(Context context, CharSequence desc) {
        if (desc.length() < 5) {
            Toast.makeText(context, desc, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, desc, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 显示toast
     *
     * @param context 上下文
     * @param resId   显示文本的资源id
     */
    public static void show(Context context, @StringRes int resId) {
        show(context, context.getResources().getString(resId));
    }
}
