package com.example.buxiaohui.myapplication.net;

import com.example.buxiaohui.myapplication.bean.TestBean;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by buxiaohui on 14/10/2016.
 */

public interface NetApi {

    @GET("api/v1")
    Observable<TestBean> getTestBeans(@Query("id") String id);
}
