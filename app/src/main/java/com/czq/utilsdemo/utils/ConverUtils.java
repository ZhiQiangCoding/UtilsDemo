package com.czq.utilsdemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Locale;

import static com.czq.utilsdemo.utils.ConsUtils.GB;
import static com.czq.utilsdemo.utils.ConsUtils.KB;
import static com.czq.utilsdemo.utils.ConsUtils.MB;

/**
 * 公司:westsoft
 * 作者:程志强
 * 创建时间:2016/10/12 15:09*
 * 描述:转换相关的工具类
 */

public class ConverUtils {
    public ConverUtils() {
        throw new UnsupportedOperationException("实例化异常");
    }


    public static String bytes2HexString(byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0,j=0; i < len; i++) {
//            ret[j++]=hex
        }
        return null;
    }

    /**
     * inputStream装换为ByteArr
     *
     * @param is 输入流
     * @return 字节数组
     */
    public static byte[] inputStream2Bytes(InputStream is) {
        if (is == null) return null;
        return input2OutputStream(is).toByteArray();
    }

    private static ByteArrayOutputStream input2OutputStream(InputStream is) {
        // TODO: 2016/10/12 有待完善
        return null;
    }

    /**
     * 字节数转换为合适大小  保留三位小数
     *
     * @param byteNum 字节数
     * @return 1...1024unit
     */
    public static String byte2FitSize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < KB) {
            return String.format(Locale.getDefault(), "%.3fB", (double) byteNum);
        } else if (byteNum < MB) {
            return String.format(Locale.getDefault(), "%.3fKB", (double) byteNum / KB);
        } else if (byteNum < GB) {
            return String.format(Locale.getDefault(), "%.3MB", (double) byteNum / MB);
        } else {
            return String.format(Locale.getDefault(), "%.3fGB", (double) byteNum / GB);
        }
    }

}
