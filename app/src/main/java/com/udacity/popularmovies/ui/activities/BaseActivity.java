package com.udacity.popularmovies.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.udacity.popularmovies.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pedro on 24-May-16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.activity_movies_list_toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setToolbar();
        initializeLayout(savedInstanceState);
    }

    protected void setToolbar() {
        setSupportActionBar(toolbar);
    }

    protected abstract int getLayoutId();

    protected abstract void initializeLayout(Bundle savedInstanceState);
}
