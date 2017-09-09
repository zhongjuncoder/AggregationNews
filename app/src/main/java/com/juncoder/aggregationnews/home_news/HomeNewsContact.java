package com.juncoder.aggregationnews.home_news;

import com.juncoder.aggregationnews.module.bean.News;

import java.util.ArrayList;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 */

interface HomeNewsContact {

    interface HomeNewsView {

        void showNews(ArrayList<News> list);

        void stopRefresh();

        void showToast(String message);

        void setPresenter(HomeNewsPresenter presenter);
    }

    interface HomeNewsPresenter {
        void getNews(String type);

        void setView(HomeNewsContact.HomeNewsView view);

        void dispose();
    }

}
