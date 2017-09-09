package com.juncoder.aggregationnews.module.net;

import com.juncoder.aggregationnews.module.bean.BaseResult;
import com.juncoder.aggregationnews.module.bean.News;
import com.juncoder.aggregationnews.module.bean.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 */

public interface NewsInterface {

    @GET("index")
    Observable<BaseResult<List<News>>> getNews(@Query("type") String type, @Query("key") String key);

    @GET("index")
    Observable<Result> getNew(@Query("type") String type, @Query("key") String key);
}
