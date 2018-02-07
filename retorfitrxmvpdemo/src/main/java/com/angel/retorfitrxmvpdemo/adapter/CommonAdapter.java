package com.angel.retorfitrxmvpdemo.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/04
 *     version: 1.0
 *     desc   : 通用适配器
 * </pre>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private List<T> datas;
    private @LayoutRes int resource;

    public CommonAdapter(List<T> datas, int resource) {
        this.datas = datas;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return datas.isEmpty() ? 0 : datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(convertView, resource, parent.getContext());
        fillData(position, viewHolder);
        return viewHolder.getConvertView();
    }

    /**
     * 填充数据
     *
     * @param position
     * @param viewHolder
     */
    protected abstract void fillData(int position, CommonViewHolder viewHolder);
}
