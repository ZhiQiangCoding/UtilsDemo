package com.czq.utilsdemo.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/06/18
 *     version: 1.0
 *     desc   : popupwindow的控制器
 * </pre>
 */
class PopupController {
    private int mLayoutResId;//布局id
    private Context mContext;
    private PopupWindow mPopupWindow;
    View mPopupView;//弹窗布局view
    private View mView;
    private Window mWindow;

    PopupController(Context context, PopupWindow popupWindow) {
        this.mContext = context;
        this.mPopupWindow = popupWindow;
    }

    /**
     * 设置弹窗view
     *
     * @param layoutResID 布局资源id
     */
    public void setView(@LayoutRes int layoutResID) {
        this.mView = null;
        this.mLayoutResId = layoutResID;
        initContentView();
    }


    /**
     * 设置弹窗view
     *
     * @param view
     */
    public void setView(View view) {
        this.mView = view;
        this.mLayoutResId = 0;
        initContentView();
    }

    /**
     * 初始化弹出窗的view
     */
    private void initContentView() {
        if (0 != mLayoutResId) {
            mPopupView = LayoutInflater.from(mContext).inflate(mLayoutResId, null);
        } else if (null != mView) {
            mPopupView = mView;
        }
        mPopupWindow.setContentView(mPopupView);
    }

    /**
     * 设置宽高
     *
     * @param width
     * @param height
     */
    private void setWidthAndHeight(int width, int height) {
        if (width == 0 || height == 0) {
            //如果没有设置宽高，则默认是WRAP_CONTENT
            mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            mPopupWindow.setWidth(width);
            mPopupWindow.setHeight(height);
        }
    }

    /**
     * 设置背景灰色程度
     *
     * @param level 0.0f ~1.0f
     */
    void setBackGroundLevel(float level) {
        mWindow = ((Activity) mContext).getWindow();
        WindowManager.LayoutParams wParams = mWindow.getAttributes();
        wParams.alpha = level;
        mWindow.setAttributes(wParams);

    }

    /**
     * 设置动画
     *
     * @param animationStyle 默认-1（默认动画），0：没有动画
     */
    private void setAnimationStyle(int animationStyle) {
        mPopupWindow.setAnimationStyle(animationStyle);
    }

    /**
     * 设置Outside是否可点击
     *
     * @param touchable 是否可点击
     */
    private void setOutsideTouchable(boolean touchable) {
        //设置透明背景
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //设置Outside可否点击
        mPopupWindow.setOutsideTouchable(touchable);
        //设置PopupWindow可获得焦点
        mPopupWindow.setFocusable(touchable);
    }


    static class PopupParams {
        int mLayoutResId;//布局id
        Context mContext;
        int mWidth, mHeight;
        boolean isShowBg, isShowAnim;
        float mBgLevel;//屏幕背景灰色程度
        int mAnimationStyle;//动画id
        View mView;
        boolean isTouchable = true;

        PopupParams(Context context) {
            this.mContext = context;
        }

        public void apply(PopupController controller) {
            if (0 != mLayoutResId) {
                controller.setView(mLayoutResId);
            } else if (null != mView) {
                controller.setView(mView);
            } else {
                throw new IllegalArgumentException("PopupView's contentView is null");
            }

            controller.setWidthAndHeight(mWidth, mHeight);
            controller.setOutsideTouchable(isTouchable);
            if (isShowBg) {
                //设置背景
                controller.setBackGroundLevel(mBgLevel);
            }

            if (isShowAnim) {
                //设置动画
                controller.setAnimationStyle(mAnimationStyle);
            }
        }
    }
}
