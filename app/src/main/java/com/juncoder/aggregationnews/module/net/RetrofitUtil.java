package com.juncoder.aggregationnews.module.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 */

public class RetrofitUtil {

    private static final String BASE_URL = "http://v.juhe.cn/toutiao/";

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .build();

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OK_HTTP_CLIENT)
            .build();

    public static <T> T create(Class<T> tClass) {
        return RETROFIT.create(tClass);
    }

}
