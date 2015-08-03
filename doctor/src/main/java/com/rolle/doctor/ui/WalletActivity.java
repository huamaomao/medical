package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.WalletListAdapater;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.event.BaseEvent;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

import static com.rolle.doctor.R.mipmap;
import static com.rolle.doctor.R.mipmap.icon_amount_smail;
import static com.rolle.doctor.R.mipmap.icon_blank_add;

/**
 * 钱包
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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("钱包");
        userModel=new UserModel(getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        lsData=new ArrayList<>();
        lsData.add(new ItemInfo(icon_amount_smail,CommonUtil.formatMoney("0"),WalletListAdapater.TYPE_ACMOUNT));
        lsData.add(new ItemInfo(icon_blank_add,"添加支付宝",WalletListAdapater.TYPE_ADD));
        adapater=new WalletListAdapater(this,lsData);
        ViewUtil.initRecyclerView(rvView, getContext(), adapater);
        rvView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (CommonUtil.isFastClick())return;
                if (position == 0) {
                    ViewUtil.openActivity(WalletDetialActivity.class, WalletActivity.this);
                } else if (position == adapater.getItemCount() - 1) {
                    ViewUtil.openActivity(AddBlankActivity.class, WalletActivity.this);
                }
            }
        }));
        loadData();
    }


    private void doWalletList(){
         Wallet wallet=userModel.getWallet();
        if (CommonUtil.notNull(wallet)){
            //ItemInfo itemInfo=lsData.get(0);
            lsData.clear();
            lsData.add(new ItemInfo(icon_amount_smail, CommonUtil.formatMoney(wallet.accountAmount), WalletListAdapater.TYPE_ACMOUNT));
            if (wallet.list!=null){
                for (Wallet.Item item:wallet.list){
                    if ("zfb".equals(item.type)){
                        lsData.add(1,new ItemInfo("支付宝账号",item.email,WalletListAdapater.TYPE_BLANK));
                    }else {
                        lsData.add(1,new ItemInfo(item.name,item.account,WalletListAdapater.TYPE_BLANK));
                    }

                }
               // adapater.notifyItemRangeChanged(1,wallet.list.size());
            }
            lsData.add(new ItemInfo(icon_blank_add,"添加支付宝",WalletListAdapater.TYPE_ADD));
            adapater.notifyDataSetChanged();
        }
    }


    private void loadData(){
        userModel.requestWallet(new SimpleResponseListener<Wallet>() {
            @Override
            public void requestSuccess(Wallet wallet, Response response) {
                doWalletList();
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                doWalletList();
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
        if (CommonUtil.isFastClick()) {
            return false;
        }
        switch (item.getItemId()){
            case R.id.toolbar_walle:
                ViewUtil.openActivity(WalletBillActivity.class,this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public  void onEvent(BaseEvent event){
        if (event.type==BaseEvent.EV_WALLET_LIST){
            doWalletList();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
