package com.angel.retorfitrxmvpdemo.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.angel.retorfitrxmvpdemo.presenter.BasePresenter;
import com.angel.retorfitrxmvpdemo.view.iview.BaseView;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/04
 *     version: 1.0
 *     desc   : xxxx描述
 * </pre>
 */
public abstract class BaseActivity<V extends BaseView, T extends BasePresenter<V>> extends AppCompatActivity {
    public T mBasePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBasePresenter = CreateSubBasePresenter();
        mBasePresenter.attach((V) this);
    }

    /**
     * 创建presenter对象
     *
     * @return
     */
    public abstract T CreateSubBasePresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBasePresenter.deAttach();
    }
}
