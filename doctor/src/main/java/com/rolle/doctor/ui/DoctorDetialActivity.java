package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.AppConstants;
import com.squareup.picasso.Picasso;

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

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detial);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("详细资料");
        user=getIntent().getParcelableExtra(AppConstants.ITEM);
        if (CommonUtil.isNull(user)){
            finish();
        }
        StringBuilder builder=new StringBuilder("(");
        if (CommonUtil.notNull(user.doctorDetail)){
            tv_address.setText(CommonUtil.initTextNull(user.doctorDetail.hospitalName));
            if (CommonUtil.isEmpty(user.doctorDetail.doctorTitle)||CommonUtil.isEmpty(user.doctorDetail.department)){
                builder.append("无");
            }else {
                builder.append(user.doctorDetail.doctorTitle ).
                        append("-").
                        append(user.doctorDetail.department);
            }
        }else{
            tv_address.setText("无");
            builder.append("无");
        }
        builder.append(")");
        tv_section.setText(builder.toString());
        tv_resume.setText(user.intro);
        Picasso.with(getContext()).load(user.headImage).placeholder(R.mipmap.icon_default).
                transform(new CircleTransform()).into(iv_photo);
        tv_name.setText(user.nickname);

    }
    @OnClick(R.id.iv_send)
    void toMessageActivity(){
        Bundle bundle=new Bundle();
        bundle.putParcelable(AppConstants.ITEM,user);
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
                bundle.putParcelable(AppConstants.ITEM,user);
                ViewUtil.openActivity(NoteActivity.class,bundle,this, ActivityModel.ACTIVITY_MODEL_1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
