package com.juncoder.aggregationnews.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juncoder.aggregationnews.R;
import com.juncoder.aggregationnews.callback.ResultCallback;
import com.juncoder.aggregationnews.module.bean.Joke;
import com.juncoder.aggregationnews.module.module_impl.JokeModule;

/**
 * Created by zhongjuncoder on 2017/9/11.
 * email:1247660633@qq.com
 */

public class JokeFragment extends Fragment {

    private SwipeRefreshLayout mRefreshLayout;

    private JokeModule mJokeModule;

    private int mPage = 1;

    private JokeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout, container, false);
        mJokeModule = new JokeModule(getActivity());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new JokeAdapter(R.layout.joke_item);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getJoke();
            }
        }, recyclerView);
        recyclerView.setAdapter(mAdapter);

        mRefreshLayout = view.findViewById(R.id.refresh_view);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mAdapter.getData().clear();
                getJoke();
            }
        });

        if (mAdapter.getData().isEmpty()) {
            mRefreshLayout.setRefreshing(true);
            getJoke();
        }

        return view;
    }

    private void getJoke() {
        mJokeModule.getJokes(mPage++, 20, new ResultCallback<Joke>() {
            @Override
            public void onSuccess(Joke joke) {
                if (mAdapter.getData().isEmpty()) {
                    mAdapter.setNewData(joke.getResult().getData());
                } else {
                    mAdapter.addData(joke.getResult().getData());
                }
                mAdapter.loadMoreComplete();
                stopRefresh();
            }

            @Override
            public void onFail(String message) {
                if (mPage > 1) {
                    mPage--;
                }
                stopRefresh();
                mAdapter.loadMoreFail();
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void stopRefresh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mJokeModule.dispose();
    }

    private class JokeAdapter extends BaseQuickAdapter<Joke.ResultBean.DataBean, BaseViewHolder> {

        JokeAdapter(@LayoutRes int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Joke.ResultBean.DataBean item) {
            helper.setText(R.id.joke_content, item.getContent());
            helper.setText(R.id.joke_date, item.getUpdatetime());
        }

    }
}
