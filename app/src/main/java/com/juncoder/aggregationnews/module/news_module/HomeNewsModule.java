package com.juncoder.aggregationnews.module.news_module;

import android.content.Context;

import com.juncoder.aggregationnews.callback.ResultCallback;
import com.juncoder.aggregationnews.module.bean.News;
import com.juncoder.aggregationnews.module.net.NewsInterface;
import com.juncoder.aggregationnews.module.net.RetrofitUtil;
import com.juncoder.aggregationnews.module.net.SubscribeUtil;

import java.util.List;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 */

public class HomeNewsModule {

    private SubscribeUtil mSubscribeUtil;

    public HomeNewsModule(Context context) {
        mSubscribeUtil = new SubscribeUtil(context);
    }

    public void getNews(String type, ResultCallback<List<News>> callback) {
        mSubscribeUtil.subscribe(RetrofitUtil.create(NewsInterface.class).getNews(type, "429c0170f14288eb44a3786c00048f7f"), callback);
    }

    public void dispose() {
        mSubscribeUtil.dispose();
    }

}
