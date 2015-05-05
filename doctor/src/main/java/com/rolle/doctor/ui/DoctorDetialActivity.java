package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class DoctorDetialActivity extends BaseActivity{

    @InjectView(R.id.iv_photo)
    ImageView iv_photo;

    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_address)
    TextView tv_address;
    @InjectView(R.id.tv_section)
    TextView tv_section;
    @InjectView(R.id.tv_position)
    TextView tv_position;
    @InjectView(R.id.tv_resume)
    TextView tv_resume;



    private FriendResponse.Item user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detial);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("详细资料");
        user=getIntent().getParcelableExtra(Constants.ITEM);
        if (CommonUtil.isNull(user)){
            finish();
        }

        Picasso.with(getContext()).load(user.headImage).placeholder(R.drawable.icon_default).
                transform(new CircleTransform()).into(iv_photo);
        tv_name.setText(user.nickname);

    }
    @OnClick(R.id.iv_send)
    void toMessageActivity(){
        Bundle bundle=new Bundle();
        bundle.putParcelable(Constants.ITEM,user);
        ViewUtil.openActivity(MessageActivity.class,bundle,this, ActivityModel.ACTIVITY_MODEL_1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_set:
                Bundle bundle=new Bundle();
                bundle.putParcelable(Constants.ITEM,user);
                ViewUtil.openActivity(NoteActivity.class,bundle,this, ActivityModel.ACTIVITY_MODEL_1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
