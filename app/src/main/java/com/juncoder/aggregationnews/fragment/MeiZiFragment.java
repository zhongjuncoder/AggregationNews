package com.juncoder.aggregationnews.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
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
import com.juncoder.aggregationnews.callback.ResultCallback;
import com.juncoder.aggregationnews.module.bean.MeiZi;
import com.juncoder.aggregationnews.module.module_impl.MeiZiModule;
import com.juncoder.aggregationnews.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongjuncoder on 2017/9/11.
 * email:1247660633@qq.com
 */

public class MeiZiFragment extends Fragment {

    private SwipeRefreshLayout mRefreshLayout;

    private MeiZiModule mModule;

    private int mPage = 1;

    private MeiZiAdapter mAdapter;

    private List<MeiZi.ResultsBean> mBeans;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout, container, false);
        mModule = new MeiZiModule(getActivity());
        mBeans = new ArrayList<>();
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
        mModule.getMeiZi(20, mPage++, new ResultCallback<MeiZi>() {
            @Override
            public void onSuccess(MeiZi meiZi) {
                for (int i = 0, size = meiZi.getResults().size(); i < size; i++) {
                    meiZi.getResults().get(i).setUsed(false);
                }
                mBeans.addAll(meiZi.getResults());
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

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.meizi_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ImageUtils.showPic(getActivity(), mBeans.get(position).getUrl(), holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mBeans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView mImageView;

            ViewHolder(View itemView) {
                super(itemView);
                mImageView = itemView.findViewById(R.id.meizi_pic);
            }
        }
    }

    private class MeiZiAdapter extends BaseQuickAdapter<MeiZi.ResultsBean, BaseViewHolder> {

        MeiZiAdapter(@LayoutRes int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final MeiZi.ResultsBean item) {
            if (!item.isUsed()) {
                ImageUtils.showPic(getActivity(), item.getUrl(), (ImageView) helper.getView(R.id.meizi_pic));
                item.setUsed(true);
            } else {
                item.setUsed(false);
            } 
            /*Glide.with(getActivity().getApplicationContext())
                    .load(item.getUrl())
                    .thumbnail(0.5f)
                    .into(new ImageViewTarget<Drawable>((ImageView) helper.getView(R.id.meizi_pic)) {
                        @Override
                        protected void setResource(@Nullable Drawable resource) {
                            helper.setImageDrawable(R.id.meizi_pic, resource);
                        }

                        @Override
                        public void setRequest(@Nullable Request request) {
                            super.setRequest(request);
                            helper.getView(R.id.meizi_pic).setTag(R.id.image_tag,request);
                        }

                        @Nullable
                        @Override
                        public Request getRequest() {
                            return (Request) helper.getView(R.id.meizi_pic).getTag();
                        }
                    });*/
        }

    }

}
