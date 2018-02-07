package com.angel.retorfitrxmvpdemo.presenter;

import com.angel.retorfitrxmvpdemo.bean.response.Foot;
import com.angel.retorfitrxmvpdemo.model.FootModel;
import com.angel.retorfitrxmvpdemo.view.iview.FootView;

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
public class FootPresenter extends BasePresenter<FootView> {
    private FootModel footModel;

    public FootPresenter() {
        footModel=new FootModel();
    }

    public void load() {
        footModel.loadData(new Action1<Foot>() {
            @Override
            public void call(Foot foot) {
                //获得的数据
                getSubView().showData(foot.getTngou());
            }
        });
    }

}
