package com.rolle.doctor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 * 邀请
 *
 */
public class InviteActivity extends Activity {

    private BaseRecyclerAdapter<String> adapter;
    private List<String> lsData;
    @InjectView(R.id.rv_view)RecyclerView rv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.inject(this);
        lsData=new ArrayList<>();
        lsData.add("");

        adapter=new BaseRecyclerAdapter<>(lsData);
        ViewUtil.initRecyclerView(rv_view, this, adapter);
    }


}
