package com.rolle.doctor.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.InviteInfo;
import com.rolle.doctor.domain.User;
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
public class InviteActivity extends BaseActivity {
    @InjectView(R.id.tv_code)
    TextView tvCode;
    @InjectView(R.id.tv_amount)
    TextView tv_amount;

    private UserModel userModel;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("邀请好友");
        userModel=new UserModel(getContext());
        user=userModel.getLoginUser();
        tvCode.setText(user.inviteCode);
        requestData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_invite_explain, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (CommonUtil.isFastClick())return true;
        switch (item.getItemId()){
            case R.id.toolbar_add:
                ViewUtil.openActivity(InviteExPlainActivity.class,getContext());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestData(){
        userModel.requestInvite(new SimpleResponseListener<InviteInfo>() {
            @Override
            public void requestSuccess(InviteInfo info, Response response) {
                StringBuilder builder=new StringBuilder();
                builder.append("您已经获得");
                int start=builder.length();
                builder.append(info.rewardsMoney).append("元").append("\n\n");
                builder.append("您已成功邀请").append(info.doctorNum).append("位医生,");
                builder.append(info.patientNum).append("位患者");
                int end=0;
                SpannableString msg=new SpannableString(builder.toString());
                if (CommonUtil.notEmpty(info.rewardsMoney)){
                    end= info.rewardsMoney.length();
                }
                msg.setSpan(new ForegroundColorSpan(Color.RED),start,start+end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                tv_amount.setText(msg);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }
        });
    }
}
