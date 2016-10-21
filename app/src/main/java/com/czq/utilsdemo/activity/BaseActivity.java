package com.czq.utilsdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.czq.utilsdemo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected Context mContext;//上下文环境
    protected Activity mActivity;//当前窗口
    private SweetAlertDialog mSweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        initView(savedInstanceState);
        setListener();
        processLogic(savedInstanceState);
    }

    /**
     * 查找控件
     *
     * @param id   控件的Id
     * @param <VT> view类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }

    /**
     * 初始化布局以及view控件
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 给view控件添加监听事件
     */
    protected abstract void setListener();

    /**
     * 处理业务逻辑，状态恢复等操作
     *
     * @param savedInstanceState
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    /**
     * 需要处理点击时间时，重写该方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * 数据加载的对话框
     *
     * @param msg
     */
    protected void showLoadingDialog(String msg) {
        if (mSweetAlertDialog == null) {
            mSweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
            mSweetAlertDialog.getProgressHelper().setBarColor(ContextCompat.getColor(mContext, R.color.red_dark));
            //mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            mSweetAlertDialog.setTitleText(msg);
            mSweetAlertDialog.setCancelable(false);
        }
        mSweetAlertDialog.show();
    }

    protected void showLoadingDialog(@StringRes int id) {
        showLoadingDialog(getResources().getString(id));
    }

    /**
     * 取消进度对话框
     */
    protected void dismissLoadingDialog() {
        if (mSweetAlertDialog != null || mSweetAlertDialog.isShowing()) {
            mSweetAlertDialog.dismiss();
        }
    }

    protected void getEditTextFocus(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.findFocus();
    }
}
