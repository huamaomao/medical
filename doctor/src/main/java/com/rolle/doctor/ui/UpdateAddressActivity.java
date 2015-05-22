package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.common.domain.ResponseMessage;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.viewmodel.UserModel;

import butterknife.InjectView;

/**
 * 工作地址
 */
public class UpdateAddressActivity extends BaseLoadingActivity{

    @InjectView(R.id.et_address)
    EditText et_intro;
    private UserModel userModel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_work_address);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("详细地址");
        userModel=new UserModel(getContext());
        user=userModel.getLoginUser();
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
                user.address=et_intro.getText().toString();
                user.doctorDetail.workAddress=user.workRegion+et_intro.getText().toString();
                showLoading();
                userModel.requestSaveUser(user,new ViewModel.ModelListener<ResponseMessage>() {
                        @Override
                        public void model(Response response, ResponseMessage o) {
                            msgShow("保存成功");
                            finish();
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
