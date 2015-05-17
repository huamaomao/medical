package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.viewmodel.ListModel;
import com.rolle.doctor.viewmodel.UserModel;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 简介
 */
public class UpdateIntroActivity extends BaseLoadingActivity{

    @InjectView(R.id.et_intro)
    EditText et_intro;
    private UserModel userModel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_intro);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("修改简介");
        userModel=new UserModel(getContext());
        user=userModel.getLoginUser();
        if(CommonUtil.notEmpty(user.intro)){
            et_intro.setText(user.intro);
            et_intro.setSelection(user.intro.length());
        }
        loadingFragment.setMessage("正在提交数据...");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_save:
                if (CommonUtil.isEmpty(et_intro.getText().toString())) {
                    msgShow("请填写简介.....");
                    return true;
                }
                user.intro=et_intro.getText().toString();
                showLoading();
                userModel.requestSaveUser(user,new ViewModel.ModelListener<ResponseMessage>() {
                        @Override
                        public void model(Response response, ResponseMessage o) {
                            msgShow("保存成功");
                        }

                    @Override
                    public void errorModel(HttpException e, Response response) {
                        msgShow("保存失败");
                    }

                    @Override
                        public void view() {
                            hideLoading();
                        }
                    });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
