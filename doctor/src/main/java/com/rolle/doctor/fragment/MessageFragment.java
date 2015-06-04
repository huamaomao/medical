package com.rolle.doctor.fragment;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
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
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.baoyz.widget.PullRefreshLayout;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.presenter.MessageListPresenter;
import com.rolle.doctor.ui.AddFriendActivity;
import com.rolle.doctor.ui.MessageActivity;
import com.rolle.doctor.ui.SeachActivity;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.util.RequestApi;
import com.rolle.doctor.viewmodel.GotyeModel;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import butterknife.InjectView;

/**
 * 消息
 */
public class MessageFragment extends BaseFragment implements MessageListPresenter.IMessageView{

    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    @InjectView(R.id.rv_view)
    RecyclerView rv_view;

    private LinkedList<User> lsData;
    private RecyclerAdapter<User> recyclerAdapter;
    private MessageListPresenter presenter;

    private GotyeModel model;
    private UserModel userModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId(R.layout.fragment_message);
        presenter=new MessageListPresenter(this);
        model=new GotyeModel();
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
        refresh.setRefreshStyle(Constants.PULL_STYLE);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.doMessage();
                refresh.setRefreshing(false);
            }
        });
        refresh.setRefreshing(false);
        recyclerAdapter=new RecyclerAdapter<>(getContext(),lsData,rv_view);
        recyclerAdapter.empty="暂时没有消息哦,快去\n\"通讯录\"找人聊天吧";
        recyclerAdapter.setOnClickEvent(new RecyclerAdapter.OnClickEvent<User>() {
            @Override
            public void onClick(View v,User messageUser , int position) {
                if (CommonUtil.notNull(messageUser)){
                    Bundle bundle=new Bundle();
                    bundle.putParcelable(Constants.ITEM,messageUser);
                    ViewUtil.openActivity(MessageActivity.class,bundle,getActivity(), ActivityModel.ACTIVITY_MODEL_1);
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
                if (Constants.USER_TYPE_DOCTOR.equals(messageUser.typeId) || Constants.USER_TYPE_DIETITAN.equals(messageUser.typeId)) {
                    viewHolder.setImageResource(R.id.iv_type, R.drawable.icon_doctor);
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
                    textView.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.icon_boy), null, null, null);
                } else {
                    textView.setBackgroundResource(R.drawable.round_bg_girl);
                    textView.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.icon_girl), null, null, null);
                }
                Picasso.with(getContext()).load(RequestApi.getImageUrl(messageUser.headImage)).placeholder(R.drawable.icon_default).
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
        presenter.doMessage();
        presenter.initReceive();
        //网络广播
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(recyclerAdapter.broadcastReceiver,filter);


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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addMessagelist(List<User> ls) {
        recyclerAdapter.addItemAll(ls);
    }

    @Override
    public void addMessageItem(User item) {
        recyclerAdapter.addItem(item);
    }

    @Override
    public void setEmpty() {
        recyclerAdapter.checkEmpty();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(recyclerAdapter.broadcastReceiver);
        }catch (Exception e){}
    }

}
