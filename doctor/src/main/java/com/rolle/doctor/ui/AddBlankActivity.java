package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
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
import com.rolle.doctor.viewmodel.RequestTag;
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
        listModel.requestTitle("97", new SimpleResponseListener<List<CityResponse.Item>>() {
            @Override
            public void requestSuccess(List<CityResponse.Item> info, Response response) {
                itemList.clear();
                itemList.addAll(info);
                spinnerAdpater.notifyDataSetChanged();
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

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
         userModel.requestAddWalletAcounnt(et_alipay.getText().toString(), new SimpleResponseListener<ResponseMessage>() {
             @Override
             public void requestSuccess(ResponseMessage info, Response response) {
                     success();
             }

             @Override
             public void requestError(HttpException e, ResponseMessage info) {
                    new AppHttpExceptionHandler().via(getContext()).handleException(e, info);
             }

             @Override
             public void requestView() {
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
        userModel.requestAddWalletBlankAcounnt(item.id,et_blank_account.getText().toString(), new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                success();
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                new AppHttpExceptionHandler().via(getContext()).handleException(e, info);
            }

            @Override
            public void requestView() {
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
        Util.startRequestService(getContext(), RequestTag.R_WALLET_LIST);
        if (CommonUtil.notEmpty(wallet.password)){
            msgLongShow("绑定成功...");
            finish();
        }else {
            ViewUtil.openActivity(WalletSetPwdActivity.class,getContext(),true);
        }
    }
}
