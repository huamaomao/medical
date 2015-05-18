package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.common.fragment.LoadingFragment;
import com.rolle.doctor.R;
import com.rolle.doctor.presenter.RegisterPresenter;

import butterknife.InjectView;

/**
 * 注册 one.
 */
public class BaseLoadingActivity extends BaseActivity{


    protected LoadingFragment loadingFragment;
    private final  static String commit="正在提交...";
    private final  static String login="正在登陆...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingFragment=LoadingFragment.newInstance();
        loadingFragment.setMessage(login);
    }

    public void setCommitMessage(){
        loadingFragment.setMessage(commit);
    }
    public void setLoginMessage(){
        loadingFragment.setMessage(login);
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    public void showLoading() {
        loadingFragment.show(getSupportFragmentManager(),"loading");

    }

    @Override
    public void hideLoading() {
        loadingFragment.dismiss();

    }
}
