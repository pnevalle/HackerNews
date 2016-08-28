package com.ycombinator.hackernews.topstory;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ycombinator.hackernews.BasePresenter;
import com.ycombinator.hackernews.HackerNewsApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.MemoryHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pnevalle on 8/28/2016.
 */
public class TopStoryPresenter implements BasePresenter {
    private TopStoryView mTopStoryView;
    private Retrofit mRetroFit;
    private HackerNewsApi mHackerNewsApi;
    private static final int PAGINATION_THRESHOLD = 10;
    private int currentIndex = 0;
    private int indexLimit = 0;
    private List<Integer> mIdList;
    private Callback<List<Integer>> mGetTopStoryIdCallback = new Callback<List<Integer>>() {
        @Override
        public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
            mIdList = response.body();
            new PaginationTask().execute(mIdList);
        }

        @Override
        public void onFailure(Call<List<Integer>>call, Throwable t) {
            mTopStoryView.showRefreshing(false);
            mTopStoryView.showSnackBar(t.getMessage());
        }
    };

    public TopStoryPresenter (TopStoryView topStoryView) {
        mTopStoryView = topStoryView;
        mTopStoryView.setPresenter(this);
        Gson gson = new GsonBuilder().setLenient().create();
        mRetroFit = new Retrofit.Builder()
                .baseUrl(HackerNewsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mHackerNewsApi = mRetroFit.create(HackerNewsApi.class);
    }
    @Override
    public void onStart() {
        mTopStoryView.showRefreshing(true);
        currentIndex = 0;
        indexLimit = 0;
        mTopStoryView.getAdapter().clearList();
        if (mIdList != null)
            mIdList.clear();
        mHackerNewsApi.getTopStories().enqueue(mGetTopStoryIdCallback);
    }

    public void loadMore(){
        if (currentIndex == indexLimit) {
            mTopStoryView.showRefreshing(true);
            new PaginationTask().execute(mIdList);
        }
    }

    private class PaginationTask extends AsyncTask<List<Integer>, Void, List<TopStory>> {
        String error;
        @Override
        protected List<TopStory> doInBackground(List<Integer>... idList) {
            indexLimit += PAGINATION_THRESHOLD;
            List<TopStory> topStoryList = new ArrayList<>();
            for (; currentIndex < indexLimit; currentIndex++) {
                try {
                    Response response = mHackerNewsApi.getTopStory(String.valueOf(idList[0].get(currentIndex))).execute();
                    topStoryList.add((TopStory)response.body());
                } catch (IOException e) {
                    error = e.getMessage();
                    indexLimit = currentIndex;
                    cancel(true);
                    break;
                }
            }
            return topStoryList;
        }

        @Override
        protected void onCancelled() {
            mTopStoryView.showSnackBar(error);
        }

        @Override
        protected void onPostExecute(List<TopStory> topStories) {
            mTopStoryView.showRefreshing(false);
            mTopStoryView.getAdapter().addStoryList(topStories);
        }
    }
}
