package com.roller.medicine.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.roller.medicine.R;
import com.roller.medicine.info.ItemInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.ui.UpdateUserActivity;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.utils.Util;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class UserDetialAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /***个人***/
    public static  final int TYPE_A=0;
    /****银行卡*****/
    public static final int TYPE_B=1;

    private Context mContext;
    List<ItemInfo> data;
    private UserInfo user;

    public UserDetialAdapater(Context mContext, List<ItemInfo> data) {
        this.mContext = mContext;
        this.data = data;
    }

    public void setUserDetail(UserInfo user){
        this.user=user;
        this.notifyItemChanged(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_A==viewType){
            return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_detail,parent,false));
        }else{
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_h,parent,false));
        }
    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemInfo info=data.get(position);
        if (position==0){
            if (CommonUtil.isNull(user)){
                return;
            }
            UserViewHolder viewHolder=(UserViewHolder)holder;
            Log.d("url head:" + DataModel.getImageUrl(user.headImage));
            Picasso.with(mContext).load(DataModel.getImageUrl("http://rolle.cn:8080/rolle/upload/20150629/20150629164322033.jpg")).
                    transform(new CircleTransform()).placeholder(R.drawable.icon_default).into(viewHolder.iv_photo);

           // Util.loadPhoto(mContext, "http://rolle.cn:8080/rolle/upload/20150629/20150629164322033.jpg",viewHolder.iv_photo);

        }else{
            ViewHolder viewHolder=(ViewHolder)holder;
            viewHolder.title.setText(info.title);
            viewHolder.desc.setText(info.desc);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?TYPE_A:TYPE_B;
    }


    public  static class UserViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_photo;
        public UserViewHolder(View itemView) {
            super(itemView);
            iv_photo=(ImageView)itemView.findViewById(R.id.iv_photo);
        }
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
