package com.juncoder.aggregationnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juncoder.aggregationnews.R;
import com.juncoder.aggregationnews.activity.ImageActivity;
import com.juncoder.aggregationnews.callback.ResultCallback;
import com.juncoder.aggregationnews.module.bean.MeiZi;
import com.juncoder.aggregationnews.module.module_impl.MeiZiModule;
import com.juncoder.aggregationnews.utils.ImageUtils;

/**
 * Created by zhongjuncoder on 2017/9/11.
 * email:1247660633@qq.com
 */

public class MeiZiFragment extends Fragment {

    private SwipeRefreshLayout mRefreshLayout;

    private MeiZiModule mModule;

    private int mPage = 1;

    private MeiZiAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout, container, false);
        mModule = new MeiZiModule(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mAdapter = new MeiZiAdapter(R.layout.meizi_item);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMeiZi();
            }
        }, recyclerView);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), ImageActivity.class);
                intent.putExtra("url", mAdapter.getItem(position).getUrl());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, "meizi");
                ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
            }
        });
        recyclerView.setAdapter(mAdapter);

        mRefreshLayout = view.findViewById(R.id.refresh_view);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mAdapter.getData().clear();
                getMeiZi();
            }
        });

        getMeiZi();
        mRefreshLayout.setRefreshing(true);
        return view;
    }

    private void getMeiZi() {
        mModule.getMeiZi(10, mPage++, new ResultCallback<MeiZi>() {
            @Override
            public void onSuccess(MeiZi meiZi) {
                if (mAdapter.getData().isEmpty()) {
                    mAdapter.setNewData(meiZi.getResults());
                } else {
                    mAdapter.addData(meiZi.getResults());
                }
                mAdapter.loadMoreComplete();
                stopRefresh();
            }

            @Override
            public void onFail(String message) {
                if (mPage > 1) {
                    mPage--;
                }
                mAdapter.loadMoreFail();
                stopRefresh();
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
        mModule.dispose();
    }

    private class MeiZiAdapter extends BaseQuickAdapter<MeiZi.ResultsBean, BaseViewHolder> {

        MeiZiAdapter(@LayoutRes int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final MeiZi.ResultsBean item) {
            ImageUtils.showPic(MeiZiFragment.this, item.getUrl(), (ImageView) helper.getView(R.id.meizi_pic));
            helper.addOnClickListener(R.id.meizi_pic);
        }

    }

}
