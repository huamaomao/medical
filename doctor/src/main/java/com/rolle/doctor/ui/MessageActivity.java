package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeUser;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.ChatListAdapater;
import com.rolle.doctor.domain.ChatMessage;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.viewmodel.GotyeModel;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.LinkedList;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class MessageActivity extends BaseActivity{

    @InjectView(R.id.rv_view) RecyclerView lvView;
    @InjectView(R.id.et_message) EditText etMessage;
    private LinkedList<GotyeMessage> data;
    private ChatListAdapater adapater;
    private FriendResponse.Item  userFriend;
    private GotyeModel model;
    private UserModel userModel;
    private GotyeUser otherUser;
    private GotyeUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    /****
     *
     */
    private void loadMessage(){
        model=new GotyeModel();
        userModel=new UserModel(getContext());
        otherUser=new GotyeUser();
        user=new GotyeUser();
        otherUser.setName(userFriend.id);
        user.setName(String.valueOf(userModel.getLoginUser().getId()));
        model.gotyeAPI.activeSession(otherUser);
        model.gotyeAPI.requestAddFriend(otherUser);
        model.getMessageList(otherUser);
    }


    @Override
    protected void initView() {
        super.initView();
        loadMessage();
        setBackActivity(userFriend.nickname);
        userFriend=getIntent().getParcelableExtra(Constants.ITEM);
        if (CommonUtil.isNull(userFriend)){
            finish();
        }
        data=new LinkedList<>();
        ViewUtil.initRecyclerView(lvView,getContext(),adapater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.toolbar_patient:
               ViewUtil.openActivity(PatientHActivity.class, this);
               return true;
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.iv_send)
    void sendMessage(){
        model.sendMessage(otherUser, etMessage.getText().toString(), new GotyeModel.OnValidationListener() {
            @Override
            public void errorMessage() {

            }
        });

      /*  if(CommonUtil.isEmpty(etMessage.getText().toString())) return;
        ChatMessage message=new ChatMessage();
        message.setMsg();
        message.setType(ChatMessage.RIGHT);
        message.setTime("中午 12:11");
        data.add(message);
        adapater.notifyDataSetChanged();
        etMessage.getText().clear();*/
        lvView.scrollToPosition(adapater.getItemCount()-1);
    }
}
