package com.roller.medicine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.android.common.domain.Version;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.roller.medicine.R;
import com.roller.medicine.info.ItemInfo;
import com.roller.medicine.info.UserInfo;

import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class SettingAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static  final int TYPE_A=0;
    public static final int TYPE_B=1;
    public static final int TYPE_C=2;
    private Context mContext;
    List<ItemInfo> data;
    private UserInfo user;

    public SettingAdapater(Context mContext, List<ItemInfo> data) {
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
            return new HeadViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_setting_head,parent,false),TYPE_A);
        }else if(TYPE_C==viewType){
            return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting_loginout,parent,false)){};
        }else{
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_more,parent,false),TYPE_B);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemInfo info=data.get(position);
        if (position==0){
            HeadViewHolder viewHolder=(HeadViewHolder)holder;
            Version version= ViewUtil.getVersion(mContext);
            if (CommonUtil.notNull(version)){
                viewHolder.tv_code.setText("当前版本"+version.versionName);
            }
        }else if (position==getItemCount()-1){

        }
        else{
            ViewHolder viewHolder=(ViewHolder)holder;
            viewHolder.title.setText(info.title);

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1){
            return TYPE_C;
        }
        return position==0?TYPE_A:TYPE_B;
    }


    public  static class HeadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_code;
        ToggleButton tbtn_swich;
        RelativeLayout rl_item_0,rl_item_1;
        int  type;

        public HeadViewHolder(View itemView,int type) {
            super(itemView);
            this.type=type;
            rl_item_0=(RelativeLayout)itemView.findViewById(R.id.rl_item_0);
            rl_item_1=(RelativeLayout)itemView.findViewById(R.id.rl_item_1);
            tbtn_swich=(ToggleButton)itemView.findViewById(R.id.tbtn_swich);
            tbtn_swich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
            tv_code=(TextView)itemView.findViewById(R.id.tv_code);
            rl_item_0.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_item_0:
                    tbtn_swich.setChecked(!tbtn_swich.isChecked());
                    break;
                case R.id.rl_item_1:

                    break;
            }
        }
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        int  type;
        public ViewHolder(View itemView,int type) {
            super(itemView);
            this.type=type;
            title=(TextView)itemView.findViewById(R.id.tv_item_0);
        }
    }

  public static interface OnItemClickListener{
       public void onItemClick();
  }

}
