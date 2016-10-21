package com.czq.utilsdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 公司：westsoft
 * 作者：程志强
 * 时间：2016/10/18 10:04
 * 描述：万能适配器
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    private Context context;//上下文
    private List<T> datas;//数据源
    private int layoutId;//布局id


    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    /**
     * 刷新数据
     *
     * @param b 是否需要刷新
     */
    public void setRefresh(boolean b) {
        if (b) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutId, null);
        }
        T t = getItem(position);
        convertView(convertView, t);

        return convertView;
    }

    /**
     * 需要去实现对Item中的View的设置与操作
     *
     * @param item 根view
     * @param t    需要设置的数据对象
     */
    protected abstract void convertView(View item, T t);
}
