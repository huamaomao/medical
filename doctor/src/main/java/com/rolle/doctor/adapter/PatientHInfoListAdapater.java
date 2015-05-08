package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.util.CommonUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class PatientHInfoListAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<ItemInfo> data;

    private FriendResponse.Item  user;

    final  static int TYPE_0=0;
    final  static int TYPE_1=1;
    final  static int TYPE_2=2;

    public void setUser(FriendResponse.Item user) {
        this.user = user;
    }

    public PatientHInfoListAdapater(Context mContext, List<ItemInfo> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       switch (viewType){
           case TYPE_0:
                return new UserViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_patient_info_head,parent,false));
           case TYPE_1:
               return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_h,parent,false)){};
           default:
               return new RecyclerView.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_patient_info_null,parent,false)){};
       }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder ,int position) {
        ItemInfo info=data.get(position);
        if (position==0){
            UserViewHolder  userViewHolder=(UserViewHolder)viewHolder;
            StringBuilder builder=new StringBuilder("简介：");
            if (user!=null){
                Picasso.with(mContext).load(user.headImage).placeholder(R.drawable.icon_default).
                        transform(new CircleTransform()).into(userViewHolder.photo);
                userViewHolder.title.setText(user.nickname);
                builder.append(CommonUtil.isEmpty(user.describe) ? " 暂无介绍" : user.describe);
                userViewHolder.desc.setText(builder.toString());
            }
        }else if (position!=5){
            ViewHolder holder=(ViewHolder)viewHolder;
            holder.title.setText(info.title);
            holder.desc.setText(CommonUtil.isEmpty(info.desc)?"无":info.desc);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
            if (position==0)
                return TYPE_0;
             else  if (position==5)
                return TYPE_2;
            else
                return TYPE_1;


    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,desc;
        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.tv_item_0);
            desc=(TextView)itemView.findViewById(R.id.tv_item_1);
        }
    }

    public  static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView title,desc;
        ImageView photo;
        public UserViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.tv_item_0);
            desc=(TextView)itemView.findViewById(R.id.tv_item_1);
            photo=(ImageView)itemView.findViewById(R.id.iv_photo);

        }
    }

}
