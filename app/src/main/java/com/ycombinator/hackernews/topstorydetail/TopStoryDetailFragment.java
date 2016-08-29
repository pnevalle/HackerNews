package com.ycombinator.hackernews.topstorydetail;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ycombinator.hackernews.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pnevalle on 8/29/2016.
 */
public class TopStoryDetailFragment extends Fragment implements TopStoryDetailView {
    public static String ID_KEY = "id";
    @BindView(R.id.detail_title_text)TextView mTitleText;
    @BindView(R.id.comment_label)TextView mCommentLabel;
    @BindView(R.id.detail_container)LinearLayout mDetailContainer;
    @BindView(R.id.progress_bar)ProgressBar mProgressBar;
    @BindView(R.id.no_comment_text)TextView mNoCommentText;
    private TopStoryDetailPresenter mTopStoryDetailPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topstory_detail, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        int id = bundle.getInt(ID_KEY);
        mTopStoryDetailPresenter = new TopStoryDetailPresenter(this, id);
        mTopStoryDetailPresenter.onStart();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(Object basePresenter) {
        mTopStoryDetailPresenter = (TopStoryDetailPresenter) basePresenter;
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(getView(), message, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mTopStoryDetailPresenter.onStart();
                    }
                });

        snackbar.show();
    }

    @Override
    public void setTitle(String title) {
        mTitleText.setText(title);
        mCommentLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void addCommentView(String comment, String title, String duration, String author) {
        if (getActivity() != null && comment != null) {
            CommentView commentView = new CommentView(getActivity(), author, duration, comment);
            mDetailContainer.addView(commentView);
        }
    }

    @Override
    public void showProgessBar(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showNoComments(boolean show) {
        mNoCommentText.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
