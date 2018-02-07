package com.czq.utilsdemo.qqZone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.czq.utilsdemo.R;
import com.czq.utilsdemo.activity.BaseActivity;

public class QQZoneActivity extends BaseActivity {
    private ScrollZoomListView lv;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_qqzone);
        lv = getViewById(R.id.lv);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        //lv添加头
        View header = LayoutInflater.from(mContext).inflate(R.layout.listview_header, null);
        ImageView imageView = (ImageView) header.findViewById(R.id.header_bg);
        lv.addHeaderView(header);
        //设置缩放的iv
        lv.setZoomImageView(imageView);

        //设置适配器
        String[] datas = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"
                , "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_activated_1, datas);
        lv.setAdapter(arrayAdapter);
    }
}
