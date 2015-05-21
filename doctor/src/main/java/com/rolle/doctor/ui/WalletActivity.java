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
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.WalletListAdapater;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/4/15.
 */
public class WalletActivity extends BaseActivity {

    @InjectView(R.id.rv_view)
    RecyclerView rvView;
    private List<ItemInfo> lsData;
    private WalletListAdapater adapater;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walle);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("钱包");
        userModel=new UserModel(getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        lsData=new ArrayList<>();
        lsData.add(new ItemInfo(R.drawable.icon_amount_smail,CommonUtil.formatMoney("0"),WalletListAdapater.TYPE_ACMOUNT));
        lsData.add(new ItemInfo(R.drawable.icon_blank_add,"添加支付宝",WalletListAdapater.TYPE_ADD));
        adapater=new WalletListAdapater(this,lsData);
        ViewUtil.initRecyclerView(rvView,getContext(),adapater);
        rvView.addOnItemTouchListener(new RecyclerItemClickListener(this,new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               if(position==0){
                    ViewUtil.openActivity(WalletDetialActivity.class,WalletActivity.this);
                }else if (position==adapater.getItemCount()-1){
                    ViewUtil.openActivity(AddBlankActivity.class,WalletActivity.this);
                }
            }}));
        loadData();
    }

    private void loadData(){
        userModel.requestWallet(new ViewModel.ModelListener<Wallet>() {
            @Override
            public void model(Response response, Wallet wallet) {
                if (wallet==null)return;
                ItemInfo itemInfo=lsData.get(0);
                itemInfo.title= CommonUtil.formatMoney(wallet.accountAmount);
                adapater.notifyItemChanged(0);
                if (wallet.list!=null){
                    for (Wallet.Item item:wallet.list){
                        lsData.add(1,new ItemInfo("支付宝账号",item.mobile,WalletListAdapater.TYPE_BLANK));
                    }
                    adapater.notifyItemRangeChanged(1,wallet.list.size());
                }
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
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_walle, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_walle:
                ViewUtil.openActivity(WalletBillActivity.class,this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
