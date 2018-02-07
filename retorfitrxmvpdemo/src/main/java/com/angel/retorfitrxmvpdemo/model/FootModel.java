package com.angel.retorfitrxmvpdemo.model;

import com.angel.retorfitrxmvpdemo.api.ServcerManager;
import com.angel.retorfitrxmvpdemo.api.Server;
import com.angel.retorfitrxmvpdemo.bean.response.Foot;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/04
 *     version: 1.0
 *     desc   : 获取食物的model
 * </pre>
 */
public class FootModel implements MyBaseModel<Foot> {

    @Override
    public void loadData(Action1<Foot> action1) {
        //请求数据
        Server server = ServcerManager.getApi(Server.class);
        Observable<Foot> footObservable = server.getFootDataible(1, 20,1).asObservable();
        footObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(action1);
    }
}
