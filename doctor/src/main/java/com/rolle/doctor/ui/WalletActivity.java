package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.WalletListAdapater;
import com.rolle.doctor.domain.ItemInfo;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walle);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("钱包");
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        lsData=new ArrayList<ItemInfo>();
        lsData.add(new ItemInfo(R.drawable.icon_amount_smail,"1000.0",WalletListAdapater.TYPE_ACMOUNT));
        lsData.add(new ItemInfo("招商银行卡","433*******2223",WalletListAdapater.TYPE_BLANK));
        lsData.add(new ItemInfo(R.drawable.icon_blank_add,"添加银行卡",WalletListAdapater.TYPE_ADD));
        adapater=new WalletListAdapater(this,lsData);
        rvView.setAdapter(adapater);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_walle,menu);
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
