package com.czq.utilsdemo.enums;

/**
 * ================================================
 * 作    者：程志强
 * 邮    箱：zhiqiang8838@163.com
 * 版    本：1.0.0
 * 创建日期：2018/02/07  18:03
 * 包    名：com.yh.baselibrary.net
 * 描    述：网络请求code枚举
 * 修订历史：key为1000——9999的区间编码
 * ================================================
 */
public enum NetCodeEnum implements IBaseStrEnum {
    SUCCESS("200", "请求成功！"),
    FAILURE("10000", "(App)请求失败！"),
    PERMISSION("10001", "(App)请添加相应的权限后重试！"),
    NETWORK("10002", "(App)网络连接失败，请检查您的网络后重试！"),
    TIMEOUT("10003", "(App)连接超时，请重试!"),
    CONNECT("10004", "(App)未能连接到服务器，请检查后重试!"),

    ;

    private String mKey;
    private String mValue;

    NetCodeEnum(String key, String value) {
        this.mKey = key;
        this.mValue = value;
    }

    @Override
    public String getKey() {
        return this.mKey;
    }

    @Override
    public String getValue() {
        return this.mValue;
    }
}
