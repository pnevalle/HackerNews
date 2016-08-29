package com.ycombinator.hackernews.topstory;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycombinator.hackernews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pnevalle on 8/28/2016.
 */
public class TopStoryListFragment extends Fragment implements TopStoryView {
    private TopStoryPresenter mTopStoryPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private TopStoryListAdapter mTopStoryListAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topstory_list, container, false);
        ButterKnife.bind(this, view);
        mTopStoryPresenter = new TopStoryPresenter(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTopStoryPresenter.onStart();
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary));
        mTopStoryListAdapter = new TopStoryListAdapter(getActivity());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mTopStoryListAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisible, totalItemCount;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && !mSwipeRefreshLayout.isRefreshing()) {
                    totalItemCount = mLinearLayoutManager.getItemCount();
                    lastVisible = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                    if (lastVisible+1 >= totalItemCount) {
                        mTopStoryPresenter.loadMore();
                    }
                }
            }
        });
        mTopStoryPresenter.onStart();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(Object basePresenter) {
        mTopStoryPresenter = (TopStoryPresenter) basePresenter;
    }

    @Override
    public TopStoryListAdapter getAdapter() {
        return mTopStoryListAdapter;
    }

    @Override
    public void showSnackBar(String message) {
        if (getView() != null) {
            Snackbar snackbar = Snackbar
                    .make(getView(), message, Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mTopStoryPresenter.onStart();
                        }
                    });

            snackbar.show();
            showRefreshing(false);
        }
    }

    @Override
    public void showRefreshing(boolean isRefreshing) {
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
    }
}
