package com.jiangkang.requests.zhihu.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jiangkang
 * @date 2017/10/21
 */

public class LatestNews implements Serializable{


    @SerializedName("date")
    private String date;

    @SerializedName("stories")
    private List<Story> stories;


    public String getDate() {
        return date;
    }

    public LatestNews setDate(String date) {
        this.date = date;
        return this;
    }

    public List<Story> getStories() {
        return stories;
    }

    public LatestNews setStories(List<Story> stories) {
        this.stories = stories;
        return this;
    }
}
