package com.angel.retorfitrxmvpdemo.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/04
 *     version: 1.0
 *     desc   : 通用viewHolder
 * </pre>
 */
public class CommonViewHolder {
    private View mConvertView;
    private SparseArray<View> sparseArray = new SparseArray<>();

    public CommonViewHolder(Context context, @LayoutRes int resource) {
        mConvertView = LayoutInflater.from(context).inflate(resource, null);
        mConvertView.setTag(this);
    }

    public View getConvertView() {
        return mConvertView;
    }

    public static CommonViewHolder getViewHolder(View convertView, @LayoutRes int resource, Context context) {
        CommonViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new CommonViewHolder(context, resource);
        } else {
            viewHolder = (CommonViewHolder) convertView.getTag();
        }
        return viewHolder;
    }

    public <T extends View> T findView(@IdRes int id) {
        View view = sparseArray.get(id);
        if (view == null) {
            view = mConvertView.findViewById(id);
            sparseArray.put(id, view);
        }
        return (T) view;
    }
}
