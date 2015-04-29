package com.rolle.doctor.adapter;

import android.content.Context;
import android.widget.TextView;

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
        .setText(R.id.tv_item_0,item.getNickname())
        .setText(R.id.tv_item_1, item.getRemarks());
        TextView textView= helper.getView(R.id.tv_item_2);
        if ("0".equals(item.getSex())){
            textView.setBackgroundResource(R.drawable.round_bg_boy);
            textView.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.icon_boy),null,null,null);
        }else {

            textView.setBackgroundResource(R.drawable.round_bg_girl);
            textView.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.icon_girl),null,null,null);
        }
        StringBuilder builder=new StringBuilder(item.getAge());
        builder.append("岁");
        helper.setText(R.id.tv_item_2,builder.toString());
        if ("0".equals(item.getType())){
            helper.setImageResource(R.id.iv_type,R.drawable.icon_doctor);
        }
        helper.setText(R.id.tv_item_3,"中午 11:11");

    }
}
