package com.yh.refreshdemo;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/06/04
 *     version: 1.0
 *     desc   : xxxx描述
 * </pre>
 */
public class MyViewGroup  extends ViewGroup {

    public MyViewGroup(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}