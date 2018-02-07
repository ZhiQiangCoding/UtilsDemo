package com.czq.utilsdemo.bean;

/**
 * 公司：westsoft
 * 作者：程志强
 * 时间：2017/2/16 21:22
 * 描述：实体共性
 */

public class BaseBean {
    private int success;//成功
    private String msg;//提示信息
    private int error;//失败

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
