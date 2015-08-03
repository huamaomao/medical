package com.android.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface StickyRecyclerHeadersAdapter<VH extends RecyclerView.ViewHolder> {
    long getHeaderId(int var1);

    VH onCreateHeaderViewHolder(ViewGroup var1);

    void onBindHeaderViewHolder(VH var1, int var2);

    int getItemCount();
}