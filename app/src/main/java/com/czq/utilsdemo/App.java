package com.czq.utilsdemo;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.antfortune.freeline.FreelineCore;
import com.czq.utilsdemo.utils.FlashBackUtils;

import org.xutils.x;

/**
 * 公司:westsoft
 * 作者:angelCheng
 * 创建时间:2016/9/13 21:41
 * 描述:Application类的使用
 */
public class App extends Application {
    public static RequestQueue mRequestQueue;
    private static App mInstance;

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //初始化闪退异常，保存本地
        FlashBackUtils flashBackUtils = new FlashBackUtils();
        flashBackUtils.init(this);
        //初始化volley队列
        mRequestQueue = Volley.newRequestQueue(this);
        //初始化 xutils3
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        //初始化freeline
        FreelineCore.init(this);
    }
}
