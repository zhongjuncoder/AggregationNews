package com.juncoder.aggregationnews.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.juncoder.aggregationnews.R;
import com.juncoder.aggregationnews.fragment.JokeFragment;
import com.juncoder.aggregationnews.fragment.MeiZiFragment;
import com.juncoder.aggregationnews.fragment.NewsFragment;

/**
 * Created by zhongjuncoder on 2017/9/11.
 * email:1247660633@qq.com
 */

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;

    private String[][] mTabResources = {{"新闻", String.valueOf(R.drawable.ic_news)},
            {"笑话", String.valueOf(R.drawable.ic_joke)},
            {"美图", String.valueOf(R.drawable.ic_meitu)}};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView title = (TextView) findViewById(R.id.toolbar_title);
        ViewPager viewPager = (ViewPager) findViewById(R.id.home_view_pager);
        viewPager.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager()));
        mTabLayout = (TabLayout) findViewById(R.id.bottom_tab_layout);
        mTabLayout.setupWithViewPager(viewPager);
        initTab();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                title.setText(mTabResources[tab.getPosition()][0]);
                TextView textView = (TextView) tab.getCustomView();
                assert textView != null;
                textView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                textView.getCompoundDrawables()[1].setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                title.setText(mTabResources[tab.getPosition()][0]);
                TextView textView = (TextView) tab.getCustomView();
                assert textView != null;
                textView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorGrey));
                textView.getCompoundDrawables()[1].setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorGrey), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTabLayout.setScrollPosition(0, 0f, true);
    }

    private void initTab() {
        TextView firstView = (TextView) LayoutInflater.from(this).inflate(R.layout.bottom_tab_item, null);
        firstView.setText(mTabResources[0][0]);
        firstView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
        firstView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, Integer.valueOf(mTabResources[0][1]), 0, 0);
        firstView.getCompoundDrawables()[1].setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        mTabLayout.getTabAt(0).setCustomView(firstView);

        for (int i = 1; i < 3; i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.bottom_tab_item, null);
            textView.setText(mTabResources[i][0]);
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, Integer.valueOf(mTabResources[i][1]), 0, 0);
            mTabLayout.getTabAt(i).setCustomView(textView);
        }

    }

    private class HomeFragmentAdapter extends FragmentPagerAdapter {

        HomeFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return new JokeFragment();
            } else if (position == 2) {
                return new MeiZiFragment();
            } else {
                return new NewsFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }

}
