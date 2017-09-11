package com.juncoder.aggregationnews.module.module_impl;

import android.content.Context;

import com.juncoder.aggregationnews.callback.ResultCallback;
import com.juncoder.aggregationnews.module.bean.Joke;
import com.juncoder.aggregationnews.module.net.RemoteInterface;
import com.juncoder.aggregationnews.module.net.RetrofitUtil;
import com.juncoder.aggregationnews.module.net.SubscribeUtil;

/**
 * Created by zhongjuncoder on 2017/9/11.
 * email:1247660633@qq.com
 */

public class JokeModule {

    private SubscribeUtil mSubscribeUtil;

    public JokeModule(Context context) {
        mSubscribeUtil = new SubscribeUtil(context);
    }

    public void getJokes(int page, int pageSize, ResultCallback<Joke> callback) {
        mSubscribeUtil.jokeSubscribe(RetrofitUtil.create(RetrofitUtil.JOKE_BASE_URL, RemoteInterface.class)
                .getJokes(page, pageSize), callback);
    }

    public void dispose() {
        mSubscribeUtil.dispose();
    }

}
