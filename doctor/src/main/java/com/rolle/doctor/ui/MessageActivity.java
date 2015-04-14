package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.ChatListAdapater;
import com.rolle.doctor.domain.ChatMessage;
import java.util.LinkedList;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class MessageActivity extends BaseActivity{

     @InjectView(R.id.rv_view) RecyclerView lvView;
    @InjectView(R.id.et_message) EditText etMessage;
    private LinkedList<ChatMessage> data;
    private ChatListAdapater adapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("小叶");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lvView.setLayoutManager(layoutManager);
        data=new LinkedList<ChatMessage>();
        ChatMessage message=new ChatMessage();
        message.setName("隔壁小王");
        message.setType(ChatMessage.LEFT);
        message.setTime("中午 12:11");
        message.setMsg("约么约不约。。。。。。");
        ChatMessage message1=new ChatMessage();
        message1.setName("隔壁小王");
        message1.setTime("中午 12:11");
        message1.setType(ChatMessage.RIGHT);
        message1.setMsg("约么约不约。。。。。。");
        data.add(message);
        data.add(message1);
        adapater=new ChatListAdapater(this,data);
        lvView.setLayoutManager(layoutManager);
        lvView.setAdapter(adapater);

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
        if(CommonUtil.isEmpty(etMessage.getText().toString())) return;
        ChatMessage message=new ChatMessage();
        message.setMsg(etMessage.getText().toString());
        message.setType(ChatMessage.RIGHT);
        message.setTime("中午 12:11");
        data.add(message);
        adapater.notifyDataSetChanged();
        etMessage.getText().clear();
        lvView.scrollToPosition(adapater.getItemCount()-1);
    }
}
