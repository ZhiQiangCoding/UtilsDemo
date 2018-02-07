package com.angel.retorfitrxmvpdemo.view.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.angel.retorfitrxmvpdemo.R;
import com.angel.retorfitrxmvpdemo.adapter.FootAdapter;
import com.angel.retorfitrxmvpdemo.bean.response.Foot;
import com.angel.retorfitrxmvpdemo.presenter.FootPresenter;
import com.angel.retorfitrxmvpdemo.view.iview.FootView;

import java.util.List;

public class MainActivity extends BaseActivity<FootView, FootPresenter> implements FootView {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = ((ListView) findViewById(R.id.lv));
        mBasePresenter.load();
    }

    @Override
    public FootPresenter CreateSubBasePresenter() {
        return new FootPresenter();
    }

    @Override
    public void showData(List<Foot.TngouBean> tngouBeen) {
        FootAdapter footAdapter = new FootAdapter(tngouBeen, R.layout.item_foot_layout);
        lv.setAdapter(footAdapter);

    }
}
