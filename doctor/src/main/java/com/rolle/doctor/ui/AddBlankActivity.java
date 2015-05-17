package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.common.domain.ResponseMessage;
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.astuetz.PagerSlidingTabStrip;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.ViewPagerAdapter;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 添加账号
 */
public class AddBlankActivity extends BaseActivity implements View.OnClickListener{
    EditText et_alipay;
    EditText et_alipay_name;
    EditText et_blank_account;
    EditText et_blank_name;
    Button btn_alipay_commit;
    Button btn_blank_commit;
    TextView tv_code;

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabStrip;
    @InjectView(R.id.viewpage)
    ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private final String[] titles={"支付宝","银行卡"};
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_add);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("添加账号");
        Util.initTabStrip(tabStrip, getContext());
        userModel=new UserModel(getContext());
        List<View> views=new ArrayList<>();
        views.add(getLayoutInflater().inflate(R.layout.viewpage_wallet_zhifubao, null));
        views.add(getLayoutInflater().inflate(R.layout.viewpage_wallet_blank, null));
        pagerAdapter=new ViewPagerAdapter(titles,views);
        viewPager.setAdapter(pagerAdapter);
        tabStrip.setViewPager(viewPager);

        et_alipay=ButterKnife.findById(views.get(0),R.id.et_alipay);
        et_alipay_name=ButterKnife.findById(views.get(0),R.id.et_alipay_name);
        btn_alipay_commit=ButterKnife.findById(views.get(0),R.id.btn_alipay_commit);

        ButterKnife.inject(views.get(1));
        et_blank_account=ButterKnife.findById(views.get(1),R.id.et_blank_account);
        et_blank_name=ButterKnife.findById(views.get(1),R.id.et_blank_name);
        btn_blank_commit=ButterKnife.findById(views.get(1),R.id.btn_blank_commit);
        tv_code=ButterKnife.findById(views.get(1),R.id.tv_code);
        btn_blank_commit.setOnClickListener(this);
        btn_alipay_commit.setOnClickListener(this);
        tv_code.setOnClickListener(this);



    }
     void onAlipayAccount(){
        userModel.requestAddWalletAcounnt("", new ViewModel.ModelListener<ResponseMessage>() {
            @Override
            public void model(Response response, ResponseMessage responseMessage) {

            }

            @Override
            public void errorModel(HttpException e, Response response) {

            }

            @Override
            public void view() {

            }
        });
    }

    void onBlankAccount(){
        userModel.requestAddWalletAcounnt("", new ViewModel.ModelListener<ResponseMessage>() {
            @Override
            public void model(Response response, ResponseMessage responseMessage) {

            }

            @Override
            public void errorModel(HttpException e, Response response) {

            }

            @Override
            public void view() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_blank_commit:
                onBlankAccount();
                break;
            case R.id.btn_alipay_commit:
                onAlipayAccount();
                break;
            case R.id.tv_code:
                onAlipayAccount();
                break;
        }
    }
}
