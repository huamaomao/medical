package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.util.Constants;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.User;

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

    private Context mContext;
    List<User> data;
    private OnItemClickListener onItemClickListener;



    public FriendListAdapater(Context mContext, List<User> data,int type) {
        this.mContext = mContext;
        this.data = data;
        this.type=type;
    }

    @Override
    public FriendListAdapater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_DOCTOR==viewType){
            return  new DoctorViewHolder(View.inflate(mContext,R.layout.list_item_doctor,null));
        }else if(TYPE_MESSAGE==viewType){
            return  new MessageViewHolder(View.inflate(mContext,R.layout.list_item_message,null));
        }else if (TYPE_FRIEND==viewType){
            return  new FriendViewHolder(View.inflate(mContext,R.layout.list_item_friend,null));
        }
        return  new PatientViewHolder(View.inflate(mContext,R.layout.list_item_patient,null));
    }

    @Override
    public void onBindViewHolder(FriendListAdapater.ViewHolder holder, int position) {
        final User user=data.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onItemClickListener)
                    onItemClickListener.onItemClick(user);
            }
        });
        StringBuilder builder=new StringBuilder(user.getAge());
        builder.append("岁");
        holder.tvName.setText(user.getNickName());
        holder.tvSex.setText(builder.toString());
        holder.ivPhoto.setImageResource(user.resId);
        if ("0".equals(user.getSex())){
            holder.tvSex.setBackgroundResource(R.drawable.round_bg_boy);
            holder.tvSex.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.icon_boy),null,null,null);
        }else {
            holder.tvSex.setBackgroundResource(R.drawable.round_bg_girl);
            holder.tvSex.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.icon_girl),null,null,null);
        }
        switch (type){
            case TYPE_DOCTOR:
                DoctorViewHolder doctor=(DoctorViewHolder)holder;
                doctor.tvRemarks.setText(user.getRemarks());
                break;
            case TYPE_PATIENT:
                PatientViewHolder patient=(PatientViewHolder)holder;
                StringBuilder builder1=new StringBuilder("最低血糖");
                builder1.append(user.minNum);
                patient.tvMinNum.setText(builder1.toString());
                builder1.delete(0, builder1.length());
                builder1.append("最高血糖");
                builder1.append(user.maxNum);
                patient.tvMaxNum.setText(builder1.toString());
                break;
            case TYPE_MESSAGE:
                MessageViewHolder message=(MessageViewHolder)holder;
                message.tvTime.setText(user.time);
                message.tvRemarks.setText(user.getRemarks());
                break;
            case TYPE_FRIEND:
                FriendViewHolder friend=(FriendViewHolder)holder;
                if (Constants.USER_STATUS_ADD.equals(user.status)){
                    friend.btnStatus.setText("添加");
                }else{
                    friend.btnStatus.setText("接受");
                }
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
        TextView tvMinNum,tvMaxNum;
        public PatientViewHolder(View itemView) {
            super(itemView);
            tvMinNum=(TextView)itemView.findViewById(R.id.tv_item_3);
            tvMaxNum=(TextView)itemView.findViewById(R.id.tv_item_4);
        }
    }

    public  static class DoctorViewHolder extends ViewHolder{
        TextView tvRemarks;
        public DoctorViewHolder(View itemView) {
            super(itemView);
            tvRemarks=(TextView)itemView.findViewById(R.id.tv_item_1);
        }

    }

    public  static class MessageViewHolder extends ViewHolder{
        TextView tvRemarks,tvTime;
        public MessageViewHolder(View itemView) {
            super(itemView);
            tvRemarks=(TextView)itemView.findViewById(R.id.tv_item_3);
            tvTime=(TextView)itemView.findViewById(R.id.tv_item_4);
        }

    }

    public  static class FriendViewHolder extends ViewHolder{
        Button btnStatus;
        public FriendViewHolder(View itemView) {
            super(itemView);
            btnStatus=(Button)itemView.findViewById(R.id.btn_status);
        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(User user);
    }
}
