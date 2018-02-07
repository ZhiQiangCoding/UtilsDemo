package com.angel.retorfitrxmvpdemo.model;

        import rx.functions.Action1;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/04
 *     version: 1.0
 *     desc   : xxxx描述
 * </pre>
 */
public interface MyBaseModel<T> extends BaseModel {
    void loadData(Action1<T> action1);
}
