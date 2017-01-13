package com.czq.utilsdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.czq.utilsdemo.R;
import com.czq.utilsdemo.httputils.IRequestCallBack;
import com.czq.utilsdemo.httputils.IRequestManager;
import com.czq.utilsdemo.httputils.RequestFactory;

public class TestHttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_http);
        //测试请求
        String url = "";
        //这里发起请求依赖的是IRequestManager接口
        IRequestManager requestManager = RequestFactory.getIRequestManager();
        requestManager.get(url, new IRequestCallBack() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}
