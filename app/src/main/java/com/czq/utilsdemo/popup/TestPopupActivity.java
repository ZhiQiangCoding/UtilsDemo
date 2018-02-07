package com.czq.utilsdemo.popup;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.czq.utilsdemo.R;
import com.czq.utilsdemo.utils.ToastUtils;

public class TestPopupActivity extends AppCompatActivity {

    private CommonPopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_popup);

        findViewById(R.id.btn_show_below).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDownPopup(v);
            }
        });
    }

    /**
     * 显示在view的下方
     *
     * @param v
     */
    private void showDownPopup(View v) {
        if (null != mPopupWindow && mPopupWindow.isShowing()) return;
        TextView tv = new TextView(this);
        tv.setText("下方");
        tv.setTextColor(Color.RED);
        mPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(tv)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setViewOnClickListener(new CommonPopupWindow.IViewInterface() {
                    @Override
                    public void getChildView(View view, @LayoutRes int layoutResId) {
                        ToastUtils.show(TestPopupActivity.this, "点击了");
                    }
                })
                .create();
        mPopupWindow.showAsDropDown(v);
    }
}
