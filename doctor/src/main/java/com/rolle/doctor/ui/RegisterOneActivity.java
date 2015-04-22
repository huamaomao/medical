package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.common.fragment.LoadingFragment;
import com.android.common.util.ActivityModel;
import com.android.common.util.ViewUtil;
import com.android.common.widget.InputMethodLinearLayout;
import com.rolle.doctor.R;
import com.rolle.doctor.presenter.RegisterPresenter;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 注册 one.
 */
public class RegisterOneActivity extends BaseLoadingActivity implements RegisterPresenter.IRegisterView{

    private RegisterPresenter presenter;
    @InjectView(R.id.et_tel) EditText etTel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
    }

    @Override
    protected void onBackActivty() {
        ViewUtil.openActivity(LoginActivity.class,null,this, ActivityModel.ACTIVITY_MODEL_2,true);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("注册");
        loadingFragment.setMessage("正在提交...");
        presenter=new RegisterPresenter(this);
    }


    @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_next:
                presenter.doFirstRegister();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public String getTel() {
        return etTel.getText().toString();
    }


}
