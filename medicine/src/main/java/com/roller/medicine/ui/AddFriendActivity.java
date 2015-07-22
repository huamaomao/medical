package com.roller.medicine.ui;

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
import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.viewmodel.DataModel;

import butterknife.InjectView;
import butterknife.OnClick;
/**
 *
 */
public class AddFriendActivity extends BaseToolbarActivity{
    @InjectView(R.id.et_tel)
    EditText et_tel;
    private DataModel dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("添加朋友");
        dataService=new DataModel();
    }


    @OnClick(R.id.ll_scanning)
    void doScanning(){
       ViewUtil.openActivity(CaptureActivity.class, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        ViewUtil.onHideSoftInput(this,getCurrentFocus(),event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_add:
                doAdd();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void doAdd(){
        if (CommonUtil.isEmpty(et_tel.getText().toString())){
            showLongMsg("请输入手机号");
            return;
        }
        if (!CommonUtil.isMobileNO(et_tel.getText().toString())){
            showLongMsg("手机格式错误");
            return;
        }
        dataService.requestAddFriend(et_tel.getText().toString(), new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                showLongMsg("添加成功");
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                new AppHttpExceptionHandler().via(getContext()).handleException(e,info);
            }
        });
    }

}
