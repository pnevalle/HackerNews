package com.ycombinator.hackernews.topstorydetail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pnevalle on 8/29/2016.
 */
public class Comment {
    @SerializedName("text")
    private String comment;
    @SerializedName("by")
    private String commentAuthor;
    private long time;

    public String getComment() {
        return comment;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public long getTime() {
        return time;
    }
}
