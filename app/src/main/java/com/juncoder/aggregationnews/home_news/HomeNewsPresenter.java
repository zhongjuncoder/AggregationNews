package com.juncoder.aggregationnews.home_news;

import android.content.Context;

import com.juncoder.aggregationnews.callback.ResultCallback;
import com.juncoder.aggregationnews.module.bean.News;
import com.juncoder.aggregationnews.module.news_module.HomeNewsModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 */

class HomeNewsPresenter implements HomeNewsContact.HomeNewsPresenter {

    private HomeNewsModule mModule;

    private HomeNewsContact.HomeNewsView mView;

    HomeNewsPresenter(HomeNewsContact.HomeNewsView view, Context context) {
        mView = view;
        mView.setPresenter(this);
        mModule = new HomeNewsModule(context);
    }

    @Override
    public void getNews(String type) {
        mModule.getNews(type, new ResultCallback<List<News>>() {
            @Override
            public void onSuccess(List<News> list) {
                mView.stopRefresh();
                mView.showNews((ArrayList<News>) list);
            }

            @Override
            public void onFail(String message) {
                mView.stopRefresh();
                mView.showToast(message);
            }
        });
    }

    @Override
    public void setView(HomeNewsContact.HomeNewsView view) {
        mView = view;
    }

    @Override
    public void dispose() {
        mModule.dispose();
    }

}
