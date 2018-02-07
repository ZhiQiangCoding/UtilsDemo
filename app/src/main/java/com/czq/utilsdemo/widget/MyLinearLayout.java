package com.czq.utilsdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.czq.utilsdemo.utils.LogUtils;

/**
 * 公司：westsoft
 * 作者：程志强
 * 时间：2017/2/14 18:40
 * 描述：
 */

public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * onInterceptTouchEvent作用：拦截事件，用来决定事件是否传向子View
     * 返回true时，拦截后交给自己的onTouchEvent处理
     * 返回false时，交给子View来处理
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.e("=====viewGroup===onInterceptTouchEvent==");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e("=====viewGroup===onTouchEvent==");
        return super.onTouchEvent(event);
    }
}
