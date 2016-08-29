package com.ycombinator.hackernews;

import com.ycombinator.hackernews.topstory.TopStory;
import com.ycombinator.hackernews.topstorydetail.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pnevalle on 8/28/2016.
 */
public interface HackerNewsApi {
    String BASE_URL = "https://hacker-news.firebaseio.com/v0/";
    @GET("topstories.json")
    Call<List<Integer>> getTopStories();

    @GET("item/{id}.json")
    Call<TopStory> getTopStory(@Path("id") String id);

    @GET("item/{id}.json")
    Call<Comment> getComments(@Path("id") String id);
}
