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
import com.roller.medicine.utils.Constants;
import com.roller.medicine.viewmodel.DataModel;

import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class NoteActivity extends BaseLoadingToolbarActivity{

    @InjectView(R.id.et_name)
    EditText et_name;
    private UserInfo user;
    private DataModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("备注信息");
        userModel=new DataModel();
        user=getIntent().getParcelableExtra(Constants.ITEM);
        if (CommonUtil.notNull(user)){
            et_name.setText(user.noteName);
            et_name.setSelection(user.noteName.length());
        }

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
                setNoteName();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNoteName(){
        if (CommonUtil.isEmpty(et_name.getText().toString())){
            showLongMsg("昵称不能为空");
            return;
        }
        showLoading();
        userModel.requestAddFriendId(user.id + "", et_name.getText().toString(), new SimpleResponseListener() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                showLongMsg("设置成功");
                user.noteName=et_name.getText().toString();
                userModel.saveUser(user);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                new AppHttpExceptionHandler().via(getContent()).handleException(e, info);
            }
            @Override
            public void requestView() {
                hideLoading();
            }
        });
    }
}
