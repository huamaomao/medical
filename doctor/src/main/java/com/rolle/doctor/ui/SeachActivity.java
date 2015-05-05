package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.common.util.ViewUtil;
import com.android.common.widget.InputMethodLinearLayout;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.presenter.LoginPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class SeachActivity extends BaseLoadingActivity {

    @InjectView(R.id.rv_view)
    RecyclerView lvView;
    private List<FriendResponse.Item> data;

    private FriendListAdapater adapater;

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
    }
}