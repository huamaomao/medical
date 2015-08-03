package com.android.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class RecyclerGroupAdapter<T>
    extends RecyclerAdapter {
   public RecyclerGroupAdapter(Context context,List<T> items,RecyclerView recyclerView){
     super(context,items,recyclerView);
     setHasStableIds(true);
   }
}