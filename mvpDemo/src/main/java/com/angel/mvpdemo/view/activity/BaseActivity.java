package com.angel.mvpdemo.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.angel.mvpdemo.presenter.BasePresenter;
import com.angel.mvpdemo.view.BaseView;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/03
 *     version: 1.0
 *     desc   : 基类activity
 * </pre>
 */
public abstract class BaseActivity<V extends BaseView, T extends BasePresenter<V>>
        extends AppCompatActivity {
    protected T mBasePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //建立连接,this指的是最终继承baseView的子view
        mBasePresenter = getBasePresenter();
        mBasePresenter.attach((V) this);

    }

    public abstract T getBasePresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBasePresenter.deAttach();
    }

}
