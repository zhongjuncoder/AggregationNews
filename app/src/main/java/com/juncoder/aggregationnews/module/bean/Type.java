package com.juncoder.aggregationnews.module.bean;

/**
 * Created by zhongjuncoder on 2017/9/8.
 * email:1247660633@qq.com
 */

public class Type {
    
    private String EnglishType;
    
    private String ChineseType;

    public Type(String englishType, String chineseType) {
        EnglishType = englishType;
        ChineseType = chineseType;
    }

    public String getEnglishType() {
        return EnglishType;
    }

    public void setEnglishType(String englishType) {
        this.EnglishType = englishType;
    }

    public String getChineseType() {
        return ChineseType;
    }

    public void setChineseType(String chineseType) {
        this.ChineseType = chineseType;
    }
}
