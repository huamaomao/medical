package com.roller.medicine.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.baoyz.widget.PullRefreshLayout;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeUser;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.adapter.ChatListAdapater;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.fragment.CommentDialogFragment;
import com.roller.medicine.info.MessageChatInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.service.MedicineGotyeService;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.utils.TimeUtil;
import com.roller.medicine.viewmodel.DataModel;
import com.roller.medicine.viewmodel.GotyeService;

import java.util.LinkedList;
import java.util.List;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class MessageActivity extends BaseLoadingToolbarActivity{
    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    @InjectView(R.id.rv_view)
    RecyclerView lvView;
    @InjectView(R.id.et_message) EditText etMessage;
    private LinkedList<GotyeMessage> data;
    private ChatListAdapater adapater;
    private UserInfo userFriend;
    private GotyeService model;
    private DataModel userModel;
    private GotyeUser otherUser;
    private GotyeUser user;
    private CommentDialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, MedicineGotyeService.class));
        List<GotyeMessage> list=model.getMessageList(otherUser, false);
        if (CommonUtil.isNull(list)||list.size()==0){
            data.clear();
            adapater.notifyDataSetChanged();
        }
        lvView.scrollToPosition(adapater.getItemCount() - 1);

    }

    /****
     * 是否评论
     * @return
     */
    public boolean  canComment(){
        int length=adapater.getItemCount();
        GotyeMessage message=null;
        long lasttime=0;
        for (int i=length-1;i>=0;i--){
            message=data.get(i);
            if (otherUser.getName().equals(message.getSender().getName())){
                lasttime=message.getDate();
                break;
            }
        }
        MessageChatInfo chatInfo=userModel.getMessageChat(userFriend.id);
        chatInfo.time=lasttime*1000;
       boolean flag= TimeUtil.checkChatTime(chatInfo.time,chatInfo.lasttime);
        if (true){
            userModel.saveMessageChat(chatInfo);
        }
        return false;
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
        model.initMessageList(otherUser, new GotyeService.ChatMessageListener() {
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
                adapater.updateItem(message);
            }
        });
    }



    @Override
    protected void initView() {
        super.initView();
        userFriend=getIntent().getParcelableExtra(Constants.ITEM);
        if (CommonUtil.isNull(userFriend)){
            finish();
        }
        dialogFragment=new CommentDialogFragment();
        dialogFragment.setUserInfo(userFriend);
        dialogFragment.setClickListener(new CommentDialogFragment.OnClickListener() {
            @Override
            public void onCancel() {
                finish();
            }

            @Override
            public void onConfirm() {
                userModel.requestPraise(userFriend.id + "", new SimpleResponseListener() {
                    @Override
                    public void requestSuccess(ResponseMessage info, Response response) {
                        showLongMsg("点赞成功");
                        finish();
                    }

                    @Override
                    public void requestError(HttpException e, ResponseMessage info) {
                        showLongMsg("点赞失败");
                    }

                    @Override
                    public void requestView() {
                        hideLoading();
                    }
                });
            }

            @Override
            public void onComment() {
                //评论
                Intent intent=new Intent(MessageActivity.this,CommentActivity.class);
                intent.putExtra(Constants.ITEM, userFriend.id);
                startActivityForResult(intent, Constants.CODE);

            }
        });


        model=new GotyeService();
        userModel=new DataModel();
        data=new LinkedList<>();
        adapater=new ChatListAdapater(data, this, userFriend, new ChatListAdapater.OnSendListener() {
            @Override
            public void onSend(GotyeMessage message) {
               model.gotyeAPI.sendMessage(message);
            }
        });
        ViewUtil.initRecyclerViewDecoration(lvView,this, adapater);
        setBackActivity(userFriend.nickname);
        loadMessage();
        refresh.setRefreshStyle(Constants.PULL_STYLE);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                model.getMessageList(otherUser, true);
                refresh.setRefreshing(false);
            }
        });
        refresh.setRefreshing(false);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.toolbar_setting:
               Bundle bundle=new Bundle();
               bundle.putParcelable(Constants.ITEM, userFriend);
               ViewUtil.openActivity(MessageSettingActivity.class, bundle, this, ActivityModel.ACTIVITY_MODEL_1);
               return true;
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @OnClick(R.id.iv_send)
    void sendMessage(){
        if(CommonUtil.isEmpty(etMessage.getText().toString())) return;

        GotyeMessage message=model.sendMessage(otherUser, etMessage.getText().toString(), new GotyeService.OnValidationListener() {
            @Override
            public void errorMessage() {

            }
        });
        if (CommonUtil.notNull(message)){
            etMessage.getText().clear();
            adapater.addItem(message);
            lvView.scrollToPosition(adapater.getItemCount()-1);
        }
        lvView.scrollToPosition(adapater.getItemCount() - 1);
    }


    @OnClick(R.id.iv_photo)
    void  toPic(){
       ViewUtil.startPictureActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 选取图片的返回值
        if (requestCode == com.android.common.util.Constants.CODE_PIC) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    adapater.addItem(model.sendImageMessage(otherUser, ViewUtil.getRealFilePath(this, uri)));
                    lvView.scrollToPosition(adapater.getItemCount()-1);
                }
            }
        }else if (resultCode==Constants.CODE){
            finish();
        }

        /*else if (requestCode == INTENT_REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                if (cameraFile != null && cameraFile.exists())
                    sendPicture(cameraFile.getAbsolutePath());
            }*/

    }


    @Override
    protected void onBackActivty() {
        if (canComment()){
            dialogFragment.show(getSupportFragmentManager(), "messageComment");
        }
        super.onBackActivty();
        ViewUtil.startTopActivity(HomeActivity.class,this);
    }
}
