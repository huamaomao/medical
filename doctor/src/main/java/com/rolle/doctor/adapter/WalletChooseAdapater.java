package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rolle.doctor.R;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.domain.Wallet;

import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class WalletChooseAdapater extends RecyclerView.Adapter<WalletChooseAdapater.ViewHolder> {
    /***下一步***/
    public static  final int TYPE_NEXT=0;
    /****银行卡*****/
    public static final int TYPE_BLANK=1;
    public int index=0;
    private Context mContext;
      List<Wallet.Item> data;

    public WalletChooseAdapater(Context mContext, List<Wallet.Item> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public WalletChooseAdapater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_NEXT==viewType){
            return new WalletChooseAdapater.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_next,parent,false)){};
        }else{
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wallet_choose,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(WalletChooseAdapater.ViewHolder holder, int position) {
        if (position!=getItemCount()-1){
           Wallet.Item item =data.get(position);
            holder.tv_name.setText(item.userName);
            holder.tv_account.setText(item.mobile);
            if (position==index){
                holder.ctv_choose.setChecked(true);
            }else
                holder.ctv_choose.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1)
            return TYPE_NEXT;
        return TYPE_BLANK;
    }


    public  static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_account;
        CheckedTextView ctv_choose;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name=(TextView)itemView.findViewById(R.id.tv_item_0);
            tv_account=(TextView)itemView.findViewById(R.id.tv_item_1);
            ctv_choose=(CheckedTextView)itemView.findViewById(R.id.ctv_choose);
        }
    }

}
