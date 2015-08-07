package com.rolle.doctor.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.adapter.RecyclerAdapter;
import com.android.common.util.CommonUtil;
import com.android.common.util.DividerDecoration;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.FlexibleDividerDecoration;
import com.android.common.util.HorizontalDividerItemDecoration;
import com.android.common.util.VerticalDividerItemDecoration;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.event.BaseEvent;
import com.rolle.doctor.ui.AppointmentActivity;
import com.rolle.doctor.ui.AuthenticationActivity;
import com.rolle.doctor.ui.BaseActivity;
import com.rolle.doctor.ui.CodeImageActivity;
import com.rolle.doctor.ui.CommentListActivity;
import com.rolle.doctor.ui.InviteActivity;
import com.rolle.doctor.ui.RecommendedActivity;
import com.rolle.doctor.ui.SettingActivity;
import com.rolle.doctor.ui.UserInfoActivity;
import com.rolle.doctor.ui.WalletActivity;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.RequestApi;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 我的
 */
public class MyFragment extends BaseFragment{
    @InjectView(R.id.rv_view)
    RecyclerView rv_view;
    private List<ItemInfo> lsData;
    private UserModel userModel;
    @InjectView(R.id.iv_photo)
     ImageView iv_photo;
    @InjectView(R.id.iv_qd_code)
    ImageView iv_qd_code;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_code)
    TextView tv_code;

     private RecyclerAdapter<ItemInfo> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId(R.layout.fragment_my);
        EventBus.getDefault().register(this);
    }

    void updateInfo(){
        User user=userModel.getLoginUser();
        tv_name.setText(user.nickname);
        tv_code.setText(user.inviteCode);
        Picasso.with(getContext()).load(RequestApi.getImageUrl(user.headImage)).placeholder(R.mipmap.icon_default).
                transform(new CircleTransform()).into(iv_photo);
    }


    @OnClick(R.id.rl_user_detial)
    void toUserDetail(){
        if (CommonUtil.isFastClick())return;
        ViewUtil.openActivity(UserInfoActivity.class, getActivity());
    }

    @OnClick(R.id.iv_qd_code)
    void toCodeImage(){
        if (CommonUtil.isFastClick())return;
        ViewUtil.openActivity(CodeImageActivity.class, getActivity());
    }



    public void onEvent(BaseEvent event)
    {
        if (CommonUtil.notNull(event)&&event.type==BaseEvent.EV_USER_INFO){
            updateInfo();
        }

    }



    @Override
    protected void initView(View view, LayoutInflater inflater) {
        super.initView(view, inflater);
        userModel=new UserModel(getContext());
        lsData=new ArrayList();
        lsData.add(new ItemInfo(R.mipmap.ic_renzheng, "我要认证"));
        lsData.add(new ItemInfo(R.mipmap.icon_money, "我的钱包"));
        lsData.add(new ItemInfo(R.mipmap.icon_zan, "收到的赞"));
        lsData.add(new ItemInfo(R.mipmap.icon_message_l, "收到的评论"));
        lsData.add(new ItemInfo(R.mipmap.ic_yaoqing, "邀请好友"));
        lsData.add(new ItemInfo(R.mipmap.ic_yuyue, "预约"));
        adapter=new RecyclerAdapter(getContext(),lsData,rv_view,false);
        ViewUtil.initRecyclerViewDecoration(rv_view, getContext(), adapter);
        FlexibleDividerDecoration.SizeProvider  sizeProvider=new FlexibleDividerDecoration.SizeProvider() {
            @Override
            public int dividerSize(int position, RecyclerView parent) {
                if (position==1)
                    return (int)getResources().getDimension(R.dimen.iten_margin_top);
                return 2;
            }
        };
        rv_view.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).
                color(getResources().getColor(R.color.appbg)).sizeProvider(sizeProvider).build());
        adapter.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<ItemInfo>() {

            @Override
            public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, ItemInfo item, int position) {
                viewHolder.setImageResource(R.id.iv_photo, item.resId);
                viewHolder.setText(R.id.tv_item_0, item.title);
            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new RecyclerAdapter.ViewHolder(
                        LayoutInflater.from(getContext()).inflate(R.layout.list_item_info, viewGroup, false));
            }

            @Override
            public int getItemCount() {
                return lsData.size();
            }
        });
        adapter.setOnClickEvent(new RecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, Object item, int position) {
                if (CommonUtil.isFastClick()) return;
                switch (position) {
                    case 0:
                        ViewUtil.openActivity(AuthenticationActivity.class, getActivity());
                        break;
                    case 1:
                        ViewUtil.openActivity(WalletActivity.class, getActivity());
                        break;
                    case 2:
                        ViewUtil.openActivity(RecommendedActivity.class, getActivity());
                        break;
                    case 3:
                        ViewUtil.openActivity(CommentListActivity.class, getActivity());
                        break;
                    case 4:
                        ViewUtil.openActivity(InviteActivity.class, getActivity());
                        break;
                    case 5:
                        ViewUtil.openActivity(AppointmentActivity.class, getActivity());
                        break;

                }
            }

        });
         updateInfo();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_my,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_set:
                if (CommonUtil.isFastClick())return true;
                ViewUtil.openActivity(SettingActivity.class,getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
