package com.rolle.doctor.adapter;

import android.content.Context;

import com.android.common.adapter.QuickAdapter;
import com.android.common.util.ViewHolderHelp;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.User;

import java.util.List;

/**
 * 消息
 */
public class MessageListAdapter extends QuickAdapter<User> {

    public MessageListAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public MessageListAdapter(Context context, List<User> data) {
        super(context, R.layout.list_item_message, data);
    }

    public MessageListAdapter(Context context, int layoutResId, List<User> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(ViewHolderHelp helper, User item) {
        helper.setImageResource(R.id.iv_photo, item.resId)
        .setText(R.id.tv_item_0,item.getNickName())
        .setText(R.id.tv_item_1, item.getRemarks());
        if ("0".equals(item.getSex())){
            helper.setBackgroundRes(R.id.tv_item_2,R.drawable.icon_boy);
        }else {
            helper.setBackgroundRes(R.id.tv_item_2,R.drawable.icon_gril);
        }
        StringBuilder builder=new StringBuilder(item.getAge());
        builder.append("岁");
        helper.setText(R.id.tv_item_2,builder.toString());
        if ("0".equals(item.getType())){
            helper.setImageResource(R.id.iv_type,R.drawable.icon_doctor);
        }

    }
}
