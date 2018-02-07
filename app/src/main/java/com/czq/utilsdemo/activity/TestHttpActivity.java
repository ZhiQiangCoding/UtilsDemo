package com.czq.utilsdemo.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.czq.utilsdemo.R;
import com.czq.utilsdemo.httputils.IRequestCallBack;
import com.czq.utilsdemo.httputils.IRequestManager;
import com.czq.utilsdemo.httputils.RequestFactory;
import com.czq.utilsdemo.utils.LogUtils;

public class TestHttpActivity extends AppCompatActivity {
    private static final String TAG = "===" + TestHttpActivity.class.getSimpleName() + "::";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_http);
        //测试请求
        String url = "http://api.test.yonghui.cn/b2b/merchant/app/version?deviceType=1&version=2.5.0_test_a1---";
        //这里发起请求依赖的是IRequestManager接口
        IRequestManager requestManager = RequestFactory.getIRequestManager();
        requestManager.get(url, new IRequestCallBack() {
            @Override
            public void onSuccess(String response) {
                boolean isMian = Looper.getMainLooper() == Looper.myLooper();
                LogUtils.e("===="+isMian);
                Log.e(TAG, "onSuccess: " + response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                boolean isMian = Looper.getMainLooper() == Looper.myLooper();
                LogUtils.e("===="+isMian);
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }
}
