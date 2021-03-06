package com.rolle.doctor.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.baoyz.widget.PullRefreshLayout;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeUser;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.ChatListAdapater;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.service.GotyeService;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.viewmodel.GotyeModel;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.LinkedList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class MessageActivity extends BaseActivity{
    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    @InjectView(R.id.rv_view) RecyclerView lvView;
    @InjectView(R.id.et_message) EditText etMessage;
    private LinkedList<GotyeMessage> data;
    private ChatListAdapater adapater;
    private User userFriend;
    private GotyeModel model;
    private UserModel userModel;
    private GotyeUser otherUser;
    private GotyeUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, GotyeService.class));
        lvView.scrollToPosition(adapater.getItemCount() - 1);
    }

    /****
     *初始化 消息监听
     */
    private void loadMessage(){
        otherUser=new GotyeUser();
        user=new GotyeUser();
        otherUser.setName(userFriend.id + "");
        user.setName(String.valueOf(userModel.getLoginUser().id));
        adapater.addMoreItem(model.getMessageList(otherUser, false));
        model.initMessageList(otherUser, new GotyeModel.ChatMessageListener() {
            @Override
            public void onReceiveMessage(GotyeMessage message) {
                if (message.getSender() != null
                        && user.getName().equals(message.getReceiver().getName())
                        && otherUser.getName().equals(message.getSender().getName())) {
                    adapater.addItem(message);
                    lvView.scrollToPosition(adapater.getItemCount() - 1);
                }
            }

            @Override
            public void onMessageList(List<GotyeMessage> messages) {
                adapater.addMoreItem(messages);
            }

            @Override
            public void onSendMessage(int code, GotyeMessage message) {
                Log.d(code + "==onSendMessage=" + message);
                if (code == 805) {
                    msgLongShow("已被加入黑名单...");
                } else if (code == 804) {
                    msgLongShow("用户不存在...");
                }else if (code==2){
                    startService(new Intent(getContext(), GotyeService.class));
                }
                adapater.updateItem(message);
            }
        });

    }


    @Override
    protected void initView() {
        super.initView();
        userFriend=getIntent().getParcelableExtra(AppConstants.ITEM);
        Log.d(userFriend);
        if (CommonUtil.isNull(userFriend)){
            finish();
        }

        model=new GotyeModel();
        userModel=new UserModel(getContext());
        data=new LinkedList<>();
        adapater=new ChatListAdapater(data,lvView, getContext(),userFriend, new ChatListAdapater.OnSendListener() {
            @Override
            public void onSend(GotyeMessage message) {
               model.gotyeAPI.sendMessage(message);
            }
        });
        ViewUtil.initRecyclerViewDecoration(lvView, getContext(), adapater);
        setBackActivity(userFriend.nickname);
        loadMessage();
        refresh.setRefreshStyle(AppConstants.PULL_STYLE);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                model.getMessageList(otherUser, true);
                refresh.setRefreshing(false);
            }
        });
        refresh.setRefreshing(false);

    }

   /* @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("复制");
        super.onCreateContextMenu(menu, v, menuInfo);
    }*/

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        try {
            CommonUtil.copy(getContext(),adapater.getItem(item.getItemId()).getText());
        }catch (Exception e){}
        return super.onContextItemSelected(item);
    }



    @Override
    protected void onBackActivty() {
        CommonUtil.hideInputMethod(getContext(),this.etMessage);
        super.onBackActivty();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.toolbar_patient:
               Bundle bundle=new Bundle();
               bundle.putParcelable(AppConstants.ITEM, userFriend);
               ViewUtil.openActivity(PatientHActivity.class,bundle,this, ActivityModel.ACTIVITY_MODEL_1);
               return true;
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (AppConstants.USER_TYPE_PATIENT.equals(userFriend.typeId)){
            getMenuInflater().inflate(R.menu.menu_patient,menu);
        }
        return super.onCreateOptionsMenu(menu);
    }




    @OnClick(R.id.iv_send)
    void sendMessage(){
        if(CommonUtil.isEmpty(etMessage.getText().toString())) return;
        GotyeMessage message=model.sendMessage(otherUser, etMessage.getText().toString());
        if (CommonUtil.notNull(message)){
            etMessage.getText().clear();
            adapater.addItem(message);
            lvView.scrollToPosition(adapater.getItemCount()-1);
        }
        lvView.scrollToPosition(adapater.getItemCount() - 1);
    }


    @OnClick(R.id.iv_photo)
    void  toPic(){
       ViewUtil.startPictureActivity(getContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 选取图片的返回值
        if (requestCode == com.android.common.util.Constants.CODE_PIC) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    adapater.addItem(model.sendImageMessage(otherUser,ViewUtil.getRealFilePath(getContext(),uri)));
                    lvView.scrollToPosition(adapater.getItemCount()-1);
                }
            }
        } /*else if (requestCode == INTENT_REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                if (cameraFile != null && cameraFile.exists())
                    sendPicture(cameraFile.getAbsolutePath());
            }*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapater.onDestroyReceiver();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        ViewUtil.onHideSoftInput(this, getCurrentFocus(), event);
        return super.onTouchEvent(event);
    }
}
