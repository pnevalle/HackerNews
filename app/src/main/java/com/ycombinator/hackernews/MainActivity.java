package com.ycombinator.hackernews;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ycombinator.hackernews.topstory.TopStoryListFragment;
import com.ycombinator.hackernews.topstorydetail.TopStoryDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public enum FRAGMENT_TYPE{
        TOP_STORY_FRAGMENT,
        TOP_STORY_DETAIL
    }
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mFragmentManager = getSupportFragmentManager();
        navigateTo(FRAGMENT_TYPE.TOP_STORY_FRAGMENT, new Bundle());
    }

    public void navigateTo(FRAGMENT_TYPE type, Bundle bundle){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (type){
            case TOP_STORY_FRAGMENT:
                TopStoryListFragment fragment = new TopStoryListFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                break;
            case TOP_STORY_DETAIL:
                TopStoryDetailFragment detailFragment = new TopStoryDetailFragment();
                detailFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.fragment_container, detailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }
}
