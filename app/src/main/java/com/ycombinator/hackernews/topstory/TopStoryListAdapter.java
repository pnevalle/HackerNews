package com.ycombinator.hackernews.topstory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ycombinator.hackernews.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pnevalle on 8/28/2016.
 */
public class TopStoryListAdapter extends RecyclerView.Adapter<TopStoryListAdapter.CardViewHolder> {
    private List<TopStory> mList = new ArrayList<>();
    private Context mContext;
    public TopStoryListAdapter(Context context){
        mContext = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_top_story, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        TopStory topStory = mList.get(position);
        holder.mNewsTitle.setText(topStory.getTitle());
        holder.mNewsScore.setText(mContext.getResources().getString(R.string.points_text,topStory.getScore()));
        holder.mNewsAuthor.setText(mContext.getResources().getString(R.string.author_text, topStory.getAuthor()));
        String duration = DateUtils.getRelativeTimeSpanString(topStory.getTime()*1000, System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL).toString();
        holder.mDuration.setText(duration);
        final String url = topStory.getUrl();
        holder.mOpenLink.setVisibility(url != null ? View.VISIBLE : View.GONE);
        holder.mOpenLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addStoryList(List<TopStory> topStoryList){
        int prevSize = mList.size();
        mList.addAll(topStoryList);
        notifyItemRangeChanged(prevSize, topStoryList.size());
    }

    public void clearList(){
        mList.clear();
        notifyDataSetChanged();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_title) TextView mNewsTitle;
        @BindView(R.id.news_score) TextView mNewsScore;
        @BindView(R.id.news_author) TextView mNewsAuthor;
        @BindView(R.id.news_duration) TextView mDuration;
        @BindView(R.id.open_link_text) TextView mOpenLink;

        public CardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
