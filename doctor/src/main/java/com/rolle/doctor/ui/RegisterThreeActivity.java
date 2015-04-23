package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.common.util.Constants;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.DoctorListAdpater;
import com.rolle.doctor.presenter.RegisterThreePresenter;
import com.rolle.doctor.util.Util;

import java.util.ArrayList;
import java.util.List;
import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class RegisterThreeActivity extends BaseLoadingActivity implements RegisterThreePresenter.IRegisterView{

    @InjectView(R.id.sp_doctor)Spinner spDoctor;
    @InjectView(R.id.et_pwd)EditText et_pwd;
    @InjectView(R.id.et_name)EditText et_name;
    private  String tel;
    private String code;
    private RegisterThreePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_three);
        tel=getIntent().getStringExtra(Constants.DATA_TEL);
        presenter=new RegisterThreePresenter(this);
    }


    @Override
    protected void initView() {
        setBackActivity("上一步");
        loadingFragment.setMessage("正在提交...");
        super.initView();
        List<String> startData=new ArrayList<String>();
        startData.add("医生");
        startData.add("营养师");
       /* DoctorListAdpater adapter = new DoctorListAdpater(this,R.layout.sp_check_doctor,R.id.tv_item_0,startData);
        spDoctor.setAdapter(adapter);
*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.toolbar_register:
                presenter.doRegister();
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
    public String getTel() {
        return tel;
    }

    @Override
    public String getPwd() {
        return et_pwd.getText().toString();
    }

    @Override
    public String getType() {
        return Util.getUserType(spDoctor.getSelectedItem().toString());
    }

    @Override
    public String getNickName() {
        return et_name.getText().toString();
    }
}
