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
import com.android.common.util.ViewUtil;
import com.android.common.widget.InputMethodLinearLayout;
import com.rolle.doctor.R;
import com.rolle.doctor.presenter.RegisterPresenter;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 注册 one.
 */
public class RegisterOneActivity extends BaseActivity implements RegisterPresenter.IRegisterView{

    private RegisterPresenter presenter;
    private LoadingFragment loadingFragment;
    @InjectView(R.id.et_tel) EditText etTel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("注册");
        loadingFragment=LoadingFragment.newInstance();
        loadingFragment.setMessage("正在登陆...");
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

    @Override
    public void showLoading() {
    /*    LoadingFragment loadingFragment=LoadingFragment.newInstance();
        loadingFragment.show(getSupportFragmentManager(),"loading");*/
    }

    @Override
    public void hideLoading() {
       /* Fragment prev = getSupportFragmentManager().findFragmentByTag("loading");
        if (prev != null) {
            LoadingFragment df = (LoadingFragment) prev;
            df.dismiss();
        }*/
    }
}
