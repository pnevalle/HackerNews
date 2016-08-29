package com.ycombinator.hackernews.topstorydetail;

import android.os.AsyncTask;
import android.text.format.DateUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ycombinator.hackernews.BasePresenter;
import com.ycombinator.hackernews.HackerNewsApi;
import com.ycombinator.hackernews.topstory.TopStory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pnevalle on 8/29/2016.
 */
public class TopStoryDetailPresenter implements BasePresenter {
    private static final int COMMENT_THRESHOLD = 10;
    private TopStoryDetailView mTopStoryDetailView;
    private Retrofit mRetroFit;
    private HackerNewsApi mHackerNewsApi;
    private int mTopStoryId;
    private String mTopStoryTitle;

    public TopStoryDetailPresenter(TopStoryDetailView topStoryDetailView, int messageId) {
        mTopStoryDetailView = topStoryDetailView;
        mTopStoryDetailView.setPresenter(this);
        Gson gson = new GsonBuilder().setLenient().create();
        mRetroFit = new Retrofit.Builder()
                .baseUrl(HackerNewsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mHackerNewsApi = mRetroFit.create(HackerNewsApi.class);
        mTopStoryId = messageId;
    }

    @Override
    public void onStart() {
        mTopStoryDetailView.showProgessBar(true);
        retrieveTopStory(mTopStoryId);
    }

    private void retrieveTopStory(int id) {
        mHackerNewsApi.getTopStory(String.valueOf(id)).enqueue(new Callback<TopStory>() {
            @Override
            public void onResponse(Call<TopStory> call, Response<TopStory> response) {
                TopStory topStory = response.body();
                mTopStoryTitle = topStory.getTitle();
                mTopStoryDetailView.setTitle(mTopStoryTitle);
                if (topStory.getCommentIdList() != null)
                    new PaginationTask().execute(topStory.getCommentIdList());
                else {
                    mTopStoryDetailView.showProgessBar(false);
                    mTopStoryDetailView.showNoComments(true);
                }
            }

            @Override
            public void onFailure(Call<TopStory> call, Throwable t) {
                mTopStoryDetailView.showErrorMessage(t.getMessage());
            }
        });
    }

    private class PaginationTask extends AsyncTask<List<Integer>, Void, List<Comment>> {

        @Override
        protected List<Comment> doInBackground(List<Integer>... lists) {
            List<Comment> commentList = new ArrayList<>();
            for (int i = 0; i < COMMENT_THRESHOLD && i < lists[0].size(); i++) {
                try {
                    Response response = mHackerNewsApi.getComments(String.valueOf(lists[0].get(i))).execute();
                    Comment comment = (Comment)response.body();
                    if (comment != null)
                        commentList.add(comment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return commentList;
        }

        @Override
        protected void onPostExecute(List<Comment> comments) {
            for (Comment c : comments) {
                String duration = DateUtils.getRelativeTimeSpanString(c.getTime() * 1000, System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL).toString();
                mTopStoryDetailView.addCommentView(c.getComment(), mTopStoryTitle, duration, c.getCommentAuthor());
            }
            mTopStoryDetailView.showProgessBar(false);
            mTopStoryDetailView.showNoComments(false);
        }
    }
}
