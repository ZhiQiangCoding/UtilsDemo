package com.czq.utilsdemo.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * 公司：westsoft
 * 作者：程志强
 * 时间：2016/10/18 09:47
 * 描述：万能的ViewHolder
 */

public class CommonViewHolder {
    /**
     * @param view 所有缓存View的根View
     * @param id   缓存View的唯一标识
     * @param <T>
     * @return
     */
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        //如果根view没有在缓存View的集合中
        if (viewHolder == null) {
            viewHolder = new SparseArray<>();
            view.setTag(viewHolder);//创建集合和根View
        }
        //获取根View储存在集合中的孩子
        View childView = viewHolder.get(id);
        if (childView == null) {//没找到子view
            //找到子view
            childView = view.findViewById(id);
            //保存到集合
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
