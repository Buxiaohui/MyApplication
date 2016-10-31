package com.example.buxiaohui.myapplication.download;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by buxiaohui on 17/10/2016.
 */

public interface FileApi {
    @GET
    Observable<ResponseBody> get(@Url String url);
}
