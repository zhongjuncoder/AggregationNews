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

    public static final String News_BASE_URL = "http://v.juhe.cn/";

    public static final String JOKE_BASE_URL = "http://japi.juhe.cn/";

    public static final String MEIZI_BASE_URL = "http://gank.io/api/data/";

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .build();

    public static <T> T create(String baseUrl, Class<T> tClass) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OK_HTTP_CLIENT)
                .build().create(tClass);
    }

}
