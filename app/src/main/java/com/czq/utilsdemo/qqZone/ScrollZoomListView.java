package com.czq.utilsdemo.qqZone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;

import com.czq.utilsdemo.R;
import com.czq.utilsdemo.utils.LogUtils;

/**
 * 公司：westsoft
 * 作者：程志强
 * 时间：2017/2/23 21:04
 * 描述：仿qq控件lv
 */

public class ScrollZoomListView extends ListView {
    private ImageView mImageView;
    private int mImageViewHeight;

    public ScrollZoomListView(Context context) {
        super(context, null);
    }

    public ScrollZoomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mImageViewHeight = context.getResources().getDimensionPixelSize(R.dimen.iv_default);
    }


    public void setZoomImageView(ImageView iv) {
        this.mImageView = iv;

    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                   int maxOverScrollY, boolean isTouchEvent) {
        /**
         * 滑动过度的情况分两种
         * deltay：增量
         * 下拉过度：deltay -
         * 上拉过度：deltay +
         */
        if (deltaY < 0) { //下拉过度
            //imageview进行放大的效果
            mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
            //不会刷新---view.layout
            mImageView.requestLayout();

        } else {//上拉过度
            //imageview进行缩小的效果
            mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
            //不会刷新---view.layout
            mImageView.requestLayout();
        }
        LogUtils.e("==deltaY==" + deltaY + "==scrollY==" + scrollY + "==scrollRangeY==" + scrollRangeY + "==maxOverScrollY==" + maxOverScrollY);
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //只有当imageview被放大过，才执行缩小
        View parent = (View) mImageView.getParent();
        //Listview会滑出去的高度（负数）
        int deltaY = parent.getTop();
        if (mImageView.getHeight() > mImageViewHeight) {
            //imageview进行放大的效果
            mImageView.getLayoutParams().height = mImageView.getHeight() + deltaY;
            //由于滑出去了一截，所以要让parent重新摆放 top设置为0
            parent.layout(parent.getLeft(), 0, parent.getRight(), parent.getHeight());

            //不会刷新---view.layout
            mImageView.requestLayout();
        }
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtils.e("==l==" + l + "==t==" + t + "==oldl==" + oldl + "==oldt==" + oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //松手
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            //开启动画
            ResetAnimation resetAnimation = new ResetAnimation(mImageViewHeight);
            //设置插值器
            resetAnimation.setInterpolator(new BounceInterpolator());
            //设置执行的时间
            resetAnimation.setDuration(500);
            mImageView.startAnimation(resetAnimation);
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 自定义动画
     */
    public class ResetAnimation extends Animation {
        private int extratHeight;//高度差
        private int currentHeight;

        public ResetAnimation(int targetHeight) {
            currentHeight = mImageView.getHeight();
            extratHeight = mImageView.getHeight() - targetHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            /**
             * 0--1
             * height--初始高度
             */
            //imageview进行缩小的效果
            mImageView.getLayoutParams().height = (int) (currentHeight - extratHeight * interpolatedTime);
            //不会刷新---view.layout
            mImageView.requestLayout();
            super.applyTransformation(interpolatedTime, t);
        }
    }
}
