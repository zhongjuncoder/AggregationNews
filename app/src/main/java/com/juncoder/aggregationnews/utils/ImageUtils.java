package com.juncoder.aggregationnews.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.juncoder.aggregationnews.R;
import com.juncoder.aggregationnews.fragment.GlideApp;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 */

public class ImageUtils {

    public static void showPic(Fragment fragment, String url, ImageView imageView) {
        GlideApp.with(fragment)
                .load(url)
                .placeholder(R.drawable.lodingview)
                .into(imageView);
    }

    public static void showPic(AppCompatActivity activity, String url, PhotoView photoView) {
        GlideApp.with(activity)
                .load(url)
                .placeholder(R.drawable.lodingview)
                .into(photoView);
    }
}
