package com.rolle.doctor.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.rolle.doctor.R;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.ui.CodeImageActivity;
import com.rolle.doctor.ui.UpdateInfoActivity;
import com.rolle.doctor.ui.UpdateIntroActivity;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.RequestApi;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.rolle.doctor.R.mipmap;

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
    private User user;

    public UserDetialAdapater(Context mContext, List<ItemInfo> data) {
        this.mContext = mContext;
        this.data = data;
    }

    public void setUserDetail(User user){
        this.user=user;
        this.notifyItemChanged(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_A==viewType){
            return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_detail,parent,false),TYPE_A);
        }else{
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_h,parent,false),TYPE_B);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemInfo info=data.get(position);
        if (position==0){
            if (CommonUtil.isNull(user)){
                return;
            }
            UserViewHolder viewHolder=(UserViewHolder)holder;
            Picasso.with(mContext).load(RequestApi.getImageUrl(user.headImage)).placeholder(R.mipmap.icon_default).
                    transform(new CircleTransform()).into(viewHolder.iv_photo);

            viewHolder.iv_qd_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CommonUtil.isFastClick())return;
                    ViewUtil.openActivity(CodeImageActivity.class,(Activity)mContext);
                }
            });
            viewHolder.tv_name.setText(user.nickname);
            viewHolder.tv_jianjie.setText(CommonUtil.isEmpty(user.intro)?"无":user.intro);
            if (CommonUtil.notEmpty(user.age)){
                StringBuilder builder = new StringBuilder();
                builder.append(CommonUtil.isEmpty(user.age)?0:user.age);
                builder.append("岁");
                viewHolder.tv_sex.setText(builder.toString());
            }
            if ("0".equals(user.sex)) {
                viewHolder.tv_sex.setBackgroundResource(R.drawable.round_bg_boy);
                viewHolder.tv_sex.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.icon_boy), null, null, null);
            } else {
                viewHolder.tv_sex.setBackgroundResource(R.drawable.round_bg_girl);
                viewHolder.tv_sex.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.mipmap.icon_girl), null, null, null);
            }

            viewHolder.rl_item_0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CommonUtil.isFastClick())return;
                    ViewUtil.openActivity(UpdateInfoActivity.class, (Activity) mContext);
                }
            });
            viewHolder.rl_item_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CommonUtil.isFastClick())return;
                    ViewUtil.openActivity(UpdateIntroActivity.class,(Activity)mContext);
                }
            });
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
        TextView tv_name,tv_sex,tv_jianjie;
        ImageView iv_photo,iv_card_img,iv_qd_code;
        RelativeLayout rl_item_0,rl_item_1,rl_item_2;
        int  type;

        public UserViewHolder(View itemView,int type) {
            super(itemView);
            this.type=type;
            rl_item_0=(RelativeLayout)itemView.findViewById(R.id.rl_item_0);
            rl_item_1=(RelativeLayout)itemView.findViewById(R.id.rl_item_1);
            rl_item_2=(RelativeLayout)itemView.findViewById(R.id.rl_item_2);
            iv_photo=(ImageView)itemView.findViewById(R.id.iv_photo);
            iv_card_img=(ImageView)itemView.findViewById(R.id.iv_card_img);
            iv_qd_code=(ImageView)itemView.findViewById(R.id.iv_qd_code);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            tv_jianjie=(TextView)itemView.findViewById(R.id.tv_jianjie);
            tv_sex=(TextView)itemView.findViewById(R.id.tv_sex);
        }
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,desc;
        int  type;
        public ViewHolder(View itemView,int type) {
            super(itemView);
            this.type=type;
            title=(TextView)itemView.findViewById(R.id.tv_item_0);
            desc=(TextView)itemView.findViewById(R.id.tv_item_1);
        }
    }

}
