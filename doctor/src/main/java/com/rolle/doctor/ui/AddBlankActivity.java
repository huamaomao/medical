package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.ViewModel;
import com.astuetz.PagerSlidingTabStrip;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.CitySpinnerAdpater;
import com.rolle.doctor.adapter.ViewPagerAdapter;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.ListModel;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 添加账号
 */
public class AddBlankActivity extends BaseLoadingActivity implements View.OnClickListener{
    EditText et_alipay;
     // EditText et_alipay_name;
    EditText et_blank_account;
    //EditText et_blank_name;
    Button btn_alipay_commit;
    Button btn_blank_commit;

    Spinner sp_start;

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabStrip;
    @InjectView(R.id.viewpage)
    ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private final String[] titles={"支付宝","银行卡"};
    private UserModel userModel;
    private ListModel listModel;
    private Wallet wallet;

    private List<CityResponse.Item> itemList;

    CitySpinnerAdpater spinnerAdpater;
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
       // et_alipay_name=ButterKnife.findById(views.get(0),R.id.et_alipay_name);
        btn_alipay_commit=ButterKnife.findById(views.get(0),R.id.btn_alipay_commit);


        et_blank_account=ButterKnife.findById(views.get(1),R.id.et_blank_account);
        //et_blank_name=ButterKnife.findById(views.get(1),R.id.et_blank_name);
        btn_blank_commit=ButterKnife.findById(views.get(1),R.id.btn_blank_commit);
        sp_start=ButterKnife.findById(views.get(1),R.id.sp_start);

        btn_blank_commit.setOnClickListener(this);
        btn_alipay_commit.setOnClickListener(this);

        wallet=userModel.getWallet();

        if (wallet==null)finish();
        itemList=new ArrayList<>();
        listModel=new ListModel(getContext());
        spinnerAdpater=new CitySpinnerAdpater(getContext(),R.layout.sp_check_text,itemList);
        sp_start.setAdapter(spinnerAdpater);
        loadBlank();
        setCommitMessage();


    }

    public void loadBlank(){
        listModel.requestTitle("97", new ViewModel.ModelListener<List<CityResponse.Item>>() {
            @Override
            public void model(Response response, List<CityResponse.Item> items) {
                itemList.clear();
                itemList.addAll(items);
                spinnerAdpater.notifyDataSetChanged();
            }

            @Override
            public void errorModel(HttpException e, Response response) {

            }

            @Override
            public void view() {

            }
        });
    }


     void onAlipayAccount(){
         if (!CommonUtil.isMobileNO(et_alipay.getText().toString())){
           if (!CommonUtil.isEmail(et_alipay.getText().toString())){
                 msgLongShow("账号格式错误...");
                 return;
             }
         }
        showLoading();
        userModel.requestAddWalletAcounnt(et_alipay.getText().toString(), new ViewModel.ModelListener<ResponseMessage>() {
            @Override
            public void model(Response response, ResponseMessage responseMessage) {
                success();
            }

            @Override
            public void errorModel(HttpException e, Response response) {
                msgLongShow("绑定支付宝失败..");
            }

            @Override
            public void view() {
                hideLoading();
            }
        });
    }

    void onBlankAccount(){
        CityResponse.Item item=itemList.get((int)sp_start.getSelectedItemId());
        if (CommonUtil.isEmpty(et_blank_account.getText().toString())){
            msgLongShow("账号格式错误...");
            return;
        }

        if (item==null){
            msgLongShow("请选择银行...");
            return;
        }

        showLoading();
        userModel.requestAddWalletBlankAcounnt(et_blank_account.getText().toString(), item.id, new ViewModel.ModelListener<ResponseMessage>() {
            @Override
            public void model(Response response, ResponseMessage responseMessage) {
                success();
            }

            @Override
            public void errorModel(HttpException e, Response response) {
                msgLongShow("绑定银行卡失败...");
            }

            @Override
            public void view() {
                hideLoading();
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

        }
    }

    public void success(){
        if (CommonUtil.notNull(wallet.password)){
            msgLongShow("绑定成功...");
            ViewUtil.openActivity(WalletSetPwdActivity.class, getContext(), true);
          finish();
        }else {
            ViewUtil.openActivity(WalletSetPwdActivity.class,getContext(),true);
        }
    }
}
