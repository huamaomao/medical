package com.rolle.doctor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

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
 * 医生圈
 */
public class AddressListFragment extends BaseFragment{
    @InjectView(R.id.lv_list)
    ListView lsList;
    private List<User> lsData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId(R.layout.fragment_message);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_message,menu);
    }

    @Override
    public boolean onMenuItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_add:
                ViewUtil.openActivity(AddFriendActivity.class, getActivity());
                break;
        }
        return super.onMenuItemSelected(menuItem);
    }

    @OnItemClick(R.id.lv_list)
    void onMessageItem(int position){
        ViewUtil.openActivity(MessageActivity.class,getActivity());
    }

    @Override
    protected void initView(View view, LayoutInflater inflater) {
        super.initView(view, inflater);
        lsData=new ArrayList<>();
        lsData.add(new User("主治慢性","23","0",R.drawable.icon_people_1,"叶子","1"));
        lsData.add(new User("主治慢性","26","1",R.drawable.icon_people_2,"孟龙","1"));
        lsData.add(new User("主治慢性","23","1",R.drawable.icon_people_3,"萌萌","1"));
        quickAdapter=new MessageListAdapter(getActivity(),lsData);
        lsList.setAdapter(quickAdapter);
    }
}
