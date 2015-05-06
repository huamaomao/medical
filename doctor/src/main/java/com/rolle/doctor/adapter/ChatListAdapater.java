package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeMessage;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.ChatMessage;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.TimeUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class ChatListAdapater extends RecyclerView.Adapter<ChatListAdapater.ViewHolder> {

    private Context mContext;
    List<GotyeMessage> data;
    private static int MESSAGE_LEFT=0;
    private static int MESSAGE_RIGHT=1;
    private String name= GotyeAPI.getInstance().getLoginUser().getName();
    private User user;
    private FriendResponse.Item friendUser;

    public ChatListAdapater(List<GotyeMessage> data, Context mContext, User user, FriendResponse.Item friendUser) {
        this.data = data;
        this.mContext = mContext;
        this.user = user;
        this.friendUser = friendUser;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==MESSAGE_LEFT){
            View view=View.inflate(mContext,R.layout.list_item_chat_left,null);
              RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                   ViewGroup.LayoutParams.MATCH_PARENT,
                   ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return    new ViewHolder(view,MESSAGE_LEFT);
        }
        return  new ViewHolder(View.inflate(mContext,R.layout.list_item_chat_right,null),MESSAGE_RIGHT);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GotyeMessage message=data.get(position);
        if (position == 0) {
            holder.tvTime.setText(TimeUtil.dateToMessageTime(message.getDate() * 1000));
            holder.tvTime.setVisibility(View.VISIBLE);
        } else {
            // 两条消息时间离得如果稍长，显示时间
            if (TimeUtil.needShowTime(message.getDate(),
                    data.get(position - 1).getDate())) {
                holder.tvTime.setText(TimeUtil.toLocalTimeString(message.getDate() * 1000));
                holder.tvTime.setVisibility(View.VISIBLE);
            } else {
                holder.tvTime.setVisibility(View.GONE);
            }
        }
        //holder.ivPhoto.setImageResource(R.drawable.icon_people_2);
        if (holder.type==MESSAGE_LEFT){
            holder.tvName.setText(friendUser.nickname);
            Picasso.with(mContext).load(friendUser.headImage).placeholder(R.drawable.icon_default).
                    transform(new CircleTransform()).into((ImageView) holder.ivPhoto);
        }else{
           // holder.tvName.setText(user.nickname);
            Picasso.with(mContext).load(user.headImage).placeholder(R.drawable.icon_default).
                    transform(new CircleTransform()).into((ImageView) holder.ivPhoto);
        }
        holder.tvMeg.setText(message.getText());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getSender().getName().equals(name)?MESSAGE_RIGHT:MESSAGE_LEFT;
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


    public void addItem(GotyeMessage gotyeMessage){
        data.add(gotyeMessage);
        notifyItemChanged(getItemCount()-1);
    }


}
