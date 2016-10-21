package com.czq.utilsdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.czq.utilsdemo.R;
import com.czq.utilsdemo.adapter.CommonAdapter;
import com.czq.utilsdemo.adapter.CommonViewHolder;
import com.czq.utilsdemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class TestListViewActivity extends BaseActivity {


    private ListView lv_test;
    private Handler handler = new Handler();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test_list_view);
        lv_test = getViewById(R.id.lv_test);
    }

    @Override
    protected void setListener() {
        lv_test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                ToastUtils.show(mContext, "点击了" + s);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        showLoadingDialog("正在显示...");
        List<String> datas = new ArrayList<>();
        datas.add("万能适配器测试1");
        datas.add("万能适配器测试2");
        datas.add("万能适配器测试3");
        datas.add("万能适配器测试4");
        datas.add("万能适配器测试5");
        //设置适配器
        lv_test.setAdapter(new CommonAdapter<String>(mContext, datas, R.layout.item_test_lv) {
            @Override
            protected void convertView(View item, String s) {
                TextView textView = CommonViewHolder.get(item, R.id.tv_show);
                textView.setText(s);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissLoadingDialog();
            }
        }, 5000);

    }
}
