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
public class ChatListAdapater extends RecyclerView.Adapter<ChatListAdapater.ViewHolder> {

    private Context mContext;
    List<ChatMessage> data;

    public ChatListAdapater(Context mContext, List<ChatMessage> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ChatMessage.LEFT){
            View view=View.inflate(mContext,R.layout.list_item_chat_left,null);
              RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                   ViewGroup.LayoutParams.MATCH_PARENT,
                   ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return    new ViewHolder(view,ChatMessage.LEFT);
        }
        return  new ViewHolder(View.inflate(mContext,R.layout.list_item_chat_right,null),ChatMessage.RIGHT);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage message=data.get(position);
        //holder.ivPhoto.setImageResource(R.drawable.icon_people_2);
        holder.tvMeg.setText(message.getMsg());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
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
