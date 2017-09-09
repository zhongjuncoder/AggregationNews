package com.juncoder.aggregationnews.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 */

public class PictureUtils {

    public static void showPic(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(imageView);
    }
}
