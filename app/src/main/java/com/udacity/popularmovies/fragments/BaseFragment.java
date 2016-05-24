package com.udacity.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by pedro on 24-May-16.
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, inflatedView);
        initializeView(savedInstanceState);
        return inflatedView;
    }

    protected abstract int getLayoutId();

    protected abstract void initializeView(Bundle savedInstanceState);


}
