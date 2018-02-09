package com.czq.utilsdemo.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.czq.utilsdemo.R;
import com.czq.utilsdemo.httputils.IRequestCallback;
import com.czq.utilsdemo.httputils.IRequestManager;
import com.czq.utilsdemo.httputils.RequestFactory;
import com.czq.utilsdemo.utils.LogUtils;

public class TestHttpActivity extends AppCompatActivity {
    private static final String TAG = "===" + TestHttpActivity.class.getSimpleName() + "::";

    /**
     * 下载
     */
    private Button mBtn;
    /**
     *
     */
    private TextView mTvShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_http);
        mBtn = findViewById(R.id.btn_download);
        mTvShow = findViewById(R.id.tv_show);

        final IRequestManager requestManager = RequestFactory.getIRequestManager();
        //测试请求
        final String url = "http://v.juhe.cn/boxoffice/rank?area=&dtype=&key=4808cf0770c56ef2f1f970dc310a3d4c";
        //这里发起请求依赖的是IRequestManager接口
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestManager.get(url, new IRequestCallback() {
                    @Override
                    public void onSuccess(String json) {
                        boolean isMian = Looper.getMainLooper() == Looper.myLooper();
                        LogUtils.e("====" + isMian);
                        Log.e(TAG, "onSuccess: " + json);
                        Toast.makeText(TestHttpActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
                        mTvShow.setText(json);
                    }

                    @Override
                    public void onFailure(String msg, String code, Throwable throwable) {
                        boolean isMian = Looper.getMainLooper() == Looper.myLooper();
                        LogUtils.e("====" + isMian);
                        Log.e(TAG, "onFailure: " + throwable.getMessage());
                        Toast.makeText(TestHttpActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
