package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.util.Constants;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.domain.User;

import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class WalletListAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /***余额***/
    public static  final int TYPE_ACMOUNT=0;
    /****银行卡*****/
    public static final int TYPE_BLANK=1;
    /****添加*****/
    public static final int TYPE_ADD=2;

    private Context mContext;
    List<ItemInfo> data;

    public WalletListAdapater(Context mContext, List<ItemInfo> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_ACMOUNT==viewType){
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wallet_0,parent,false),TYPE_ACMOUNT);
        }else if(TYPE_BLANK==viewType){
            return new BlankViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wallet_3,parent,false),TYPE_BLANK);
        }else{
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wallet_2,parent,false),TYPE_ADD);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemInfo info=data.get(position);
        if (position==0){
            ViewHolder viewHolder=(ViewHolder)holder;
            viewHolder.ivPhoto.setImageResource(info.resId);
            viewHolder.title.setText(info.title);
        }else if(position==data.size()-1){
                return;
        }else{
            BlankViewHolder viewHolder=(BlankViewHolder)holder;
            viewHolder.title.setText(info.title);
            viewHolder.blankCode.setText(info.desc);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }



    public  static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView ivPhoto;
        int  type;

        public ViewHolder(View itemView,int type) {
            super(itemView);
            this.type=type;
            title=(TextView)itemView.findViewById(R.id.tv_item_0);
            ivPhoto=(ImageView)itemView.findViewById(R.id.iv_photo);
        }
    }

    public  static class BlankViewHolder extends RecyclerView.ViewHolder{
        TextView title,blankCode;
        int  type;
        public BlankViewHolder(View itemView,int type) {
            super(itemView);
            this.type=type;
            title=(TextView)itemView.findViewById(R.id.tv_item_0);
            blankCode=(TextView)itemView.findViewById(R.id.tv_item_1);
        }
    }



}
