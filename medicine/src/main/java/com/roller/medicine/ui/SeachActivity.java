package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.util.ViewUtil;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 查询
 */
public class SeachActivity extends BaseToolbarActivity {

    @InjectView(R.id.rv_view)
    RecyclerView lvView;
    private List<UserInfo> data;

    private BaseRecyclerAdapter<UserInfo> adapater;
    @InjectView(R.id.seach)
    SearchView seachView;
    private DataModel userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("");
        userModel=new DataModel();
        data=new ArrayList<>();
        adapater=new BaseRecyclerAdapter<>(data);
        adapater.implementRecyclerAdapterMethods(new BaseRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(BaseRecyclerAdapter.ViewHolder viewHolder, int i) {
                UserInfo item=data.get(i);
                Picasso.with(getApplicationContext()).load(userModel.getImageUrl(item.headImage)).placeholder(R.drawable.icon_default).
                        transform(new CircleTransform()).into((ImageView) viewHolder.getView(R.id.iv_photo));
                viewHolder.setText(R.id.tv_item_1,item.nickname).setText(R.id.tv_item_0,item.noteName);
            }

            @Override
            public BaseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new BaseRecyclerAdapter.ViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item_seach_friend,viewGroup,false));
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });
        ViewUtil.initRecyclerView(lvView, this, adapater);
        lvView.setVisibility(View.GONE);
        seachView.setIconified(false);
        seachView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        seachView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if ( s != null && s.length ( ) > 0 ) {
                    lvView.setVisibility(View.VISIBLE);
                    adapater.addItemAll(userModel.seachFriendList(s));
                }
                return true;
            }
        });
    }
}