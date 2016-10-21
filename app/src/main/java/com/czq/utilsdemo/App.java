package com.czq.utilsdemo;

import android.app.Application;

import com.czq.utilsdemo.utils.FlashBackUtils;

/**
 * 公司:westsoft
 * 作者:angelCheng
 * 创建时间:2016/9/13 21:41
 * 描述:Application类的使用
 */
public class App extends Application {
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
    }
}
