package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.rolle.doctor.R;
import com.rolle.doctor.presenter.WriteInviteCodePresenter;
import com.rolle.doctor.presenter.YaoqingPresenter;

import butterknife.InjectView;

/**
 *
 */
public class WriteInviteCodeActivity extends BaseLoadingActivity implements WriteInviteCodePresenter.IInviteCodeView{

    @InjectView(R.id.et_code)
    EditText et_code;
    private WriteInviteCodePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invite_code);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("填写邀请码");
        presenter=new WriteInviteCodePresenter(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.toolbar_register:
                presenter.doSave();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public String getInviteCode() {
        return et_code.getText().toString();
    }


}
