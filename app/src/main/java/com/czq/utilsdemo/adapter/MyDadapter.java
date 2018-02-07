package com.czq.utilsdemo.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * 公司：westsoft
 * 作者：程志强
 * 时间：2017/2/7 11:32
 * 描述：
 */

public class MyDadapter<T> extends CommonAdapter<T> {

    public MyDadapter(Context context, List<T> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void convertView(View item, T t) {

    }
}
