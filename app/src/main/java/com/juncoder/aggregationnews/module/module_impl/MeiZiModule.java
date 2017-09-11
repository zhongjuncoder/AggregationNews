package com.juncoder.aggregationnews.module.module_impl;

import android.content.Context;

import com.juncoder.aggregationnews.callback.ResultCallback;
import com.juncoder.aggregationnews.module.bean.MeiZi;
import com.juncoder.aggregationnews.module.net.RemoteInterface;
import com.juncoder.aggregationnews.module.net.RetrofitUtil;
import com.juncoder.aggregationnews.module.net.SubscribeUtil;

/**
 * Created by zhongjuncoder on 2017/9/11.
 * email:1247660633@qq.com
 */

public class MeiZiModule {

    private SubscribeUtil mSubscribeUtil;

    public MeiZiModule(Context context) {
        mSubscribeUtil = new SubscribeUtil(context);
    }

    public void getMeiZi(int count, int page, ResultCallback<MeiZi> callback) {
        mSubscribeUtil.meiziSubscribe(RetrofitUtil.create(RetrofitUtil.MEIZI_BASE_URL, RemoteInterface.class)
                .getMeiZi("福利", count, page), callback);
    }

    public void dispose() {
        mSubscribeUtil.dispose();
    }
    
}
