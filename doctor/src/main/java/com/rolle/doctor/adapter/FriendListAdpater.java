package com.rolle.doctor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rolle.doctor.R;
import com.rolle.doctor.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/8 0008.
 */
public class FriendListAdpater extends BaseExpandableListAdapter {

    private Context mContext;
    private Map<Integer,List<User>> data;
    private String[] groups={"医生","患者"};

    public FriendListAdpater(Map<Integer, List<User>> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder=null;
        if (convertView==null){
            convertView=View.inflate(mContext, R.layout.list_group_name,null);
            groupViewHolder=new GroupViewHolder();
            groupViewHolder.mGroupName=(TextView)convertView.findViewById(R.id.tv_item_0);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder=(GroupViewHolder)convertView.getTag();
        }
        groupViewHolder.mGroupName.setText(groups[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(mContext, R.layout.list_item_message,null);
            holder=new ChildViewHolder();
            holder.name=(TextView)convertView.findViewById(R.id.tv_item_0);
            holder.detail=(TextView)convertView.findViewById(R.id.tv_item_1);
            holder.sex=(TextView)convertView.findViewById(R.id.tv_item_2);
            holder.time=(TextView)convertView.findViewById(R.id.tv_item_3);

            holder.photo=(ImageView)convertView.findViewById(R.id.iv_photo);
            holder.type=(ImageView)convertView.findViewById(R.id.iv_type);
            convertView.setTag(holder);
        }else {
            holder=(ChildViewHolder)convertView.getTag();
        }
        User item=data.get(groupPosition).get(childPosition);
        holder.name.setText(item.getNickName());
        holder.detail.setText(item.getRemarks());
        holder.photo.setImageResource(item.resId);
        if ("0".equals(item.getSex())){
            holder.sex.setBackgroundResource(R.drawable.round_bg_boy);
            holder.sex.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.icon_boy),null,null,null);
        }else {
            holder.sex.setBackgroundResource(R.drawable.round_bg_girl);
            holder.sex.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.icon_girl),null,null,null);
        }
        StringBuilder builder=new StringBuilder(item.getAge());
        builder.append("岁");
        holder.sex.setText(builder.toString());
        holder.type.setImageResource(0);
        if ("0".equals(item.getType())){
            holder.type.setImageResource(R.drawable.icon_doctor);
        }
        holder.time.setText(item.time);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static class GroupViewHolder {
        TextView mGroupName;

    }

    public static class ChildViewHolder {
        ImageView photo;
        ImageView type;
        TextView name;
        TextView detail;
        TextView time;
        TextView sex;
    }



}
