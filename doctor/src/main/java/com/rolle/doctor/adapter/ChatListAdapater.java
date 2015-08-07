package com.rolle.doctor.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.widget.AlertDialogFragment;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeMessageStatus;
import com.gotye.api.GotyeMessageType;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.ChatMessage;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.ui.BaseActivity;
import com.rolle.doctor.ui.ImageTouchActivity;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.TimeUtil;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class ChatListAdapater extends RecyclerAdapter<GotyeMessage> implements View.OnLongClickListener {

    private Context mContext;
    LinkedList<GotyeMessage> data;
    /***普通消息***/
    private final static int MESSAGE_LEFT_MESSAGE=0;
    private  final  static int MESSAGE_RIGHT_MESSAGE=1;
    /***图片消息***/
    private  final static int MESSAGE_RIGHT_PIC=2;
    private final static int MESSAGE_LEFT_PIC=3;
    private String name= GotyeAPI.getInstance().getLoginUser().getName();
    private User friendUser;
    protected AlertDialogFragment dialog;


    public ChatListAdapater(final LinkedList<GotyeMessage> data,RecyclerView recyclerView,final Context mContext,final User friendUser,final  OnSendListener listener) {
        super(mContext,data,recyclerView);
        this.data = data;
        this.mContext = mContext;
        this.friendUser = friendUser;
        dialog=new AlertDialogFragment();
        dialog.setClickListener(new AlertDialogFragment.OnClickListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm() {
                GotyeMessage message =getItem(dialog.getPosition());
                if (CommonUtil.notNull(message)) {
                    listener.onSend(message);
                }
            }
        });
        implementRecyclerAdapterMethods(new RecyclerAdapterMethods<GotyeMessage>() {
            @Override
            public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, final GotyeMessage message, final int position) {
                ViewHolder holder=(ViewHolder)viewHolder;
                if (position == 0) {
                    holder.tvTime.setText(TimeUtil.dateToMessageTime(message.getDate() * 1000));
                    holder.tvTime.setVisibility(View.VISIBLE);
                } else {
                    // 两条消息时间离得如果稍长，显示时间
                    if (TimeUtil.needShowTime(message.getDate()*1000,
                            data.get(position - 1).getDate()*1000)) {
                        holder.tvTime.setText(TimeUtil.toLocalTimeString(message.getDate() * 1000));
                        holder.tvTime.setVisibility(View.VISIBLE);
                    } else {
                        holder.tvTime.setVisibility(View.GONE);
                    }
                }

                Picasso.with(mContext).load(friendUser.headImage).placeholder(R.mipmap.icon_default).
                        transform(new CircleTransform()).into(holder.ivPhoto);
                if (holder.type==MESSAGE_LEFT_MESSAGE||holder.type==MESSAGE_LEFT_PIC){
                    holder.tvName.setText(CommonUtil.isEmpty(friendUser.noteName)?friendUser.nickname:friendUser.noteName);
                }
                if (holder.type== MESSAGE_LEFT_MESSAGE||holder.type==MESSAGE_RIGHT_MESSAGE){
                    holder.tvMeg.setText(message.getText());
                    /// holder.tvMeg.setOnLongClickListener(this);
                    holder.tvMeg.setTag(message.getText());
                    holder.tvMeg.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                        @Override
                        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                            menu.add(0,position,1,"复制");
                        }
                    });
                }
                switch (holder.type){
                    case MESSAGE_LEFT_MESSAGE:
                        break;
                    case MESSAGE_LEFT_PIC:

                        Picasso.with(mContext).load(new File(message.getMedia().getPath())).placeholder(R.mipmap.icon_default)
                                .resize(120,120).into(holder.iv_pic);
                        // 查看图片
                        holder.iv_pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (CommonUtil.isFastClick())return;
                                Bundle bundle=new Bundle();
                                bundle.putString(AppConstants.ITEM,message.getMedia().getPathEx());
                                ViewUtil.openActivity(ImageTouchActivity.class,bundle,(Activity)mContext);
                            }
                        });
                        break;
                    case MESSAGE_RIGHT_MESSAGE:
                        // 消息   成功 失败
                        holder.tvMeg.setText(message.getText());

                        if (GotyeMessageStatus.GotyeMessageStatusSent==message.getStatus()){
                            holder.setSuccess();
                        }else if (GotyeMessageStatus.GotyeMessageStatusSendingFailed==message.getStatus()){
                            holder.setError();
                            holder.loading.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.setPosition(position);
                                    dialog.show(((BaseActivity)mContext).getSupportFragmentManager(),"dialog");
                                }
                            });
                        }else if (GotyeMessageStatus.GotyeMessageStatusSending==message.getStatus()){
                            holder.setLoading();
                        }
                        break;
                    case MESSAGE_RIGHT_PIC:
                        if (CommonUtil.notEmpty(message.getMedia().getPath()))
                            Picasso.with(mContext).load(new File(message.getMedia().getPath())).placeholder(R.mipmap.icon_default)
                                    .into(holder.iv_pic);
                        // 查看图片
                        Log.d(message.getMedia().getPath());
                        holder.iv_pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (CommonUtil.isFastClick())return;
                                Bundle bundle=new Bundle();
                                bundle.putString(AppConstants.ITEM,message.getMedia().getPathEx());
                                ViewUtil.openActivity(ImageTouchActivity.class,bundle,(Activity)mContext);
                            }
                        });
                        if (GotyeMessageStatus.GotyeMessageStatusSent==message.getStatus()){
                            holder.setSuccess();
                        }else if (GotyeMessageStatus.GotyeMessageStatusSendingFailed==message.getStatus()){
                            holder.setError();
                            holder.loading.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.setPosition(position);
                                    dialog.show(((BaseActivity) mContext).getSupportFragmentManager(), "dialog");
                                }
                            });
                        }else if (GotyeMessageStatus.GotyeMessageStatusSending==message.getStatus()){
                            holder.setLoading();
                        }

                        break;
                }
            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType){
                    case MESSAGE_RIGHT_PIC:
                        return new ViewHolder(LayoutInflater.from(mContext).inflate
                                (R.layout.list_item_chat_right_pic,parent,false),MESSAGE_RIGHT_PIC);
                    case MESSAGE_RIGHT_MESSAGE:
                        return new ViewHolder(LayoutInflater.from(mContext).inflate
                                (R.layout.list_item_chat_right,parent,false),MESSAGE_RIGHT_MESSAGE);
                    case MESSAGE_LEFT_MESSAGE:
                        return new ViewHolder(LayoutInflater.from(mContext).inflate
                                (R.layout.list_item_chat_left,parent,false),MESSAGE_LEFT_MESSAGE);
                    case MESSAGE_LEFT_PIC:
                        return new ViewHolder(LayoutInflater.from(mContext).inflate
                                (R.layout.list_item_chat_left_pic,parent,false),MESSAGE_LEFT_PIC);
                }
                return  new ViewHolder(View.inflate(mContext,R.layout.list_item_chat_right,null),MESSAGE_RIGHT_MESSAGE);
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });

    }

    @Override
    public boolean onLongClick(View v) {
        if (v instanceof TextView){
          /*  TextView textView=(TextView)v;
            CommonUtil.copy(mContext,textView.getText().toString());*/
        }
        return false;
    }

    public interface OnSendListener{
        void onSend(GotyeMessage message);
    }


    @Override
    public int getItemType(int position) {
        GotyeMessage  message=getItem(position);
        if (isSend(position)){
            if (GotyeMessageType.GotyeMessageTypeImage==message.getType()){
                return MESSAGE_RIGHT_PIC;
            }else{
                // 不管什么先默认都是text消息
                return MESSAGE_RIGHT_MESSAGE;
            }
        }else {
            if (GotyeMessageType.GotyeMessageTypeImage==message.getType()){
                return MESSAGE_LEFT_PIC;
            }else{
                // 不管什么先默认都是text消息
                return MESSAGE_LEFT_MESSAGE;
            }
        }
    }

    /****
     *  判断是否自己发送的消息
     * @param position
     * @return
     */
    private boolean isSend(int position){
        return getItem(position).getSender().getName().equals(name);
    }


    public  static class ViewHolder extends RecyclerAdapter.ViewHolder{
        TextView tvMeg,tvTime,tvName;
        ImageView ivPhoto;
        ImageView iv_pic;
        LinearLayout loading;
        int type;
        public ViewHolder(View itemView,int type) {
            super(itemView);
            tvMeg=(TextView)itemView.findViewById(R.id.tv_item_1);
            tvTime=(TextView)itemView.findViewById(R.id.tv_chat_time);
            tvName=(TextView)itemView.findViewById(R.id.tv_item_0);
            ivPhoto=(ImageView)itemView.findViewById(R.id.iv_photo);
            iv_pic=(ImageView)itemView.findViewById(R.id.iv_pic);
            loading=(LinearLayout)itemView.findViewById(R.id.ll_loading);
            this.type=type;
        }

         void setLoading(){
             loading.findViewById(R.id.iv_send).setVisibility(View.GONE);
             loading.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
         }
        void setError(){
            loading.findViewById(R.id.iv_send).setVisibility(View.VISIBLE);
            loading.findViewById(R.id.progressBar).setVisibility(View.GONE);
        }

        void setSuccess(){
            loading.setVisibility(View.GONE);
        }

    }




    public void addItem(GotyeMessage gotyeMessage){
        data.add(gotyeMessage);
        notifyItemChanged(getItemCount()-1);
    }

    /***
     *  update  message
     * @param gotyeMessage
     */
    public void updateItem(GotyeMessage gotyeMessage){
        int index=data.indexOf(gotyeMessage);
        if (index!=-1){
            data.set(index,gotyeMessage);
            notifyItemChanged(index);
        }
    }


    public void addMoreItem(List<GotyeMessage> ls){
        if (CommonUtil.notNull(ls)){
            data.addAll(0,ls);
            notifyItemRangeInserted(0,ls.size());
        }
    }


}
