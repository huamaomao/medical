package com.rolle.doctor.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.baoyz.widget.PullRefreshLayout;
import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeUser;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.domain.UserResponse;
import com.rolle.doctor.ui.AddFriendActivity;
import com.rolle.doctor.ui.MessageActivity;
import com.rolle.doctor.ui.SeachActivity;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.util.RequestApi;
import com.rolle.doctor.util.TimeUtil;
import com.rolle.doctor.viewmodel.GotyeModel;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.InjectView;

import static com.rolle.doctor.R.mipmap;

/**
 * 消息
 */
public class MessageFragment extends BaseFragment{

    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    @InjectView(R.id.rv_view)
    RecyclerView rv_view;
    private LinkedList<User> lsData;
    private RecyclerAdapter<User> recyclerAdapter;
    private GotyeModel gotyeModel;
    private UserModel userModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId(R.layout.fragment_message);
        gotyeModel=new GotyeModel();
        userModel=new UserModel(getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_message,menu);
    }


    @Override
    protected void initView(final View view, LayoutInflater inflater) {
        super.initView(view, inflater);
        lsData=new LinkedList<>();
        refresh.setRefreshStyle(AppConstants.PULL_STYLE);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doMessage();
            }
        });
        refresh.setRefreshing(false);
        recyclerAdapter=new RecyclerAdapter(getContext(),lsData,rv_view);
        recyclerAdapter.empty="暂时没有消息哦,快去\n\"通讯录\"找人聊天吧";
        recyclerAdapter.setOnClickEvent(new RecyclerAdapter.OnClickEvent<User>() {
            @Override
            public void onClick(View v,User messageUser , int position) {
                if (CommonUtil.notNull(messageUser)){
                    Bundle bundle=new Bundle();
                    bundle.putParcelable(AppConstants.ITEM,messageUser);
                    ViewUtil.openActivity(MessageActivity.class,bundle,getActivity());
                }
            }
        });

        recyclerAdapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<User>() {
            @Override
            public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, User messageUser, int i) {
                if (CommonUtil.isNull(messageUser)) return;
                viewHolder.setText(R.id.tv_item_0, messageUser.nickname);
                viewHolder.setText(R.id.tv_item_1, messageUser.message);
                viewHolder.setText(R.id.tv_item_3, messageUser.date);
                TextView tvmsg = viewHolder.getView(R.id.tv_msg_number);
                if (messageUser.messageNum == 0) {
                    tvmsg.setVisibility(View.GONE);
                } else {
                    tvmsg.setVisibility(View.VISIBLE);
                    tvmsg.setText((messageUser.messageNum >= 100) ? "99+" : messageUser.messageNum + "");
                }
                //是否是医生
                viewHolder.setImageResource(R.id.iv_type, 0);
                if (AppConstants.USER_TYPE_DOCTOR.equals(messageUser.typeId) || AppConstants.USER_TYPE_DIETITAN.equals(messageUser.typeId)) {
                    viewHolder.setImageResource(R.id.iv_type, R.mipmap.icon_doctor);
                }
                TextView textView = viewHolder.getView(R.id.tv_item_2);
                if (CommonUtil.notEmpty(messageUser.age)) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(messageUser.age == null ? "" : messageUser.age);
                    builder.append("岁");
                    textView.setText(builder.toString());
                }
                if ("0".equals(messageUser.sex)) {
                    textView.setBackgroundResource(R.drawable.round_bg_boy);
                    textView.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.mipmap.icon_boy), null, null, null);
                } else {
                    textView.setBackgroundResource((R.drawable.round_bg_girl));
                    textView.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.mipmap.icon_girl), null, null, null);
                }
                Picasso.with(getContext()).load(RequestApi.getImageUrl(messageUser.headImage)).placeholder(R.mipmap.icon_default).
                        transform(new CircleTransform()).into((ImageView) viewHolder.getView(R.id.iv_photo));

            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_message, viewGroup, false));
            }

            @Override
            public int getItemCount() {
                return lsData.size();
            }
        });
        ViewUtil.initRecyclerViewDecoration(rv_view, getContext(), recyclerAdapter);
        doMessage();
        //接受消息
        gotyeModel.initReceive(new GotyeModel.ReceiveMessageListener() {
            @Override
            public void onReceiveMessage(GotyeMessage message) {
                if (CommonUtil.notNull(message) && message.getReceiver().getName().equals(userModel.getLoginUser().id)) {
                    User user = getUser(message.getSender().getName());
                    if (CommonUtil.notNull(user))
                        recyclerAdapter.pushItem(user);
                }

            }
        });
    }



    /****
     * 处理消息
     */
    public void doMessage(){
        int id=userModel.getLoginUser().id;
        
        List<GotyeChatTarget> ls =gotyeModel.getFriendSession();
        if (CommonUtil.notNull(ls)){
            List<User> userList=new ArrayList<>();
            User user=null;
            for (GotyeChatTarget target:ls){
                user=getUser(target.getName());
                if (CommonUtil.notNull(user)&&user.id!=id){
                    userList.add(user);
                }
            }
            recyclerAdapter.addItemAll(userList);
        }else {
            lsData.clear();
            recyclerAdapter.notifyDataSetChanged();
        }
        recyclerAdapter.checkEmpty();
        refresh.setRefreshing(false);
    }

    public  User getUser(String id){
        User user1=null;
        GotyeUser userTarget=null;
        GotyeMessage message=null;
        //     可能获取不到用户  需从服务器拉取  不在好友列表中
        user1= userModel.getUser(CommonUtil.parseInt(id));
        if (CommonUtil.notNull(user1)){
            userTarget=new GotyeUser();
            userTarget.setName(user1.id + "");
            user1.messageNum=gotyeModel.getMessageCount(userTarget);
            message=gotyeModel.getLastMessage(userTarget);
            if (CommonUtil.notNull(message)){
                user1.message=message.getText();
                user1.date= TimeUtil.getDiffTime(message.getDate() * 1000);
            }
        }else {
            userModel.requestUserInfo(id, new SimpleResponseListener<UserResponse>() {
                @Override
                public void requestSuccess(UserResponse info, Response response) {
                    if (info.user==null)return;
                    GotyeUser userTarget = new GotyeUser();
                    userTarget.setName(info.user.id + "");
                    info.user.messageNum = gotyeModel.getMessageCount(userTarget);
                    GotyeMessage gotyeMessage = gotyeModel.getLastMessage(userTarget);
                    if (CommonUtil.notNull(gotyeMessage)) {
                        info.user.message = gotyeMessage.getText();
                        info.user.date = TimeUtil.getDiffTime(gotyeMessage.getDate() * 1000);
                    }
                    userModel.saveUser(info.user);
                    recyclerAdapter.pushItem(info.user);
                }

                @Override
                public void requestError(HttpException e, ResponseMessage info) {
                    Log.d(info);

                }
            });
        }
        return user1;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_add:
                ViewUtil.openActivity(AddFriendActivity.class,getActivity());
                break;
            case R.id.toolbar_seach:
               ViewUtil.openActivity(SeachActivity.class,getActivity());
                break;
        }
        return true;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            recyclerAdapter.onDestroyReceiver();
        }catch (Exception e){}
    }

}
