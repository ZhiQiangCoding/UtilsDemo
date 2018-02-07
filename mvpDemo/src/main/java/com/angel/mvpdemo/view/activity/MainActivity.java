package com.angel.mvpdemo.view.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.angel.mvpdemo.R;
import com.angel.mvpdemo.presenter.MainAcPresenter;
import com.angel.mvpdemo.view.BaseView;

import java.util.List;

public class MainActivity extends BaseActivity<BaseView, MainAcPresenter> implements BaseView {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);

        //需要通过某种方式获取数据源，然后设置给listview
//        new MainAcPresenter(this).setModel(1).load();//创建presenter对象，调用内部的方法
        mBasePresenter.setModel(1).load();
    }

    @Override
    public MainAcPresenter getBasePresenter() {
        return new MainAcPresenter();
    }

    /**
     * 更新界面
     *
     * @param lists
     */
    @Override
    public void showView(List<String> lists) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lists);
        lv.setAdapter(adapter);

    }
}
