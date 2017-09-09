package com.juncoder.aggregationnews.callback;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 */

public interface ResultCallback<T> {

    void onSuccess(T t);

    void onFail(String message);
    
}
