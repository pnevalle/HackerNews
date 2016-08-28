package com.ycombinator.hackernews;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ycombinator.hackernews.topstory.TopStoryListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public enum FRAGMENT_TYPE{
        TOP_STORY_FRAGMENT
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        navigateTo(FRAGMENT_TYPE.TOP_STORY_FRAGMENT);
    }

    public void navigateTo(FRAGMENT_TYPE type){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (type){
            case TOP_STORY_FRAGMENT:
                TopStoryListFragment fragment = new TopStoryListFragment();
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                break;
        }
    }
}
