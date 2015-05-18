package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.WalletChooseAdapater;
import com.rolle.doctor.adapter.WalletListAdapater;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 选择银行卡
 */
public class WalletChooseActivity extends BaseActivity {

    @InjectView(R.id.rv_view)
    RecyclerView rvView;
    private List<Wallet.Item> lsData;
    private WalletChooseAdapater adapater;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycker);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("选择账号");
        userModel=new UserModel(getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        lsData=new ArrayList<>();
        lsData.addAll(userModel.getWallet().list);
        lsData.add(new Wallet.Item());
        adapater=new WalletChooseAdapater(this,lsData);
        ViewUtil.initRecyclerView(rvView, getContext(), adapater);
        rvView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != adapater.getItemCount() - 1) {
                    adapater.index = position;
                } else {
                    ViewUtil.openActivity(WalletCashoutPwdActivity.class, WalletChooseActivity.this);
                }
            }
        }));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_add:
                ViewUtil.openActivity(AddBlankActivity.class,this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
