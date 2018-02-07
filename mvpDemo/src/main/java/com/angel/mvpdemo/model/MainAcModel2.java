package com.angel.mvpdemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/03
 *     version: 1.0
 *     desc   : 专门给mainActivity获取数据的类
 * </pre>
 */
public class MainAcModel2 implements BaseMode {
    @Override
    public void getData(OnLoadCompleteListener onLoadCompleteListener) {
        //获取数据
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("这是更新后的--第" + i + "条数据");
        }
        onLoadCompleteListener.onLoadComplete(list);

    }
}
