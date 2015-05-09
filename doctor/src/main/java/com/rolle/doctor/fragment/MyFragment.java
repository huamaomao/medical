package com.rolle.doctor.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.adapter.QuickAdapter;
import com.android.common.util.Log;
import com.android.common.util.ViewHolderHelp;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.domain.RecommendedItemInfo;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.ui.RecommendedActivity;
import com.rolle.doctor.presenter.MyPresenter;
import com.rolle.doctor.ui.SettingActivity;
import com.rolle.doctor.ui.UserInfoActivity;
import com.rolle.doctor.ui.WalletActivity;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

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
     private BaseRecyclerAdapter<ItemInfo> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId(R.layout.fragment_my);
    }

    @OnClick(R.id.rl_user_detial)
    void toUserDetail(){
        ViewUtil.openActivity(UserInfoActivity.class, getActivity());
    }

    @Override
    protected void initView(View view, LayoutInflater inflater) {
        super.initView(view, inflater);
        lsData=new ArrayList<ItemInfo>();
        lsData.add(new ItemInfo(R.drawable.icon_money, "我的钱包"));
        lsData.add(new ItemInfo(R.drawable.icon_zan, "收到的推荐"));
        lsData.add(new ItemInfo(R.drawable.icon_message_l, "收到的评论"));
        lsData.add(new ItemInfo(R.drawable.icon_tousu,"收到的投诉"));

        adapter=new BaseRecyclerAdapter<>(lsData);
        adapter.implementRecyclerAdapterMethods(new BaseRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(BaseRecyclerAdapter.ViewHolder viewHolder, int i) {
                ItemInfo info = lsData.get(i);
                viewHolder.setImageResource(R.id.iv_photo, info.resId);
                viewHolder.setText(R.id.tv_item_0, info.title);
            }

            @Override
            public BaseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new BaseRecyclerAdapter.ViewHolder(
                        LayoutInflater.from(getContext()).inflate(R.layout.list_item_info, viewGroup, false));
            }

            @Override
            public int getItemCount() {
                return lsData.size();
            }
        });
        adapter.setOnClickEvent(new BaseRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, int position) {
                switch (position) {
                    case 0:
                        ViewUtil.openActivity(WalletActivity.class, getActivity());
                        break;
                    case 1:
                        ViewUtil.openActivity(RecommendedActivity.class, getActivity());
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        });
         ViewUtil.initRecyclerView(rv_view,getContext(),adapter);
        userModel=new UserModel(getContext());
        User user=userModel.getLoginUser();
        tv_name.setText(user.nickname);
        Picasso.with(getContext()).load(user.headImage).placeholder(R.drawable.icon_default).
                transform(new CircleTransform()).into(iv_photo);


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
                ViewUtil.openActivity(SettingActivity.class,getActivity());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
