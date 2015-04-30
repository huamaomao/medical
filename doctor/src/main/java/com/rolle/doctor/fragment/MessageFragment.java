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
import android.widget.ListView;
import android.widget.TextView;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.adapter.MessageRecyclerAdapter;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.baoyz.widget.PullRefreshLayout;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.MessageListAdapter;
import com.rolle.doctor.domain.MessageUser;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.presenter.MessageListPresenter;
import com.rolle.doctor.ui.AddFriendActivity;
import com.rolle.doctor.ui.MessageActivity;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * 消息
 */
public class MessageFragment extends BaseFragment implements MessageListPresenter.IMessageView{

    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    @InjectView(R.id.rv_view)
    RecyclerView rv_view;
    private LinkedList<MessageUser> lsData;
    private MessageRecyclerAdapter<MessageUser> recyclerAdapter;
    private MessageListPresenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId(R.layout.fragment_message);
        setHasOptionsMenu(true);
        presenter=new MessageListPresenter(this);
        presenter.doMessage();

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_message,menu);
    }


    @Override
    protected void initView(final View view, LayoutInflater inflater) {
        super.initView(view, inflater);
        lsData=new LinkedList<MessageUser>();
        //lsData.add(new User("多喝水","23","0",R.drawable.icon_people_1,"叶子","0"));
        //quickAdapter=new MessageListAdapter(getActivity(),lsData);
        //lsList.setAdapter(quickAdapter);
        //layout.setColorSchemeColors(int []);
        refresh.setRefreshStyle(Constants.PULL_STYLE);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
            }
        });
        refresh.setRefreshing(false);
        recyclerAdapter=new MessageRecyclerAdapter<>(lsData);
        recyclerAdapter.implementRecyclerAdapterMethods(new MessageRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(MessageRecyclerAdapter.ViewHolder viewHolder, int i) {
                MessageUser messageUser=lsData.get(i);
                viewHolder.setText(R.id.tv_item_0,messageUser.nickname);
                viewHolder.setText(R.id.tv_item_1,messageUser.message);
                viewHolder.setText(R.id.tv_item_3,messageUser.date);
                //是否是医生
                viewHolder.setImageResource(R.id.iv_type,0);
                if ("0".equals(messageUser.type)){
                    viewHolder.setImageResource(R.id.iv_type,R.drawable.icon_doctor);
                }
                TextView textView=viewHolder.getView(R.id.tv_item_2);
                StringBuilder builder=new StringBuilder(messageUser.age);
                builder.append("岁");
                viewHolder.setText(R.id.tv_item_2,messageUser.message);
                if ("0".equals(messageUser.sex)){
                    textView.setBackgroundResource(R.drawable.round_bg_boy);
                    textView.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.icon_boy),null,null,null);
                }else {
                    textView.setBackgroundResource(R.drawable.round_bg_girl);
                    textView.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.icon_girl),null,null,null);
                }
                textView.setText(builder.toString());
                Picasso.with(getContext()).load(messageUser.icon).placeholder(R.drawable.icon_default).
                        transform(new CircleTransform()).into((ImageView) viewHolder.getView(R.id.iv_photo));

            }

            @Override
            public MessageRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new MessageRecyclerAdapter.ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_message,viewGroup,false));
            }

            @Override
            public int getItemCount() {
                return lsData.size();
            }
        });
        ViewUtil.initRecyclerView(rv_view,getContext(),recyclerAdapter);
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
    public void addMessagelist(List<MessageUser> ls) {
        recyclerAdapter.addItemAll(ls);
    }

    @Override
    public void addMessageItem(MessageUser item) {
        recyclerAdapter.addItem(item);
    }

    @Override
    public void pushMessageItem(MessageUser item) {
        recyclerAdapter.pushItem(item);
    }
}
