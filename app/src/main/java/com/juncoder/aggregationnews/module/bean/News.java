package com.juncoder.aggregationnews.module.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.juncoder.aggregationnews.R;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 * <p>
 * uniquekey : 6c4caa0c3ba6e05e2a272892af43c00e
 * title : 杨幂的发际线再也回不去了么？网友吐槽像半秃
 * date : 2017-01-05 11:03
 * category : yule
 * author_name : 腾讯娱乐
 * url : http://mini.eastday.com/mobile/170105110355287.html?qid=juheshuju
 * thumbnail_pic_s :
 * thumbnail_pic_s02 :
 * thumbnail_pic_s03 :
 */
public class News implements MultiItemEntity, Parcelable {

    private String uniquekey;
    private String title;
    private String date;
    private String category;
    private String author_name;
    private String url;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s02;
    private String thumbnail_pic_s03;

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getThumbnail_pic_s02() {
        return thumbnail_pic_s02;
    }

    public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
    }

    public String getThumbnail_pic_s03() {
        return thumbnail_pic_s03;
    }

    public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }

    @Override
    public String toString() {
        return "News{" +
                "uniquekey='" + uniquekey + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                ", author_name='" + author_name + '\'' +
                ", url='" + url + '\'' +
                ", thumbnail_pic_s='" + thumbnail_pic_s + '\'' +
                ", thumbnail_pic_s02='" + thumbnail_pic_s02 + '\'' +
                ", thumbnail_pic_s03='" + thumbnail_pic_s03 + '\'' +
                '}';
    }

    @Override
    public int getItemType() {
        if (thumbnail_pic_s02 == null && thumbnail_pic_s03 == null) {
            return R.layout.news_item1;
        } else if (thumbnail_pic_s02 != null && thumbnail_pic_s03 == null) {
            return R.layout.news_item2;
        } else {
            return R.layout.news_item3;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uniquekey);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.category);
        dest.writeString(this.author_name);
        dest.writeString(this.url);
        dest.writeString(this.thumbnail_pic_s);
        dest.writeString(this.thumbnail_pic_s02);
        dest.writeString(this.thumbnail_pic_s03);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.uniquekey = in.readString();
        this.title = in.readString();
        this.date = in.readString();
        this.category = in.readString();
        this.author_name = in.readString();
        this.url = in.readString();
        this.thumbnail_pic_s = in.readString();
        this.thumbnail_pic_s02 = in.readString();
        this.thumbnail_pic_s03 = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
