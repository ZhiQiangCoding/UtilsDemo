package com.czq.utilsdemo.enums;

/**
 * ================================================
 * 作    者：程志强
 * 邮    箱：zhiqiang8838@163.com
 * 版    本：1.0.0
 * 创建日期：2018/02/08  09:44
 * 包    名：com.yh.baselibrary.enums
 * 描    述：请求方法的枚举
 * 修订历史：
 * ================================================
 */
public enum RequestMethodEnum implements IBaseIntEnum {
    /**
     * GET请求
     */
    GET(1, "GET"),
    /**
     * POST请求
     */
    POST(2, "POST"),
    /**
     * PUT请求
     */
    PUT(3, "PUT"),
    /**
     * DELETE请求
     */
    DELETE(4, "DELETE"),;

    private int mKey;
    private String mValue;

    RequestMethodEnum(int key, String value) {
        this.mKey = key;
        this.mValue = value;
    }

    @Override
    public int getKey() {
        return this.mKey;
    }

    @Override
    public String getValue() {
        return this.mValue;
    }

    public static RequestMethodEnum fromKey(int key) {
        if (0 == key) {
            throw new IllegalArgumentException("[==key不存在，请重新确认key==]");
        }
        for (RequestMethodEnum methodEnum : values()) {
            if (methodEnum.getKey() == key) {
                return methodEnum;
            }
        }
        return null;
    }
}
