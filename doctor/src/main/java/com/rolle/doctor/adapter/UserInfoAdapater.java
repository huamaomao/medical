package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rolle.doctor.R;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.domain.User;

import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class UserInfoAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    List<ItemInfo> data;

    public UserInfoAdapater(Context mContext, List<ItemInfo> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_text,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemInfo info=data.get(position);
        ViewHolder viewHolder=(ViewHolder)holder;
        viewHolder.title.setText(info.title);
        viewHolder.desc.setText(info.desc);
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
        TextView title,desc;
        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.tv_item_0);
            desc=(TextView)itemView.findViewById(R.id.tv_item_1);
        }
    }

}
