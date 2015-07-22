package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.viewmodel.UserModel;

import butterknife.InjectView;

/**
 *
 * 意见反馈
 *
 */
public class FeedbackActivity extends BaseLoadingActivity{

    @InjectView(R.id.et_content)
    EditText et_content;
    private User user;

    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        ViewUtil.onHideSoftInput(this,getCurrentFocus(),event);
        return super.onTouchEvent(event);
    }

    private void saveData(){

        if (CommonUtil.isEmpty(et_content.getText().toString())){
            msgShow("请输入反馈意见....");
            return;
        }
        showLoading();
        userModel.requestSaveFeedback(et_content.getText().toString(), new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                msgShow("提交成功....");
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

    }

    @Override
    protected void initView() {
        super.initView();
        loadingFragment.setMessage("正在提交数据");
        setBackActivity("意见反馈");
        userModel=new UserModel(getContext());
        user=getIntent().getParcelableExtra(AppConstants.ITEM);
        if (CommonUtil.notNull(user)){
            et_content.setText(user.noteName);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_commit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_commit:
                saveData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
