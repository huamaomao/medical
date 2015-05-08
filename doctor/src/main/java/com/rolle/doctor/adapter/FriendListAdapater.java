package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.util.CommonUtil;
import com.android.common.util.Constants;
import com.android.common.util.Log;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class FriendListAdapater extends RecyclerView.Adapter<FriendListAdapater.ViewHolder> {
     int type=TYPE_PATIENT;
    /***患者***/
    public static  final int TYPE_PATIENT=0;
    /****医生*****/
    public static final int TYPE_DOCTOR=1;
    /****消息*****/
    public static final int TYPE_MESSAGE=2;
    /****添加 *****/
    public static final int TYPE_FRIEND=3;

    /**** 全部 严重 轻微  ***/
    public boolean flag=false;

    private Context mContext;
    List<FriendResponse.Item> data;
    private OnItemClickListener onItemClickListener;



    public FriendListAdapater(Context mContext, List<FriendResponse.Item> data,int type) {
        this.mContext = mContext;
        this.data = data;
        this.type=type;
    }

    @Override
    public FriendListAdapater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_DOCTOR==viewType){
            return  new DoctorViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_doctor, parent, false));
        }
        else if (TYPE_FRIEND==viewType){
            return  new FriendViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_friend,parent,false));
        }
        return  new PatientViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_patient,parent,false));
    }

    @Override
        public void onBindViewHolder(FriendListAdapater.ViewHolder holder, int position) {
            final FriendResponse.Item user=data.get(position);
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onItemClickListener)
                    onItemClickListener.onItemClick(user);
            }
        });*/
        StringBuilder builder=new StringBuilder();
        builder.append(user.age==null?"?":user.age);
        builder.append("岁");
        holder.tvName.setText(user.nickname);
        holder.tvSex.setText(builder.toString());
        Picasso.with(mContext).load(user.headImage).placeholder(R.drawable.icon_default).
                transform(new CircleTransform()).into((ImageView) holder.ivPhoto);
        if ("0".equals(user.sex)){
            holder.tvSex.setBackgroundResource(R.drawable.round_bg_boy);
            holder.tvSex.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.icon_boy),null,null,null);
        }else {
            holder.tvSex.setBackgroundResource(R.drawable.round_bg_girl);
            holder.tvSex.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.icon_girl),null,null,null);
        }
        switch (type){
            case TYPE_DOCTOR:
                DoctorViewHolder doctor=(DoctorViewHolder)holder;
                doctor.tvRemarks.setText(user.doctorTitle);
                break;
            case TYPE_PATIENT:
               /* if (CommonUtil.isEmpty(user.g)){

                }*/
                PatientViewHolder patient=(PatientViewHolder)holder;
                if (CommonUtil.isEmpty(user.maxNum)){
                    patient.tvMaxNum.setVisibility(View.GONE);
                    patient.tvMinNum.setVisibility(View.GONE);
                }else {
                    StringBuilder builder1=new StringBuilder("最低血糖");
                    builder1.append(user.minNum);
                    patient.tvMinNum.setText(builder1.toString());
                    builder1.delete(0, builder1.length());
                    builder1.append("最高血糖");
                    builder1.append(user.maxNum);
                    patient.tvMaxNum.setText(builder1.toString());
                    patient.tvValue.setText("");
                }

               /* if (flag){
                    if (Util.compareBigValue(user.getMaxNum(), com.rolle.doctor.util.Constants.MAX_BLOOD)){
                        patient.tvName.setTextColor(mContext.getResources().getColor(R.color.red));
                        patient.tvValue.setTextColor(mContext.getResources().getColor(R.color.red));
                        patient.tvValue.setText("严重");
                    }else if (Util.compareSmallValue(user.getMinNum(), com.rolle.doctor.util.Constants.MIN_BLOOD)){
                        patient.tvValue.setText("轻微");
                    }

                }*/
                break;
            case TYPE_FRIEND:
                FriendViewHolder friend=(FriendViewHolder)holder;
                if (Constants.USER_STATUS_ADD.equals(user.status)){
                    friend.btnStatus.setText("添加");
                }else{
                    friend.btnStatus.setText("接受");
                }
                if (com.rolle.doctor.util.Constants.USER_TYPE_DOCTOR.equals(user.typeId)){
                    friend.ivType.setImageResource(R.drawable.icon_doctor);
                }
                friend.tvRemarks.setText("主治:血糖");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return type;
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


    public  static class PatientViewHolder extends ViewHolder{
        TextView tvMinNum,tvMaxNum,tvValue;
        public PatientViewHolder(View itemView) {
            super(itemView);
            tvMinNum=(TextView)itemView.findViewById(R.id.tv_item_3);
            tvMaxNum=(TextView)itemView.findViewById(R.id.tv_item_4);
            tvValue=(TextView)itemView.findViewById(R.id.tv_item_5);
        }
    }

    public  static class DoctorViewHolder extends ViewHolder{
        TextView tvRemarks;
        public DoctorViewHolder(View itemView) {
            super(itemView);
            tvRemarks=(TextView)itemView.findViewById(R.id.tv_item_1);
        }

    }

   /* public  static class MessageViewHolder extends ViewHolder{
        TextView tvRemarks,tvTime;
        public MessageViewHolder(View itemView) {
            super(itemView);
            tvRemarks=(TextView)itemView.findViewById(R.id.tv_item_3);
            tvTime=(TextView)itemView.findViewById(R.id.tv_item_4);
        }

    }*/

    public  static class FriendViewHolder extends DoctorViewHolder{
        Button btnStatus;
        ImageView ivType;
        public FriendViewHolder(View itemView) {
            super(itemView);
            btnStatus=(Button)itemView.findViewById(R.id.btn_status);
            ivType=(ImageView)itemView.findViewById(R.id.iv_type);
        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(User user);
    }

    public void addCleanItems(List<FriendResponse.Item> items){
        this.data.clear();
        this.data.addAll(items);
        notifyDataSetChanged();
    }
}
