package com.android.common.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.common.util.CommonUtil;
import com.android.common.util.Log;

import java.util.List;

public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = RecyclerOnScrollListener.class.getSimpleName();
 
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = false; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    //是否有值可以再加载数据
    private boolean isMore=true;

    private int current_page = 1;
 
    private LinearLayoutManager mLinearLayoutManager;
 
    public RecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }
 
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        if (current_page==1)return;
        if (isMore&&!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            loading=true;
            onLoadMore(current_page);
        }
    }
 
    public abstract void onLoadMore(int current_page);


    public void setPageInit(){
        current_page=1;
        loading=false;
        isMore=false;
    }

    public void nextPage(List data){
        if (CommonUtil.isNull(data)||data.size()<10)
            isMore=false;
        else{
            isMore=true;
            current_page++;
            loading=false;
        }

    }


    public void setLoadMore(){
        loading=false;
    }
}