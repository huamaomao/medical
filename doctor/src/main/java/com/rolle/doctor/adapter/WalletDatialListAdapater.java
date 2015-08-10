package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.adapter.RecyclerGroupAdapter;
import com.android.common.util.CommonUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.WalleDetail;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.domain.WalletBill;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class WalletDatialListAdapater extends RecyclerAdapter<WalletBill.Item> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context mContext;

    public WalletDatialListAdapater(Context context, final List<WalletBill.Item> items,RecyclerView recyclerView){

        super(context,items,recyclerView);
        mContext=context;
        setHasStableIds(true);
        implementRecyclerAdapterMethods(new RecyclerAdapterMethods<WalletBill.Item>() {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, WalletBill.Item item, int position) {
                viewHolder.setText(R.id.tv_item_0,item.tradingExplain);
                viewHolder.setText(R.id.tv_item_1,item.tradingDate);
                viewHolder.setText(R.id.tv_item_2, CommonUtil.formatMoney(item.accountAmountChange));
                if (CommonUtil.notEmpty(item.statusId)){
                    String str=null;
                    switch (item.statusId){
                        case "89":
                            str="交易中";
                            break;
                        case "90":
                            str="交易成功";
                            break;
                        case "91":
                            str="交易失败";
                            break;
                    }
                    viewHolder.setText(R.id.tv_item_3,str);
                }

            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                return  new ViewHolder(View.inflate(mContext,R.layout.list_item_blank_datial,null));
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        });
    }



    @Override
    public long getHeaderId(int position) {
        if (mRecyclerAdapterMethods.getItemCount()==0)return 0;
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
    public int getDataItemCount() {
        return mRecyclerAdapterMethods.getItemCount();
    }


}
