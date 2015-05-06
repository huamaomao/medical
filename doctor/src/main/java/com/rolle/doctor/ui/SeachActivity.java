package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.domain.FriendResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class SeachActivity extends BaseLoadingActivity {

    @InjectView(R.id.rv_view)
    RecyclerView lvView;
    private List<FriendResponse.Item> data;

    private FriendListAdapater adapater;
    @InjectView(R.id.seach)
    SearchView seachView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("");
        data=new ArrayList<FriendResponse.Item>();
      /*  data.add(new User("主治慢性","23","0",R.drawable.icon_people_1,"叶子","0"));
        data.add(new User("主治慢性","26","1",R.drawable.icon_people_2,"孟龙","0"));
        data.add(new User("主治慢性", "23", "1", R.drawable.icon_people_3, "萌萌", "0"));*/
        adapater=new FriendListAdapater(this,data,FriendListAdapater.TYPE_PATIENT);
        ViewUtil.initRecyclerView(lvView,getContext(),adapater);
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

                }
                return true;
            }
        });
    }
}