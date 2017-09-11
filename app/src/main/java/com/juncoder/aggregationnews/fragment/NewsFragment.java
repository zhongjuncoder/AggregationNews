package com.juncoder.aggregationnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juncoder.aggregationnews.R;
import com.juncoder.aggregationnews.module.bean.Type;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private List<Type> mTypeList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment_layout, container, false);
        initData();

        ViewPager viewPager = view.findViewById(R.id.news_view_pager);
        HomeNewsFragmentAdapter adapter = new HomeNewsFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.top_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }


    private void initData() {
        mTypeList = new ArrayList<>();
        mTypeList.add(new Type("top", "头条"));
        mTypeList.add(new Type("shehui", "社会"));
        mTypeList.add(new Type("guonei", "国内"));
        mTypeList.add(new Type("guoji", "国际"));
        mTypeList.add(new Type("yule", "娱乐"));
        mTypeList.add(new Type("tiyu", "体育"));
        mTypeList.add(new Type("junshi", "军事"));
        mTypeList.add(new Type("keji", "科技"));
    }

    private class HomeNewsFragmentAdapter extends FragmentStatePagerAdapter {

        HomeNewsFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        //每次获取的时候都是new一个fragment，里面的presenter没有保存，所以需要重新设置
        @Override
        public Fragment getItem(int position) {
            return NewsListFragment.getInstance(mTypeList.get(position).getEnglishType());
        }

        @Override
        public int getCount() {
            return mTypeList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTypeList.get(position).getChineseType();
        }
    }
}
