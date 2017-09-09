package com.juncoder.aggregationnews.home_news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.juncoder.aggregationnews.R;
import com.juncoder.aggregationnews.module.bean.Type;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Type> mTypeList;

    private SparseArray<HomeNewsContact.HomeNewsPresenter> mPresenters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        ViewPager viewPager = findViewById(R.id.home_view_pager);
        HomeNewsFragmentAdapter adapter = new HomeNewsFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.home_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initData() {
        mPresenters = new SparseArray<>();

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
            HomeNewsFragment homeNewsFragment = HomeNewsFragment.getInstance(mTypeList.get(position).getEnglishType());
            if (mPresenters.get(position) == null) {
                mPresenters.put(position, new HomeNewsPresenter(homeNewsFragment, MainActivity.this));
            }
            mPresenters.get(position).setView(homeNewsFragment);
            homeNewsFragment.setPresenter(mPresenters.get(position));
            return homeNewsFragment;
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
