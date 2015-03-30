package com.rolle.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.common.adapter.QuickAdapter;
import com.android.common.util.ViewHolderHelp;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.MessageListAdapter;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.ui.AddFriendActivity;
import com.rolle.doctor.ui.MessageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * 患者
 */
public class PatientFragment extends BaseFragment{

    @InjectView(R.id.lv_list)
    ListView lsList;
    private List<User> lsData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId(R.layout.fragment_patient);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_patient,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @OnItemClick(R.id.lv_list)
    void onMessageItem(int position){
        ViewUtil.openActivity(MessageActivity.class,getActivity());
    }

    @Override
    public boolean onMenuItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_add:
                ViewUtil.openActivity(AddFriendActivity.class,getActivity());
                break;
        }
        return super.onMenuItemSelected(menuItem);
    }


    @Override
    protected void initView(View view, LayoutInflater inflater) {
        super.initView(view, inflater);
        lsData=new ArrayList<>();
        lsData.add(new User("23","0",R.drawable.icon_people_1,"悠悠","最高血糖 16.7","最低血糖 6.6"));
        lsData.add(new User("27","1",R.drawable.icon_people_1,"叶子","最高血糖 15.7","最低血糖 8.6"));
        lsData.add(new User("28","0",R.drawable.icon_people_1,"萌子","最高血糖 18.7","最低血糖 9.6"));
        quickAdapter=new QuickAdapter<User>(getActivity(),R.layout.list_item_patient,lsData) {
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
                helper.setText(R.id.tv_item_3,item.maxNum).setText(R.id.tv_item_4,item.minNum);
            }
        };
        lsList.setAdapter(quickAdapter);
    }
}
