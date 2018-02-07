package com.czq.utilsdemo.popup;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.PopupWindow;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/06/18
 *     version: 1.0
 *     desc   : 通用popupwindow类
 *     https://github.com/crazyqiang/AndroidStudy
 * </pre>
 */
public class CommonPopupWindow extends PopupWindow {
    private final PopupController mPopupController;

    public CommonPopupWindow(Context context) {
        mPopupController = new PopupController(context, this);
    }

    @Override
    public int getWidth() {
//        return super.getWidth();
        return mPopupController.mPopupView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
//        return super.getHeight();
        return mPopupController.mPopupView.getMeasuredHeight();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mPopupController.setBackGroundLevel(1.0f);
    }

    /**
     * popupwindow中视图view的接口
     */
    public interface IViewInterface {
        /**
         * 获取子view
         *
         * @param view
         * @param layoutResId
         */
        void getChildView(View view, @LayoutRes int layoutResId);
    }

    /**
     * 建造器
     */
    public static class Builder {
        private final PopupController.PopupParams params;
        private IViewInterface listener;

        public Builder(Context context) {
            params = new PopupController.PopupParams(context);
        }

        /**
         * 设置popupWindow布局
         *
         * @param layoutResID 布局资源id
         * @return
         */
        public Builder setView(@LayoutRes int layoutResID) {
            params.mView = null;
            params.mLayoutResId = layoutResID;
            return this;
        }

        /**
         * 设置popupWindow布局
         *
         * @param view 布局view
         * @return
         */
        public Builder setView(View view) {
            params.mLayoutResId = 0;
            params.mView = view;
            return this;
        }

        /**
         * 设置子view
         *
         * @param listener
         * @return
         */
        public Builder setViewOnClickListener(IViewInterface listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 设置宽度和高度，如果不设置，默认是wrap_content
         *
         * @param width
         * @param heght
         * @return
         */
        public Builder setWidthAndHeight(int width, int heght) {
            params.mWidth = width;
            params.mHeight = heght;
            return this;
        }

        /**
         * 设置背景灰色程度
         *
         * @param level 0.1f ~1.0f,默认1.0f
         * @return
         */
        public Builder setBackGroundLevel(float level) {
            params.isShowBg = true;
            params.mBgLevel = level;
            return this;
        }

        /**
         * 是否可点击outside消失
         *
         * @param touchable 是否可点击，默认true
         * @return
         */
        public Builder setOutsideTouchable(boolean touchable) {
            params.isTouchable = touchable;
            return this;
        }

        /**
         * 设置动画
         *
         * @param animationStyle
         * @return
         */
        public Builder setAnimationStyle(int animationStyle) {
            params.isShowAnim = true;
            params.mAnimationStyle = animationStyle;
            return this;
        }

        public CommonPopupWindow create() {
            final CommonPopupWindow popupWindow = new CommonPopupWindow(params.mContext);
            params.apply(popupWindow.mPopupController);
            if (null != listener && 0 != params.mLayoutResId) {
                listener.getChildView(popupWindow.mPopupController.mPopupView, params.mLayoutResId);
            }
            measureWidthAndHeight(popupWindow.mPopupController.mPopupView);
            return popupWindow;
        }

        /**
         * 测量View的宽高
         *
         * @param view View
         */
        private void measureWidthAndHeight(View view) {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
        }
    }


}
