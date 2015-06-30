package com.roller.medicine.fragment;

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
import com.roller.medicine.R;
import com.roller.medicine.ui.AddFriendActivity;
import com.roller.medicine.ui.DietitianActivity;
import com.roller.medicine.ui.DoctorActivity;
import com.roller.medicine.ui.MessageActivity;
import com.roller.medicine.ui.PatientActivity;
import com.roller.medicine.ui.SeachActivity;
import com.roller.medicine.base.BaseToolbarFragment;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.info.UserResponseInfo;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.utils.TimeUtil;
import com.roller.medicine.viewmodel.DataModel;
import com.roller.medicine.viewmodel.GotyeService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author Hua_
 * @Description:
 * @date 2015/5/22 - 10:19
 */
public class TabMessageFragment extends BaseToolbarFragment {
    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    @InjectView(R.id.rv_view)
    RecyclerView rv_view;
    private LinkedList<UserInfo> lsData;
    private RecyclerAdapter<UserInfo> recyclerAdapter;
    private GotyeService gotyeService;
    private DataModel medicineDataService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId(R.layout.fragment_message);
        gotyeService=new GotyeService();
        medicineDataService=new DataModel();
    }

    @Override
    protected void initView(View view, LayoutInflater inflater) {
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
        recyclerAdapter=new RecyclerAdapter(getActivity(),lsData,rv_view);
        recyclerAdapter.setOnClickEvent(new RecyclerAdapter.OnClickEvent<UserInfo>() {
            @Override
            public void onClick(View v, UserInfo userInfo, int position) {
                Bundle bundle=new Bundle();
                bundle.putParcelable(AppConstants.ITEM,userInfo);
                ViewUtil.openActivity(MessageActivity.class, bundle,getActivity(), ActivityModel.ACTIVITY_MODEL_1);

            }
        });

        recyclerAdapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<UserInfo>() {
            @Override
            public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder,UserInfo userInfo,int i) {
                UserInfo messageUser = lsData.get(i);
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
                    viewHolder.setImageResource(R.id.iv_type, R.drawable.icon_doctor);
                }
                TextView textView = viewHolder.getView(R.id.tv_item_2);
                StringBuilder builder = new StringBuilder();
                if (CommonUtil.notEmpty(messageUser.age)) {
                    builder.append(messageUser.age);
                    builder.append("岁");
                }
                viewHolder.setText(R.id.tv_item_2, messageUser.message);
                if ("0".equals(messageUser.sex)) {
                    textView.setBackgroundResource(R.drawable.round_bg_boy);
                    textView.setCompoundDrawablesWithIntrinsicBounds(getActivity().getResources().getDrawable(R.drawable.icon_boy), null, null, null);
                } else {
                    textView.setBackgroundResource(R.drawable.round_bg_girl);
                    textView.setCompoundDrawablesWithIntrinsicBounds(getActivity().getResources().getDrawable(R.drawable.icon_girl), null, null, null);
                }
                textView.setText(builder.toString());
                Picasso.with(getActivity()).load(DataModel.getImageUrl(messageUser.headImage)).placeholder(R.drawable.icon_default).
                        transform(new CircleTransform()).into((ImageView) viewHolder.getView(R.id.iv_photo));

            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.list_item_message, viewGroup, false));
            }

            @Override
            public int getItemCount() {
                return lsData.size();
            }
        });
        ViewUtil.initRecyclerViewDecoration(rv_view, getActivity(), recyclerAdapter);
        refresh.setRefreshing(true);
        doMessage();
        //接受消息
        gotyeService.initReceive(new GotyeService.ReceiveMessageListener() {
            @Override
            public void onReceiveMessage(GotyeMessage message) {
                if (CommonUtil.notNull(message)&&message.getReceiver().getName().equals(medicineDataService.getLoginUser().id)){
                    UserInfo user=getUser(message.getSender().getName());
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
         int id=medicineDataService.getLoginUser().id;
        List<GotyeChatTarget> ls =gotyeService.getFriendSession();
        if (CommonUtil.notNull(ls)){
            List<UserInfo> userList=new ArrayList<>();
            UserInfo user=null;
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

       //addall 消息集合

    }

    public  UserInfo getUser(String id){
        UserInfo user1=null;
        GotyeUser userTarget=null;
        GotyeMessage message=null;
        //     可能获取不到用户  需从服务器拉取  不在好友列表中
        user1= medicineDataService.getUser(CommonUtil.parseInt(id));
        if (CommonUtil.notNull(user1)){
            userTarget=new GotyeUser();
            userTarget.setName(user1.id + "");
            user1.messageNum=gotyeService.getMessageCount(userTarget);
            message=gotyeService.getLastMessage(userTarget);
            if (CommonUtil.notNull(message)){
                user1.message=message.getText();
                user1.date= TimeUtil.getDiffTime(message.getDate() * 1000);
            }
        }else {
            medicineDataService.requestUserInfo(id, new SimpleResponseListener<UserResponseInfo>() {
                @Override
                public void requestSuccess(UserResponseInfo info, Response response) {
                    if (info.user==null)return;
                    GotyeUser userTarget = new GotyeUser();
                    userTarget.setName(info.user.id + "");
                    info.user.messageNum = gotyeService.getMessageCount(userTarget);
                    GotyeMessage gotyeMessage = gotyeService.getLastMessage(userTarget);
                    if (CommonUtil.notNull(gotyeMessage)) {
                        info.user.message = gotyeMessage.getText();
                        info.user.date = TimeUtil.getDiffTime(gotyeMessage.getDate() * 1000);
                    }
                    medicineDataService.saveUser(info.user);
                    recyclerAdapter.pushItem(info.user);
                }

                @Override
                public void requestError(HttpException e, ResponseMessage info) {
                    Log.d(info);
                   // new AppHttpExceptionHandler().via(getActivity()).handleException(e, info);
                }
            });
        }
        return user1;

    }

    /*****
     * 找医生
     */
    @OnClick(R.id.ll_item_0)
    void onSeachDoctor(){
        ViewUtil.openActivity(DoctorActivity.class,getActivity());
        setLastClickTime();
}
    /*****
     * 找营养师
     */
    @OnClick(R.id.ll_item_1)
    void onSeachDietitian(){
        ViewUtil.openActivity(DietitianActivity.class,getActivity());
        setLastClickTime();
    }
    /*****
     * 找患者
     */
    @OnClick(R.id.ll_item_2)
    void onSeachPatient(){
        ViewUtil.openActivity(PatientActivity.class,getActivity());
        setLastClickTime();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_message, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_add:
                ViewUtil.openActivity(AddFriendActivity.class, getActivity(), ActivityModel.ACTIVITY_MODEL_2);
                break;
            case R.id.toolbar_seach:
                ViewUtil.openActivity(SeachActivity.class,getActivity());
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public static TabMessageFragment newInstance() {
        return new TabMessageFragment();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerAdapter.onDestroyReceiver();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
