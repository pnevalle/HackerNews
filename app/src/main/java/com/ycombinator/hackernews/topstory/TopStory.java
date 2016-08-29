package com.ycombinator.hackernews.topstory;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pnevalle on 8/28/2016.
 */
public class TopStory {
    private int id;
    @SerializedName("by")
    private String author;
    private String title;
    private int score;
    private long time;
    private String url;
    @SerializedName("kids")
    private List<Integer> commentIdList;

    public int getId(){
        return id;
    }

    public String getAuthor() {
        return author;
    }


    public String getTitle() {
        return title;
    }


    public int getScore() {
        return score;
    }


    public long getTime() {
        return time;
    }

    public String getUrl(){
        return url;
    }

    public List<Integer> getCommentIdList(){
        return commentIdList;
    }

}
