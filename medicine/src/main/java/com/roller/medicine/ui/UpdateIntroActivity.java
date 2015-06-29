package com.roller.medicine.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.viewmodel.DataModel;
import butterknife.InjectView;

/**
 * 简介
 */
public class UpdateIntroActivity extends BaseLoadingToolbarActivity{

    @InjectView(R.id.et_intro)
    EditText et_intro;
    private DataModel userModel;
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_intro);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("修改简介");
        userModel=new DataModel();
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
                    showMsg("请填写简介.....");
                    return true;
                }
                user.intro=et_intro.getText().toString();
                showLoading();
                userModel.saveDoctor(user, new SimpleResponseListener<ResponseMessage>() {
                    @Override
                    public void requestSuccess(ResponseMessage info, Response response) {
                        showMsg("保存成功");
                        userModel.saveUser(user);
                        finish();
                    }

                    @Override
                    public void requestError(HttpException e, ResponseMessage info) {
                        new AppHttpExceptionHandler().via(getContext()).handleException(e,info);
                    }

                    @Override
                    public void requestView() {
                        hideLoading();
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
