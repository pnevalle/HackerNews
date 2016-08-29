package com.ycombinator.hackernews.topstorydetail;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ycombinator.hackernews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pnevalle on 8/29/2016.
 */
public class CommentView extends FrameLayout {
    private Context mContext;
    @BindView(R.id.comment_author)TextView mCommentAuthor;
    @BindView(R.id.comment_duration)TextView mCommentDuration;
    @BindView(R.id.comment_text) TextView mCommentText;
    private String mAuthor, mDuration, mComment;
    public CommentView(Context context, String author, String duration, String comment) {
        super(context);
        mAuthor = author;
        mDuration = duration;
        mComment = comment;
        init(context);
    }
    public CommentView(Context context) {
        super(context);
        init(context);
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
        ButterKnife.bind(this, view);
        mCommentDuration.setText(mDuration);
        mCommentAuthor.setText(mAuthor);
        mCommentText.setText(Html.fromHtml(mComment));

        addView(view);
    }
}
