package com.example.buxiaohui.myapplication.download;

/**
 * Created by buxiaohui on 17/10/2016.
 */


import android.content.Context;
import android.util.Log;

import com.example.buxiaohui.myapplication.download.factory.DownLoadFileFactory;
import com.example.buxiaohui.myapplication.download.factory.HeaderAddFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DownLoadClient {

    private String TAG = "LeopardClient";

    private Context mContext;

    private boolean isJson = false;
    private String baseUrl = "";
    private static final int TIME_OUT = 30 * 1000;
    private static final String CONTENT_TYPE = "application/json";

    private FileApi fileApi;
    private Retrofit retrofit;
    private Retrofit.Builder retrofitBuilder;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder okHttpClientBuilder;

    private MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");
    private MediaType dataMediaType = MediaType.parse("multipart/form-data");

    public DownLoadClient(FileApi fileApi, Retrofit retrofit, Retrofit.Builder retrofitBuilder, OkHttpClient okHttpClient, OkHttpClient.Builder okHttpClientBuilder, boolean isJson) {
        this.fileApi = fileApi;
        this.retrofit = retrofit;
        this.retrofitBuilder = retrofitBuilder;
        this.okHttpClient = okHttpClient;
        this.okHttpClientBuilder = okHttpClientBuilder;
        this.isJson = isJson;
    }


    public void downLoadFile(final DownloadItem downloadItem, FileReponseListener callback, DownloadTask downloadTask) {
        // call 缓存实现
//        serverApi.downloadFile(downloadInfo.getDownloadUrl())
//                .compose(schedulersTransformer)
//                .subscribe(new DownLoadSubscriber(callback,downloadInfo,task));
        Observable.create(new Observable.OnSubscribe<ResponseBody>() {
            @Override
            public void call(Subscriber<? super ResponseBody> subscriber) {
                if (downloadItem.getState() == DownloadTask.STATE_PAUSE) {
                    return;
                }
                Request request = new Request.Builder().url(downloadItem.getFileSourceUrl()).build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (downloadItem.getFileLength() <= 0) {
                        downloadItem.setFileLength(response.body().contentLength());
                    }
                    downloadItem.getDownloadTask().writeCache(response.body().byteStream());

                    //HttpDbUtil.instance.updateState(downloadItem);
                    subscriber.onNext(response.body());
                } catch (IOException e) {

                    downloadItem.setState(DownloadTask.STATE_ERROR);
                    // TODO: 2016/8/31 更新数据库
                    //HttpDbUtil.instance.updateState(downloadItem);
                    e.printStackTrace();
                }
            }
        })
                //避免doing too much work on its main thread.
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DownloadSubscriber(callback, downloadItem, downloadTask));
    }

//    final Observable.Transformer schedulersTransformer = new Observable.Transformer() {
//        @Override
//        public Object call(Object observable) {
//            return ((Observable) observable)
//                    .subscribeOn(Schedulers.io())
//                    .unsubscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    ;
//        }
//    };

    public static class Builder {

        private String baseUrl = "http://127.0.0.1";
        private int TIME_OUT = 30 * 1000;
        private boolean isLog = true;
        private boolean isJson = false;

        private FileApi fileApi;
        private Retrofit retrofit;
        private Retrofit.Builder retrofitBuilder;
        private OkHttpClient okHttpClient;
        private OkHttpClient.Builder okHttpClientBuilder;

        //Factory
        private GsonConverterFactory gsonConverterFactory;
        private RxJavaCallAdapterFactory javaCallAdapterFactory;
        //        private RequestJsonFactory requestJsonFactory;
//        private UploadFileFactory uploadFileFactory;
        private DownLoadFileFactory downLoadFileFactory;
//        private RequestComFactory requestComFactory;

        public Builder() {
            retrofitBuilder = new Retrofit.Builder();
            okHttpClientBuilder = new OkHttpClient.Builder();
        }

        public Builder baseUrl(String url) {
            baseUrl = url;
            return this;
        }

        public Builder timeOut(int TIME_OUT) {
            this.TIME_OUT = TIME_OUT;
            return this;
        }

        public Builder isLog(boolean isLog) {
            this.isLog = isLog;
            return this;
        }


        public Builder addGsonConverterFactory(GsonConverterFactory factory) {
            this.gsonConverterFactory = factory;
            return this;
        }

        public Builder addRxJavaCallAdapterFactory(RxJavaCallAdapterFactory factory) {
            this.javaCallAdapterFactory = factory;
            return this;
        }


        public Builder addDownLoadFileFactory(DownLoadFileFactory factory) {
            this.downLoadFileFactory = factory;
            return this;
        }

        public Builder client(OkHttpClient client) {
            this.okHttpClient = client;
            return this;
        }

        public Builder addHeader(HashMap<String, String> headers) {
            okHttpClientBuilder.addInterceptor(new HeaderAddFactory(headers));
            return this;
        }

        public DownLoadClient build() {
            if (this.downLoadFileFactory != null) {
                okHttpClientBuilder.addInterceptor(this.downLoadFileFactory);
            }

            okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
            if (isLog)
                okHttpClientBuilder.addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS));

            okHttpClient = okHttpClientBuilder.build();

            // retrofit
            if (this.javaCallAdapterFactory != null) {
                retrofitBuilder.addCallAdapterFactory(javaCallAdapterFactory);
            }

            if (this.gsonConverterFactory != null) {
                retrofitBuilder.addConverterFactory(gsonConverterFactory);
            }
            if (baseUrl.startsWith("http"))
                retrofitBuilder.baseUrl(baseUrl);
            retrofitBuilder.client(okHttpClient);
            retrofit = retrofitBuilder.build();

            fileApi = retrofit.create(FileApi.class);

            return new DownLoadClient(fileApi, retrofit, retrofitBuilder, okHttpClient, okHttpClientBuilder, isJson);
        }

    }


    private static String fiterURLFromJSON(String params) {
        try {
            JSONObject jsonObject = new JSONObject(params);
            if (jsonObject.has("ruqestURL"))
                jsonObject.remove("ruqestURL");
            return jsonObject.toString();
        } catch (JSONException e) {
            return "";
        }
    }

    private static Map<String, Object> fiterURLFromRequestParams(Map<String, Object> params) {
        if (params.containsKey("ruqestURL"))
            params.remove("ruqestURL");
        return params;
    }

}


