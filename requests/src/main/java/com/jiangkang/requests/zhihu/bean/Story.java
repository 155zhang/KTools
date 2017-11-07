package com.jiangkang.requests.zhihu.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jiangkang
 * @date 2017/10/21
 */

public class Story implements Serializable{

    @SerializedName("images")
    private List<String> imageUrlList;

    @SerializedName("type")
    private int type;

    @SerializedName("id")
    private long id;

    @SerializedName("ga_prefix")
    private String gaPrefix;

    @SerializedName("title")
    private String title;


    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public Story setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
        return this;
    }

    public int getType() {
        return type;
    }

    public Story setType(int type) {
        this.type = type;
        return this;
    }

    public long getId() {
        return id;
    }

    public Story setId(long id) {
        this.id = id;
        return this;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public Story setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Story setTitle(String title) {
        this.title = title;
        return this;
    }
}
