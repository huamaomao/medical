package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rolle.doctor.R;
import com.rolle.doctor.domain.ChatMessage;

import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public abstract class BaseListAdapater<Model> extends RecyclerView.Adapter<BaseListAdapater.ViewHolder> {

    private Context mContext;
    List<Model> data;

    public BaseListAdapater(Context mContext, List<Model> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return  new ViewHolder(View.inflate(mContext,R.layout.list_item_chat_right,null),ChatMessage.RIGHT);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMeg,tvTime,tvName;
        ImageView ivPhoto;
        int type;
        public ViewHolder(View itemView,int type) {
            super(itemView);
            tvMeg=(TextView)itemView.findViewById(R.id.tv_item_1);
            tvTime=(TextView)itemView.findViewById(R.id.tv_chat_time);
            tvName=(TextView)itemView.findViewById(R.id.tv_item_0);
            ivPhoto=(ImageView)itemView.findViewById(R.id.iv_photo);
            this.type=type;
        }

    }
}
