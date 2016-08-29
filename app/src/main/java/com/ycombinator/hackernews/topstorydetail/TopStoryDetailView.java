package com.ycombinator.hackernews.topstorydetail;

import com.ycombinator.hackernews.BaseView;

/**
 * Created by pnevalle on 8/29/2016.
 */
public interface TopStoryDetailView extends BaseView {
    void showErrorMessage(String message);
    void setTitle(String title);
    void addCommentView(String comment, String title, String duration, String author);
    void showProgessBar(boolean show);
    void showNoComments(boolean show);
}
