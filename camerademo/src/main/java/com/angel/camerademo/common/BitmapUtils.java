package com.angel.camerademo.common;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.angel.camerademo.utils.AppUtils;
import com.angel.camerademo.utils.CloseUtils;
import com.angel.camerademo.utils.FileUtils;
import com.angel.camerademo.utils.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/04
 *     version: 1.0
 *     desc   : bitmap相关的工具类
 * </pre>
 */
public class BitmapUtils {
    public BitmapUtils() {
    }

    /**
     * 将uri转换为bitmap对象
     *
     * @param uri uri
     * @return bitmap对象
     */
    public static Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            //获取ContentResolver对象
            ContentResolver contentResolver = AppUtils.getContext().getContentResolver();
            //调用openInputStream方法获取uri关联的数据流
            is = contentResolver.openInputStream(uri);
            //将liu转换成bitmap对象
            bitmap = BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeID(is);
        }
        return bitmap;
    }

    /**
     * 图片质量压缩
     *
     * @param bitmap  bitmap对象
     * @param quality 压缩的保留率，比如使用80，表示压缩为原来的80%,则对其压缩了20%
     * @return
     */
    public static String saveBitmapByQuality(Bitmap bitmap, int quality, int maxSize) {
        String cropPath = "";
        try {
            File file = new File(FileUtils.CreateImagePath());

            //得到相机图片存到本地的图片
            cropPath = file.getPath();
            if (file.exists()) {
                file.delete();
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            while (bos.toByteArray().length / 1024 > maxSize) {
                LogUtils.e("===图片大小==" + bos.toByteArray().length);
                // Clean up os
                bos.reset();
                // interval 10
                quality -= 10;
                if (quality <= 10) {
                    break;
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bos.toByteArray());
            LogUtils.e("===图片压缩后的最终大小==" + bos.toByteArray().length + "==wenjian=" + file.length());
            String saveLocalFile = FileUtils.saveFile(bos.toByteArray(), FileUtils.createFileDir("demo"), System.currentTimeMillis() + "_save.jpg");
            LogUtils.e("===本地保存==" + saveLocalFile);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cropPath;
    }

}
