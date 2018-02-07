package com.angel.camerademo.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 公司：westsoft
 * 作者：程志强
 * 时间：2016/10/12 17:50
 * 描述：关闭相关的工具类
 */

public class CloseUtils {
    public CloseUtils() {
        throw new UnsupportedOperationException("实例化异常!");
    }

    /**
     * 关闭IO
     *
     * @param closeables Closeabled对象组成的可变数组
     */
    public static void closeID(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables Closeabled对象组成的可变数组
     */
    public static void closeIDQuietly(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
           // e.printStackTrace();
        }
    }
}
