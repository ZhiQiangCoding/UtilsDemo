package com.czq.utilsdemo.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.czq.utilsdemo.R;

/**
 * 公司:westsoft
 * 作者:angelCheng
 * 创建时间:2016/9/18 23:11
 * 描述:带删除按钮的EditText
 * 使用：直接将layout中的EditText改为$your package name$.ClearableEditText即可。
 * 如果没有依赖v7包需将其继承的AppCompatEditText改为普通的EditText。
 */
public class EditTextWithDelete extends AppCompatEditText {
    private static final String TAG = "===EditTextWithDelete=";
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private Drawable mClearDrawable;


    public EditTextWithDelete(Context context) {
        this(context, null);
    }

    public EditTextWithDelete(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);

    }

    public EditTextWithDelete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        mClearDrawable = getResources().getDrawable(R.drawable.delete_gray);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisiale(hasFocus() && text.length() > 0);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisiale(focused && length() > 0);
    }


    /**
     * 处理删除事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
            if (drawable != null && event.getRawX() >= (getRight() - getPaddingRight() - drawable.getBounds().width())) {
                setText("");
            }
            this.setFocusable(true);
            this.setFocusableInTouchMode(true);
            this.requestFocus();
        }
        return super.onTouchEvent(event);
    }

    private void setClearIconVisiale(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[DRAWABLE_LEFT], getCompoundDrawables()[DRAWABLE_TOP],
                visible ? mClearDrawable : null, getCompoundDrawables()[DRAWABLE_BOTTOM]
        );
    }

}
