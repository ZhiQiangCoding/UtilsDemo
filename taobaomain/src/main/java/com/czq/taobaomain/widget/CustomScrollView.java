package com.czq.taobaomain.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/12
 *     version: 1.0
 *     desc   : ScrollView没有提供滚动事件的监听方法，也就没法判断是否滚动到了顶部，
 *     或者底部，这里我们继承ScrollView 自己实现滚动事件监听。
 * </pre>
 */
public class CustomScrollView extends ScrollView {
    public CustomScrollView(Context context) {
        super(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:

                if (mOnScrollListener != null) {
                    int contentHeight = getChildAt(0).getHeight();
                    int scrollHeight = getHeight();

                    int scrollY = getScrollY();
                    mOnScrollListener.onScroll(scrollY);

                    if (scrollY + scrollHeight >= contentHeight || contentHeight <= scrollHeight) {
                        mOnScrollListener.onScrollToBottom();
                    } else {
                        mOnScrollListener.notBottom();
                    }

                    if (scrollY == 0) {
                        mOnScrollListener.onScrollToTop();
                    }

                }

                break;
        }
        boolean result = super.onTouchEvent(ev);
        //防止子View禁止父view拦截事件
        requestDisallowInterceptTouchEvent(false);

        return result;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    private OnScrollListener mOnScrollListener;

    /**
     * scrollview滑动的监听事件
     */
    public interface OnScrollListener {
        void onScrollToBottom();//滑到底部

        void onScrollToTop();//滑到顶部

        void onScroll(int scrollY);//滑动事件

        void notBottom();//没有滑到底部
    }
}
