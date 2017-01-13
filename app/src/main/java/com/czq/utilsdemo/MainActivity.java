package com.czq.utilsdemo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.czq.utilsdemo.activity.BaseActivity;
import com.czq.utilsdemo.activity.TestBGARefreshlayoutActivity;
import com.czq.utilsdemo.activity.TestListViewActivity;
import com.czq.utilsdemo.utils.LogUtils;
import com.czq.utilsdemo.utils.ToastUtils;
import com.czq.utilsdemo.widget.EditTextWithDelete;

public class MainActivity extends BaseActivity {

    private TextView tv;//设置通过逻辑代码空值tv中图标的大小
    private LinearLayout ll_dayNight;//设置夜间模式
    private EditTextWithDelete et_phone;//待删除的edittext
    private TextView tv_test_adapter;//测试 万能适配器
    private TextView tv_bgaRefreshlayout;//测试 下拉刷新下拉加载更多

    /**
     * 初始化布局以及view控件
     *
     * @param savedInstanceState
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        tv = getViewById(R.id.tv_1);
        ll_dayNight = getViewById(R.id.ll_dayNight);
        et_phone = getViewById(R.id.et_phone);
        tv_test_adapter = getViewById(R.id.tv_test_adapter);
        tv_bgaRefreshlayout=getViewById(R.id.tv_bgaRefreshlayout);

    }

    /**
     * 给view控件添加监听事件
     */
    @Override
    protected void setListener() {
        ll_dayNight.setOnClickListener(this);
        et_phone.setOnClickListener(this);
        tv_test_adapter.setOnClickListener(this);
        tv_bgaRefreshlayout.setOnClickListener(this);
    }

    /**
     * 处理业务逻辑，状态恢复等操作
     *
     * @param savedInstanceState
     */
    @Override
    protected void processLogic(Bundle savedInstanceState) {
        //获取tv它前后左右的图片，数组下标0-3，依次是：左上右下
        Drawable[] drawables = tv.getCompoundDrawables();
        drawables[0].setBounds(0, 0, 30, 30);
        tv.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_dayNight://设置夜间模式
                LogUtils.e("==" + et_phone.getText().toString());
                ToastUtils.show(App.getInstance(), "设置夜间模式");
                break;
            case R.id.et_phone://获取输入框的焦点
                getEditTextFocus(et_phone);
                break;
            case R.id.tv_test_adapter://测试万能适配器

                ToastUtils.show(App.getInstance(), "万能适配器");
                startActivity(new Intent(mActivity, TestListViewActivity.class));
                break;
            case R.id.tv_bgaRefreshlayout://测试下拉刷新上拉加载更多
                ToastUtils.show(App.getInstance(), "测试下拉刷新上拉加载更多");
                startActivity(new Intent(mActivity, TestBGARefreshlayoutActivity.class));
                break;
        }
    }
}
