package com.android.common.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.common.R;

public class CustomLayoutProgressFragment extends ProgressFragment {
    private View mContentView;
    private Handler  mHandler = new Handler();
    private Runnable mShowContentRunnable = new Runnable() {

        @Override
        public void run() {
            setContentEmpty(true);
            setContentShown(true);
        }

    };

    public static CustomLayoutProgressFragment newInstance() {
        return new CustomLayoutProgressFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //mContentView = inflater.inflate(R.layout.view_content, null);
        return inflater.inflate(R.layout.fragment_custom_progress, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Setup content view
        //setContentView(mContentView);
        // Setup text for empty content
        setEmptyText(R.string.empty);
        obtainData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(mShowContentRunnable);
    }

    private void obtainData() {
        // Show indeterminate progress
        setContentShown(false);
        mHandler.postDelayed(mShowContentRunnable, 3000);
    }
}
