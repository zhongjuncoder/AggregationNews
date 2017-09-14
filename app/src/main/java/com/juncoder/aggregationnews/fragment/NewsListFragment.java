package com.juncoder.aggregationnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juncoder.aggregationnews.R;
import com.juncoder.aggregationnews.callback.ResultCallback;
import com.juncoder.aggregationnews.module.bean.News;
import com.juncoder.aggregationnews.activity.NewsDetailActivity;
import com.juncoder.aggregationnews.module.module_impl.NewsModule;
import com.juncoder.aggregationnews.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 */

public class NewsListFragment extends Fragment {

    private SwipeRefreshLayout mRefreshLayout;

    private ArrayList<News> mNewses;

    private NewsAdapter mAdapter;

    private String mType;

    private NewsModule mNewsModule;

    static NewsListFragment getInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);

        NewsListFragment newsListFragment = new NewsListFragment();
        newsListFragment.setArguments(bundle);
        return newsListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout, container, false);
        mType = getArguments().getString("type");
        if (savedInstanceState != null) {
            mNewses = savedInstanceState.getParcelableArrayList("news");
        }

        mNewsModule = new NewsModule(getActivity());

        mRefreshLayout = view.findViewById(R.id.refresh_view);
        mRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNews();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new NewsAdapter(mNewses);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("url", mNewses.get(position).getUrl());
                startActivity(intent);
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        if (mNewses == null || mNewses.isEmpty()) {
            mRefreshLayout.setRefreshing(true);
            getNews();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNewses != null) {
            outState.putParcelableArrayList("news", mNewses);
        }
    }

    private void getNews() {
        mNewsModule.getNews(mType, new ResultCallback<List<News>>() {
            @Override
            public void onSuccess(List<News> list) {
                showNews((ArrayList<News>) list);
                stopRefresh();
            }

            @Override
            public void onFail(String message) {
                showToast(message);
                stopRefresh();
            }
        });
    }

    public void showNews(ArrayList<News> list) {
        mNewses = list;
        mAdapter.setNewData(list);
    }

    public void stopRefresh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        mNewsModule.dispose();
    }

    private class NewsAdapter extends BaseMultiItemQuickAdapter<News, BaseViewHolder> {

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        NewsAdapter(List<News> data) {
            super(data);
            addItemType(R.layout.news_item1, R.layout.news_item1);
            addItemType(R.layout.news_item2, R.layout.news_item2);
            addItemType(R.layout.news_item3, R.layout.news_item3);
        }

        @Override
        protected void convert(BaseViewHolder helper, News item) {
            switch (helper.getItemViewType()) {
                case R.layout.news_item1:
                    helper.setText(R.id.news_title1, item.getTitle())
                            .setText(R.id.news_author1, item.getAuthor_name())
                            .setText(R.id.news_date1, item.getDate().split(" ")[1])
                            .addOnClickListener(R.id.news1);
                    ImageUtils.showPic(NewsListFragment.this, item.getThumbnail_pic_s(), (ImageView) helper.getView(R.id.news_title_pic1));
                    break;
                case R.layout.news_item2:
                    helper.setText(R.id.news_title2, item.getTitle())
                            .setText(R.id.news_author2, item.getAuthor_name())
                            .setText(R.id.news_date2, item.getDate())
                            .addOnClickListener(R.id.news2);
                    ImageUtils.showPic(NewsListFragment.this, item.getThumbnail_pic_s(), (ImageView) helper.getView(R.id.news_title_pic2_one));
                    ImageUtils.showPic(NewsListFragment.this, item.getThumbnail_pic_s02(), (ImageView) helper.getView(R.id.news_title_pic2_two));
                    break;
                case R.layout.news_item3:
                    helper.setText(R.id.news_title3, item.getTitle())
                            .setText(R.id.news_author3, item.getAuthor_name())
                            .setText(R.id.news_date3, item.getDate())
                            .addOnClickListener(R.id.news3);
                    ImageUtils.showPic(NewsListFragment.this, item.getThumbnail_pic_s(), (ImageView) helper.getView(R.id.news_title_pic3_one));
                    ImageUtils.showPic(NewsListFragment.this, item.getThumbnail_pic_s02(), (ImageView) helper.getView(R.id.news_title_pic3_two));
                    ImageUtils.showPic(NewsListFragment.this, item.getThumbnail_pic_s03(), (ImageView) helper.getView(R.id.news_title_pic3_three));
                    break;
                default:
                    break;
            }
        }
    }

}
