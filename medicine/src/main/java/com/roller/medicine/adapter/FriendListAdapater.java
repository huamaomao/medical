package com.roller.medicine.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.roller.medicine.R;
import com.roller.medicine.ui.DoctorDetialActivity;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class FriendListAdapater extends RecyclerAdapter<UserInfo> {
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

    public FriendListAdapater(final Context mContext, List<UserInfo> data, RecyclerView recyclerView,final int type) {
        super(mContext, data, recyclerView);
        this.type = type;
        implementRecyclerAdapterMethods(new RecyclerAdapterMethods<UserInfo>() {
            @Override
            public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder,final UserInfo user, int position) {
                ViewHolder holder=(ViewHolder)viewHolder;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CommonUtil.notNull(user)) {
                            if (Constants.USER_TYPE_DOCTOR.equals(user.typeId)||Constants.USER_TYPE_DIETITAN.equals(user.typeId)){
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(Constants.ITEM, user);
                                ViewUtil.openActivity(DoctorDetialActivity.class, bundle,(BaseToolbarActivity)mContext, ActivityModel.ACTIVITY_MODEL_1);
                            }
                        }
                    }
                });
                holder.tvName.setText(user.nickname);
                Picasso.with(mContext).load(DataModel.getImageUrl(user.headImage)).placeholder(R.drawable.icon_default).
                        transform(new CircleTransform()).into(holder.ivPhoto);
                switch (type){
                    case TYPE_DOCTOR:
                        DoctorViewHolder doctor=(DoctorViewHolder)holder;
                        StringBuilder builder1=new StringBuilder("擅长:");
                        if (CommonUtil.notNull(user.doctorDetail)){
                            builder1.append(CommonUtil.initTextNull(user.speciality));
                        }else {
                            builder1.append("无");
                        }
                        doctor.tvRemarks.setText(builder1.toString());
                        doctor.tvSex.setText(user.level+"");
                        //
                        break;
                    case TYPE_PATIENT:
                        DoctorViewHolder patient=(DoctorViewHolder)holder;
                        if ("0".equals(user.sex)){
                            holder.tvSex.setVisibility(View.VISIBLE);
                            holder.tvSex.setBackgroundResource(R.drawable.round_bg_boy);
                            holder.tvSex.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.icon_boy), null, null, null);
                        }else if ("1".equals(user.sex)){
                            holder.tvSex.setVisibility(View.VISIBLE);
                            holder.tvSex.setBackgroundResource(R.drawable.round_bg_girl);
                            holder.tvSex.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.icon_girl),null,null,null);
                        }else {
                            holder.tvSex.setVisibility(View.GONE);
                        }
                        //病类
                        if (CommonUtil.notNull(user.patientDetail)){
                            patient.tvRemarks.setText(CommonUtil.initTextNull(user.patientDetail.diseaseTypeId));
                        }

                        break;
                    case TYPE_FRIEND:
                        FriendViewHolder friend=(FriendViewHolder)holder;
                        if (Constants.USER_STATUS_ADD.equals(user.status)){
                            friend.btnStatus.setText("添加");
                        }else{
                            friend.btnStatus.setText("接受");
                        }
                        if (com.roller.medicine.utils.Constants.USER_TYPE_DOCTOR.equals(user.typeId)){
                            friend.ivType.setImageResource(R.drawable.icon_doctor);
                        }
                        friend.tvRemarks.setText("主治:血糖");
                        break;
                }
            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (TYPE_DOCTOR==viewType){
                    return  new DoctorViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_doctor, parent, false));
                }
                else if (TYPE_FRIEND==viewType){
                    return  new FriendViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_friend,parent,false));
                }
                return  new DoctorViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_doctor,parent,false));
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });
    }


    @Override
    public int getItemType(int position) {
        return type;
    }

    public  static class ViewHolder extends RecyclerAdapter.ViewHolder{
        TextView tvName,tvSex;
        ImageView ivPhoto;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName=(TextView)itemView.findViewById(R.id.tv_item_0);
            ivPhoto=(ImageView)itemView.findViewById(R.id.iv_photo);
            tvSex=(TextView)itemView.findViewById(R.id.tv_item_2);
        }
    }

    public  static class DoctorViewHolder extends ViewHolder{
        TextView tvRemarks;
        public DoctorViewHolder(View itemView) {
            super(itemView);
            tvRemarks=(TextView)itemView.findViewById(R.id.tv_item_1);
        }

    }

    public  static class FriendViewHolder extends DoctorViewHolder{
        Button btnStatus;
        ImageView ivType;
        public FriendViewHolder(View itemView) {
            super(itemView);
            btnStatus=(Button)itemView.findViewById(R.id.btn_status);
            ivType=(ImageView)itemView.findViewById(R.id.iv_type);
        }

    }

}
