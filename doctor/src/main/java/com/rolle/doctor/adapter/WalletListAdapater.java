package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
public class WalletListAdapater extends RecyclerView.Adapter<WalletListAdapater.ViewHolder> {
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
    public WalletListAdapater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_ACMOUNT==viewType){

        }else if(TYPE_BLANK==viewType){

        }else{

        }
        return null;
    }

    @Override
    public void onBindViewHolder(WalletListAdapater.ViewHolder holder, int position) {

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
        TextView tvName,tvSex;
        ImageView ivPhoto;
        public ViewHolder(View itemView) {
            super(itemView);
            tvSex=(TextView)itemView.findViewById(R.id.tv_item_2);
            tvName=(TextView)itemView.findViewById(R.id.tv_item_0);
            ivPhoto=(ImageView)itemView.findViewById(R.id.iv_photo);
        }
    }



}
