package com.juncoder.aggregationnews.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.chrisbanes.photoview.PhotoView;
import com.juncoder.aggregationnews.R;
import com.juncoder.aggregationnews.utils.ImageUtils;

/**
 * Created by zhongjun on 2017/9/13/013.
 * email:1247660633@qq.com
 */
public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail);
        initStatus();
        PhotoView imageView = (PhotoView) findViewById(R.id.meizi_detail);
        String url = getIntent().getStringExtra("url");
        ImageUtils.showPic(this, url, imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }
}
