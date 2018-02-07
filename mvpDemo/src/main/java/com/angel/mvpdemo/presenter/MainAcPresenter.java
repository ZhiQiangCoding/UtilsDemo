package com.angel.mvpdemo.presenter;

import com.angel.mvpdemo.model.BaseMode;
import com.angel.mvpdemo.model.MainAcModel;
import com.angel.mvpdemo.model.MainAcModel2;
import com.angel.mvpdemo.view.BaseView;

import java.util.List;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/03
 *     version: 1.0
 *     desc   :
 * </pre>
 */
public class MainAcPresenter extends BasePresenter<BaseView> {
    /**
     * 用于获取数据的model
     */
    private BaseMode baseMode;

    /**
     * 用于获得数据后更新界面的view
     */
    // private BaseView baseView;
//
//    public MainAcPresenter(BaseView baseView) {
//        this.baseView = baseView;
//        baseMode = new MainAcModel();
//    }
    public MainAcPresenter() {
        baseMode = new MainAcModel();
    }

    public MainAcPresenter setModel(int modelType) {
        switch (modelType) {
            case 0:
                baseMode = new MainAcModel();
                break;
            case 1:
                baseMode = new MainAcModel2();
                break;
        }
        return this;

    }

    /**
     * 加载数据，内部通过model去加载
     */
    public void load() {
        baseMode.getData(new BaseMode.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(List<String> lists) {
                //mode加载完成后，数据会传递到这里
                getSubView().showView(lists);
            }
        });
    }

}
