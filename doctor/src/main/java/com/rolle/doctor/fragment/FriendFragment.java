package com.rolle.doctor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.android.common.adapter.QuickAdapter;
import com.android.common.util.Log;
import com.android.common.util.ViewHolderHelp;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdpater;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.ui.AddFriendActivity;
import com.rolle.doctor.ui.MessageActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 患者
 */
public class FriendFragment extends BaseFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId(R.layout.fragment_friend);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_message,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @OnClick(R.id.rl_patient)
    void toPatient(){

    }
    @OnClick(R.id.rl_friend)
    void toFriend(){

    }
    @OnClick(R.id.rl_doctor)
    void toDoctor(){

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                ViewUtil.openActivity(AddFriendActivity.class,getActivity());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView(View view, LayoutInflater inflater) {
        super.initView(view, inflater);
      /*  List<User> data=new ArrayList<>();
        data.add(new User("主治慢性","23","0",R.drawable.icon_people_1,"叶子","0"));
        data.add(new User("主治慢性","26","1",R.drawable.icon_people_2,"孟龙","0"));
        data.add(new User("主治慢性","23","1",R.drawable.icon_people_3,"萌萌","0"));
        fData=new HashMap<Integer,List<User>>();
        fData.put(1,data);
        List<User> lsData=new ArrayList<>();
        lsData.add(new User("主治慢性","23","0",R.drawable.icon_people_1,"叶子","1"));
        lsData.add(new User("主治慢性","26","1",R.drawable.icon_people_2,"孟龙","1"));
        lsData.add(new User("主治慢性","23","1",R.drawable.icon_people_3,"萌萌","1"));
        fData.put(0,lsData);
        adpater=new FriendListAdpater(fData,getActivity());
        lsList.setAdapter(adpater);*/
    }
}
