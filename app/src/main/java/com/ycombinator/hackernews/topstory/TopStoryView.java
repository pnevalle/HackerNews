package com.ycombinator.hackernews.topstory;

import com.ycombinator.hackernews.BaseView;

/**
 * Created by pnevalle on 8/28/2016.
 */
public interface TopStoryView extends BaseView {
    TopStoryListAdapter getAdapter();
    void showSnackBar(String message);
    void showRefreshing(boolean isRefreshing);
}
