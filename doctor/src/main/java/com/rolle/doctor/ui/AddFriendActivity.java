package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.presenter.AddFriendPresenter;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 *
 */
public class AddFriendActivity extends BaseActivity implements AddFriendPresenter.IFriendView{

    private AddFriendPresenter presenter;

    @InjectView(R.id.et_tel)
    EditText et_tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        presenter=new AddFriendPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("添加朋友");
    }

    @OnClick(R.id.ll_invite)
    void doInvite(){
        ViewUtil.openActivity(MyInviteCodeActivity.class, this);
    }

    @OnClick(R.id.ll_scanning)
    void doScanning(){
       // ViewUtil.openActivity(MyInviteCodeActivity.class, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_add:
                presenter.doAdd();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getTel() {
        return et_tel.getText().toString();
    }
}
