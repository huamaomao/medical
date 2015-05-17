package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.common.adapter.RecyclerGroupAdapter;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.WalleDetail;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.domain.WalletBill;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

/**
 * Created by Hua on 2015/4/3.
 */
public class WalletDatialListAdapater extends RecyclerGroupAdapter<WalletBill.Item,WalletDatialListAdapater.WalleViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public WalletDatialListAdapater(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public WalleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new WalleViewHolder(View.inflate(mContext,R.layout.list_item_blank_datial,null));
    }

    @Override
    public void onBindViewHolder(WalleViewHolder holder, int position){
        WalletBill.Item item=getItem(position);
        holder.amount.setText("¥"+item.accountAmountChange);
        holder.date.setText(item.tradingDate);
        holder.title.setText(item.typeId+"==");
        holder.status.setText("交易成功");
    }


    @Override
    public long getHeaderId(int position) {
        return getItem(position).yearMonth;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
       return  new RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.getContext())
               .inflate(R.layout.list_group_head, viewGroup, false)){};
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.tv_item_0);
        textView.setText(getItem(position).month+"月");
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    public  static class WalleViewHolder extends RecyclerView.ViewHolder {
        TextView title,date,status,amount;
        public WalleViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.tv_item_0);
            date=(TextView)itemView.findViewById(R.id.tv_item_1);
            status=(TextView)itemView.findViewById(R.id.tv_item_3);
            amount=(TextView)itemView.findViewById(R.id.tv_item_2);
        }
    }




}
