package com.roller.medicine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.util.CommonUtil;
import com.gotye.api.GotyeAPI;
import com.roller.medicine.R;
import com.roller.medicine.info.RecommendedInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.utils.TimeUtil;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Hua on 2015/4/3.
 */
public class DoctorCommentAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<RecommendedInfo.Item> data;

    private String name= GotyeAPI.getInstance().getLoginUser().getName();
    private UserInfo userInfo;

    public static final int ITEM_TYPE_HEAD=0;
    public static final int ITEM_TYPE_COMMENT=1;

    public DoctorCommentAdapater(final List<RecommendedInfo.Item> data, Context mContext, UserInfo friendUser) {
        this.data = data;
        this.mContext = mContext;
        this.userInfo = friendUser;
    }

    public void setUserInfo(UserInfo info){
        this.userInfo=info;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ITEM_TYPE_HEAD){
           return new UserViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_doctor_info,parent,false));
        }
       return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_comment,parent,false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position==0){
            UserViewHolder  userViewHolder=(UserViewHolder)holder;
            if (CommonUtil.isNull(userInfo.doctorDetail)){
                userInfo.doctorDetail=new UserInfo.DoctorDetail();
            }
            userViewHolder.tv_address.setText(CommonUtil.initTextNull(userInfo.doctorDetail.hospitalName));
            StringBuilder builder=new StringBuilder("(");
            if (CommonUtil.isEmpty(userInfo.doctorDetail.doctorTitle)|| CommonUtil.isEmpty(userInfo.doctorDetail.department)){
                builder.append("无");
            }else {
                builder.append(userInfo.doctorDetail.doctorTitle ).
                        append("-").
                        append(userInfo.doctorDetail.department);
            }
            builder.append(")");
            userViewHolder.tv_section.setText(builder.toString());
            userViewHolder.tv_resume.setText(userInfo.intro);
            userViewHolder.tv_position.setText(CommonUtil.initTextNull(userInfo.doctorDetail.jobAddress));
            Picasso.with(mContext).load(DataModel.getImageUrl(userInfo.headImage)).placeholder(R.drawable.icon_default).
                    transform(new CircleTransform()).into(userViewHolder.iv_photo);
            userViewHolder.tv_name.setText(userInfo.nickname);
            StringBuilder builder1=new StringBuilder("评论 (");
            builder1.append(getItemCount()-1).append(")");
            userViewHolder.tv_comment.setText(builder1.toString());
        }else {
            RecommendedInfo.Item info=data.get(position);
            ViewHolder viewHolder=(ViewHolder)holder;
            Picasso.with(mContext).load(DataModel.getImageUrl(info.headImage)).placeholder(R.drawable.icon_default).
                    transform(new CircleTransform()).into(viewHolder.iv_photo);
            viewHolder.tv_content.setText(info.content);
            viewHolder.tv_time.setText(TimeUtil.getDiffTime(info.createTime));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return ITEM_TYPE_HEAD;
        return  ITEM_TYPE_COMMENT;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.iv_photo)
        ImageView iv_photo;
        @InjectView(R.id.tv_name)
        TextView tv_name;
        @InjectView(R.id.tv_address)
        TextView tv_address;
        @InjectView(R.id.tv_section)
        TextView tv_section;
        @InjectView(R.id.tv_position)
        TextView tv_position;
        @InjectView(R.id.tv_resume)
        TextView tv_resume;
        @InjectView(R.id.tv_comment)
        TextView tv_comment;
        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }


    public  static class ViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.tv_item_0)
        TextView tv_content;
        @InjectView(R.id.tv_item_1)
        TextView tv_time;
        @InjectView(R.id.iv_photo)
        ImageView iv_photo;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }


}
