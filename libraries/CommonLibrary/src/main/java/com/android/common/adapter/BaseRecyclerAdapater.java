package com.android.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public abstract class BaseRecyclerAdapater<Model> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Model> data;
    private OnItemClickListener onItemClickListener;


    public BaseRecyclerAdapater(Context mContext, List<Model> data) {
        this.mContext = mContext;
        this.data = data;
    }



    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener<Model>{
        public void onItemClick(Model model,int position);
    }
}
