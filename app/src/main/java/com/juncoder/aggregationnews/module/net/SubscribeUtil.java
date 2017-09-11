package com.juncoder.aggregationnews.module.net;

import android.content.Context;
import android.util.Log;

import com.juncoder.aggregationnews.callback.ResultCallback;
import com.juncoder.aggregationnews.module.bean.BaseResult;
import com.juncoder.aggregationnews.module.bean.Joke;
import com.juncoder.aggregationnews.module.bean.MeiZi;
import com.juncoder.aggregationnews.utils.NetworkUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 */

public class SubscribeUtil {

    private static final String TAG = "SubscribeUtil";

    private Context mContext;

    private CompositeDisposable mCompositeDisposable;

    public SubscribeUtil(Context context) {
        mContext = context.getApplicationContext();
        mCompositeDisposable = new CompositeDisposable();
    }

    public <T, D extends BaseResult<T>> void subscribe(Observable<D> observable, final ResultCallback<List<T>> callback) {

        if (NetworkUtils.isAvailable(mContext)) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<D>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            mCompositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(@NonNull D d) {
                            if (d.getResult().getStat().equals("1")) {
                                callback.onSuccess(d.getResult().getData());
                            } else {
                                callback.onFail(d.getReason());
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            if (e instanceof SocketTimeoutException) {
                                Log.d(TAG, "onError: SocketTimeoutException");
                                callback.onFail("连接超时，请重试");
                            } else if (e instanceof ConnectException) {
                                Log.d(TAG, "onError: BluetoothConnectException");
                                callback.onFail("连接超时，请重试");
                            } else if (e instanceof HttpException) {
                                Log.d(TAG, "onError: HttpException");
                                callback.onFail("请求异常，请重试");
                            } else if (e instanceof RuntimeException) {
                                Log.d(TAG, "onError: RuntimeException");
                                callback.onFail("运行错误，请重试");
                            } else {
                                Log.d(TAG, "onError: unknown exception");
                                callback.onFail("网络异常，请重试");
                            }

                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else {
            callback.onFail("网络不可用!请检查网络设置");
        }

    }

    public void jokeSubscribe(Observable<Joke> observable, final ResultCallback<Joke> callback) {
        if (NetworkUtils.isAvailable(mContext)) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Joke>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            mCompositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(@NonNull Joke joke) {
                            if (joke.getError_code() == 0) {
                                callback.onSuccess(joke);
                            } else {
                                callback.onFail(joke.getReason());
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            callback.onFail("请求异常，请稍后再试");
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            callback.onFail("网络不可用!请检查网络设置");
        }
    }

    public void meiziSubscribe(Observable<MeiZi> observable, final ResultCallback<MeiZi> callback) {
        if (NetworkUtils.isAvailable(mContext)) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<MeiZi>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            mCompositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(@NonNull MeiZi meizi) {
                            if (!meizi.isError()) {
                                callback.onSuccess(meizi);
                            } else {
                                callback.onFail("请求异常，请稍后再试");
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            callback.onFail("请求异常，请稍后再试");
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            callback.onFail("网络不可用!请检查网络设置");
        }
    }

    private <T> void transferErrorCode(String code, ResultCallback<T> callback) {
        switch (code) {
            case "10001":
                callback.onFail("错误的请求KEY");
                break;
            case "10002":
                callback.onFail("该KEY无请求权限");
                break;
            case "10003":
                callback.onFail("KEY过期");
                break;
            case "10004":
                callback.onFail("错误的OPENID");
                break;
            case "10005":
                callback.onFail("应用未审核超时，请提交认证");
                break;
            case "10007":
                callback.onFail("未知的请求源");
                break;
            case "10008":
                callback.onFail("被禁止的IP");
                break;
            case "10009":
                callback.onFail("被禁止的KEY");
                break;
            case "10011":
                callback.onFail("当前IP请求超过限制");
                break;
            case "10012":
                callback.onFail("请求超过次数限制");
                break;
            case "10013":
                callback.onFail("测试KEY超过请求限制");
                break;
            case "10014":
                callback.onFail("系统内部异常");
                break;
            case "10020":
                callback.onFail("接口维护");
                break;
            case "10021":
                callback.onFail("接口停用");
                break;
            default:
                callback.onFail("未知错误");
        }
    }

    public void dispose() {
        mCompositeDisposable.clear();
    }
}
