package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.MessageRecyclerAdapter;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeUser;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.ChatMessage;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class MessageListAdapater extends MessageRecyclerAdapter<GotyeMessage> {

    private Context mContext;
    List<GotyeMessage> data;

    private GotyeUser user;

    public MessageListAdapater(LinkedList<GotyeMessage> data) {
        super(data);
    }

  /*
    public MessageListAdapater(){

    }
*/
  /*
    public MessageListAdapater(Context mContext, List<ChatMessage> data) {
        this.mContext = mContext;
        this.data = data;
    }
*/


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
       // return data.get(position).getId();
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
