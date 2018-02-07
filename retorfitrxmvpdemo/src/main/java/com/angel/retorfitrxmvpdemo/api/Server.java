package com.angel.retorfitrxmvpdemo.api;

import com.angel.retorfitrxmvpdemo.bean.response.Foot;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/04
 *     version: 1.0
 *     desc   : xxxx描述
 * </pre>
 */
public interface Server {

    /**
     * 食品列表接口-食物分类列表API接口
     * http://www.tngou.net/api/food/list
     *
     * @param page 请求页数，默认page=1
     * @param rows 每页返回的条数，默认rows=20
     * @param id   分类ID，默认返回的是全部。这里的ID就是指分类的ID
     * @return
     */
    @GET("food/list")
    Observable<Foot> getFootDataible(@Query("page") int page,
                                     @Query("rows") int rows,
                                     @Query("id") int id);
}
