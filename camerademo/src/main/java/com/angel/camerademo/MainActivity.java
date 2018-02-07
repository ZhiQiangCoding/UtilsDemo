package com.angel.camerademo;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angel.camerademo.common.BitmapUtils;
import com.angel.camerademo.common.PhotoManager;
import com.angel.camerademo.utils.FileUtils;
import com.angel.camerademo.utils.LogUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mIvShow;//展示照片
    private TextView mTxtShow;//展示照片
    private Button mBtnTakePhoto;//拍照
    private Button mBtnSelecthoto;//选择照片
    private String imagePath;//拍照完成后图片保存路径
    private Uri outputUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }


    /**
     * 初始化view
     */
    private void initView() {
        mIvShow = (ImageView) findViewById(R.id.iv_show);
        mTxtShow = (TextView) findViewById(R.id.txt_show);
        mBtnTakePhoto = ((Button) findViewById(R.id.btn_take_photo));
        mBtnSelecthoto = ((Button) findViewById(R.id.btn_select_photo));
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        mBtnTakePhoto.setOnClickListener(this);
        mBtnSelecthoto.setOnClickListener(this);
        //初始化图片路径
        imagePath = FileUtils.CreateImagePath();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_take_photo://拍照
                getCaramePermission();
//                LogUtils.e("===保存图片路径：" + imagePath);
//                File imgFile = new File(imagePath);
//                Uri imgUrl = null;
//                //适配 Android7.0 或者以上，调用拍照功能的时候，导致应用Crash,报 FileUriExposedException 异常
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    //7.0以后使用getUriForFile()获取uri
//                    imgUrl = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", imgFile);
////                    imgUrl = FileProvider.getUriForFile(this, PhotoManager.FILE_PROVIDER_AUTHORITY, imgFile);
//                } else {
//                    imgUrl = Uri.fromFile(imgFile);
//                }
//                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUrl);
//                startActivityForResult(intent, PhotoManager.REQUEST_TOKEN_PHOTO);
                break;
            case R.id.btn_select_photo://相册
                intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, PhotoManager.REQUEST_SELECT_PHOTO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//完成
            Bitmap bitmap = null;
            File temFile = null;
            File srcFile = null;
            File outputFile = null;

            switch (requestCode) {
                case PhotoManager.REQUEST_TOKEN_PHOTO://拍照
                    srcFile = new File(imagePath);
                    outputFile = new File(FileUtils.CreateImagePath());
                    outputUri = Uri.fromFile(outputFile);
                    //发起裁剪请求
                    PhotoManager.startPhotoZoom(this, srcFile, outputFile, PhotoManager.REQUEST_CROP_PICTURE);
                    break;
                case PhotoManager.REQUEST_SELECT_PHOTO://相册
                    if (data != null) {
                        Uri sourceUri = data.getData();
                        String[] proj = {MediaStore.Images.Media.DATA};
                        // 好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = managedQuery(sourceUri, proj, null, null, null);
                        // 按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        // 最后根据索引值获取图片路径
                        String imgPath = cursor.getString(column_index);
                        srcFile = new File(imgPath);
                        outputFile = new File(FileUtils.CreateImagePath());
                        outputUri = Uri.fromFile(outputFile);
                        //发起裁剪请求
                        PhotoManager.startPhotoZoom(this, srcFile, outputFile, PhotoManager.REQUEST_CROP_PICTURE);
                    }
                    break;
                case PhotoManager.REQUEST_CROP_PICTURE://裁剪
                    //  Bundle extras = data.getExtras();
                    if (data != null) {
                        //  bm = extras.getParcelable("data");
                        if (outputUri != null) {
                            bitmap = BitmapUtils.decodeUriAsBitmap(outputUri);
                            //如果是拍照的，则删除临时文件
                            temFile = new File(imagePath);
                            if (temFile.exists()) {
                                temFile.delete();
                            }

                            String scaleImgPath = BitmapUtils.saveBitmapByQuality(bitmap, 100, 60);
                            File saveFile = new File(scaleImgPath);
//                            LogUtils.e("===压缩后的路径为：" + scaleImgPath + "\n==大小=：" + saveFile.length());
                            mTxtShow.setText("===压缩后的路径为：" + scaleImgPath + "\n==大小=：" + scaleImgPath.length() +
                                    "\n==文件路径=" + saveFile.getAbsolutePath() + "\n==文件大小=" + saveFile.length());
                            //进行上传，上传成功后显示新图片,上传的逻辑就是将scaleImgPath这个路径下的图片上传，
                            //此处不做演示，这里只是显示到iv上
                            mIvShow.setImageBitmap(bitmap);
                        }
                    } else {
                        Toast.makeText(this, "选择图片发生错误，图片可能已经移位或删除", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }


        } else if (resultCode == RESULT_CANCELED) {//取消
            Toast.makeText(this, "取消了", Toast.LENGTH_SHORT).show();

        }
    }


    /**
     * 动态获取相机权限
     */
    private void getCaramePermission() {
        //申请6.0 危险 权限
        AndPermission.with(MainActivity.this)
                .requestCode(100)
                .permission(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(MainActivity.this, rationale).show();
                    }
                })
                .send();

    }

    //----------------------------------权限回调处理----------------------------------//
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**
         * 转给AndPermission分析结果。
         *
         * @param requestCode  请求码。
         * @param permissions  权限数组，一个或者多个。
         * @param grantResults 请求结果。
         * @param listener PermissionListener 对象。
         */
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, List<String> grantPermissions) {
                switch (requestCode) {
                    case 100://相机
                        LogUtils.e("获取相机权限成功！");
                        LogUtils.e("===保存图片路径：" + imagePath);
                        File imgFile = new File(imagePath);
                        Uri imgUrl = null;
                        //适配 Android7.0 或者以上，调用拍照功能的时候，导致应用Crash,报 FileUriExposedException 异常
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //7.0以后使用getUriForFile()获取uri
                            imgUrl = FileProvider.getUriForFile(MainActivity.this, MainActivity.this.getApplicationContext().getPackageName() + ".provider", imgFile);
//                    imgUrl = FileProvider.getUriForFile(this, PhotoManager.FILE_PROVIDER_AUTHORITY, imgFile);
                        } else {
                            imgUrl = Uri.fromFile(imgFile);
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUrl);
                        startActivityForResult(intent, PhotoManager.REQUEST_TOKEN_PHOTO);
                        break;
                }
            }

            @Override
            public void onFailed(int requestCode, List<String> deniedPermissions) {

                switch (requestCode) {
                    case 100://相机
                        Toast.makeText(MainActivity.this, "获取相机权限失败", Toast.LENGTH_SHORT).show();
                        break;
                }
                // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
                    // 第一种：用默认的提示语。
                    AndPermission.defaultSettingDialog(MainActivity.this, 101).show();
                }

            }
        });
    }

}
