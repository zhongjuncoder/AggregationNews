package com.juncoder.aggregationnews.module.net;

import com.juncoder.aggregationnews.module.bean.BaseResult;
import com.juncoder.aggregationnews.module.bean.Joke;
import com.juncoder.aggregationnews.module.bean.MeiZi;
import com.juncoder.aggregationnews.module.bean.News;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 */

public interface RemoteInterface {

    @GET("toutiao/index")
    Observable<BaseResult<News>> getNews(@Query("type") String type, @Query("key") String key);

    @GET("joke/content/text.from?key=ae240f7fba620fc370b803566654949e")
    Observable<Joke> getJokes(@Query("page") int page, @Query("pagesize") int pageSize);

    @GET("{category}/{count}/{page}")
    Observable<MeiZi> getMeiZi(@Path("category") String category, @Path("count") int count, @Path("page") int page);
}
