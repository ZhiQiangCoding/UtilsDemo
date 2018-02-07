package com.czq.taobaomain.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/12
 *     version: 1.0
 *     desc   : 自定义向上滑动加载更多
 * </pre>
 */
public class CustomPullUpLoadMore extends ViewGroup {
    private CustomScrollView mTopCustomScrollView;//顶部scrollview
    private CustomScrollView mBottomCustomScrollView;//底部scrollview

    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    private Scroller mScroller = new Scroller(getContext());


    private int mCurrPosition = 0;
    private int mPosition1Y;
    private int mLastY;
    public int mScaledTouchSlop;//最小滑动距离
    private int mSpeed = 200;
    private boolean isIntercept;//是否拦截

    public boolean mBottomScrollVIewIsInTop = false;
    public boolean mTopScrollViewIsBottom = false;

    public CustomPullUpLoadMore(Context context) {
        super(context, null);
        init();
    }

    public CustomPullUpLoadMore(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public CustomPullUpLoadMore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        post(new Runnable() {
            @Override
            public void run() {
                mTopCustomScrollView = (CustomScrollView) getChildAt(0);
                mBottomCustomScrollView = (CustomScrollView) getChildAt(1);
                mTopCustomScrollView.setOnScrollListener(new CustomScrollView.OnScrollListener() {
                    @Override
                    public void onScrollToBottom() {
                        mTopScrollViewIsBottom = true;
                    }

                    @Override
                    public void onScrollToTop() {

                    }

                    @Override
                    public void onScroll(int scrollY) {

                    }

                    @Override
                    public void notBottom() {
                        mTopScrollViewIsBottom = false;
                    }

                });

                mBottomCustomScrollView.setOnScrollListener(new CustomScrollView.OnScrollListener() {
                    @Override
                    public void onScrollToBottom() {

                    }

                    @Override
                    public void onScrollToTop() {

                    }

                    @Override
                    public void onScroll(int scrollY) {
                        if (scrollY == 0) {
                            mBottomScrollVIewIsInTop = true;
                        } else {
                            mBottomScrollVIewIsInTop = false;
                        }
                    }

                    @Override
                    public void notBottom() {

                    }
                });

                mPosition1Y = mTopCustomScrollView.getBottom();

                mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //防止子View禁止父view拦截事件
        this.requestDisallowInterceptTouchEvent(false);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //判断是否已经滚动到了底部
                if (mTopScrollViewIsBottom) {
                    int dy = mLastY - y;

                    //判断是否是向上滑动和是否在第一屏
                    if (dy > 0 && mCurrPosition == 0) {
                        if (dy >= mScaledTouchSlop) {
                            isIntercept = true;//拦截事件
                            mLastY = y;
                        }
                    }
                }

                if (mBottomScrollVIewIsInTop) {
                    int dy = mLastY - y;

                    //判断是否是向下滑动和是否在第二屏
                    if (dy < 0 && mCurrPosition == 1) {
                        if (Math.abs(dy) >= mScaledTouchSlop) {
                            isIntercept = true;
                        }
                    }
                }

                break;
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        mVelocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int dy = mLastY - y;
                if (getScrollY() + dy < 0) {
                    dy = getScrollY() + dy + Math.abs(getScrollY() + dy);
                }

                if (getScrollY() + dy + getHeight() > mBottomCustomScrollView.getBottom()) {
                    dy = dy - (getScrollY() + dy - (mBottomCustomScrollView.getBottom() - getHeight()));
                }
                scrollBy(0, dy);
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;

                mVelocityTracker.computeCurrentVelocity(1000);
                float yVelocity = mVelocityTracker.getYVelocity();

                if (mCurrPosition == 0) {
                    if (yVelocity < 0 && yVelocity < -mSpeed) {
                        smoothScroll(mPosition1Y);
                        mCurrPosition = 1;
                    } else {
                        smoothScroll(0);
                    }
                } else {
                    if (yVelocity > 0 && yVelocity > mSpeed) {
                        smoothScroll(0);
                        mCurrPosition = 0;
                    } else {
                        smoothScroll(mPosition1Y);
                    }
                }
                break;
        }
        mLastY = y;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childTop = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(l, childTop, r, childTop + child.getMeasuredHeight());
            childTop += child.getMeasuredHeight();
        }
    }

    //通过Scroller实现弹性滑动
    private void smoothScroll(int tartY) {
        int dy = tartY - getScrollY();
        mScroller.startScroll(getScrollX(), getScrollY(), 0, dy);
        invalidate();
    }


    //滚动到顶部
    public void scrollToTop() {
        smoothScroll(0);
        mCurrPosition = 0;
        mTopCustomScrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

}
