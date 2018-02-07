package com.angel.camerademo.common;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/04
 *     version: 1.0
 *     desc   : 照片相关的帮助类，包括拍照、选择相册
 * </pre>
 */
public class PhotoManager {
    public static final int REQUEST_TOKEN_PHOTO = 100;//拍照
    public static final int REQUEST_SELECT_PHOTO = 101;//选择相册
    public static final int REQUEST_CROP_PICTURE = 102;//选择相册

    /**
     * file_provider_authority
     */
//    public static String FILE_PROVIDER_AUTHORITY = AppUtils.getPackageName() + ".fileprovider";
//    imageUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);

    /**
     * 发起裁剪照片的请求
     *
     * @param activity    上下文
     * @param srcFile     原文件file
     * @param outFile     输出文件的file
     * @param requestCode 请求码
     */
    public static void startPhotoZoom(Activity activity, File srcFile, File outFile, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(Uri.fromFile(srcFile), "image/*");
        intent.setDataAndType(getImageContentUri(activity, srcFile), "image/*");
        //crop为true是设置在开启的intent中设置显示的view可以裁剪
        intent.putExtra("crop", "true");
        //aspectX,aspectY是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //outputX,outputY是裁剪图片的宽高
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 480);
        //true:不返回uri,false:返回uri
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 适配Android 7.0以后裁剪根据文件路径获取uri
     *
     * @param context
     * @param imageFile
     * @return
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=?",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(
                    cursor.getColumnIndex(MediaStore.Images.Media._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().
                        insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
