package com.rolle.doctor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.common.adapter.QuickAdapter;
import com.android.common.util.ViewHolderHelp;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.ui.AddFriendActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 我的
 */
public class MyFragment extends BaseFragment{
    @InjectView(R.id.lv_list)
    ListView lsList;
    private List<ItemInfo> lsData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId(R.layout.fragment_my);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initView(View view, LayoutInflater inflater) {
        super.initView(view, inflater);
        lsData=new ArrayList<ItemInfo>();
        lsData.add(new ItemInfo(R.drawable.icon_money,"我的钱包"));
        lsData.add(new ItemInfo(R.drawable.icon_zan,"收到的推荐"));
        lsData.add(new ItemInfo(R.drawable.icon_message_l,"收到的评论"));
        lsData.add(new ItemInfo(R.drawable.icon_tousu,"收到的投诉"));
        quickAdapter=new QuickAdapter<ItemInfo>(getActivity(),R.layout.list_item_info,lsData) {
            @Override
            protected void convert(ViewHolderHelp helper, ItemInfo item) {
                helper.setImageResource(R.id.iv_photo,item.resId)
                        .setText(R.id.tv_item_0,item.title);
            }
        };
        lsList.setAdapter(quickAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_my,menu);
    }


}
