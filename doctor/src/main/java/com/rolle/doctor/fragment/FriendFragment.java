package com.rolle.doctor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.ui.AddFriendActivity;
import com.rolle.doctor.ui.FriendActivity;
import com.rolle.doctor.ui.PatientActivity;
import com.rolle.doctor.ui.TheDoctorActivity;

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
        ViewUtil.openActivity(PatientActivity.class,getActivity());
    }
    @OnClick(R.id.rl_friend)
    void toFriend(){
        ViewUtil.openActivity(FriendActivity.class,getActivity());
    }
    @OnClick(R.id.rl_doctor)
    void toDoctor(){
        ViewUtil.openActivity(TheDoctorActivity.class,getActivity());
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
    }
}
