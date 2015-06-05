package com.roller.medicine.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.lidroid.xutils.exception.HttpException;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseToolbarActivity;
import com.roller.medicine.httpservice.MedicineDataService;
import com.roller.medicine.info.BaseInfo;
import com.roller.medicine.myinterface.SimpleResponseListener;

import butterknife.InjectView;
import butterknife.OnClick;
/**
 *
 */
public class AddFriendActivity extends BaseToolbarActivity{
    @InjectView(R.id.et_tel)
    EditText et_tel;
    private MedicineDataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("添加朋友");
        dataService=new MedicineDataService();
    }


    @OnClick(R.id.ll_scanning)
    void doScanning(){
       ViewUtil.openActivity(CaptureActivity.class, this);
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
                doAdd();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void doAdd(){
        if (CommonUtil.isEmpty(et_tel.getText().toString())){
            showLongMsg("请输入手机号");
            return;
        }
        if (!CommonUtil.isMobileNO(et_tel.getText().toString())){
            showLongMsg("手机格式错误");
            return;
        }
        try {
            dataService.requestAddFriend(et_tel.getText().toString(), new SimpleResponseListener<BaseInfo>() {
                @Override
                public void requestSuccess(BaseInfo info, String result) {
                    showLongMsg("添加成功");
                }

                @Override
                public void requestError(HttpException e, BaseInfo info) {
                    if (CommonUtil.notNull(info)) {
                        showLongMsg(info.message);
                    } else if (CommonUtil.notNull(e)) {
                        showLongMsg(e.getMessage());
                    }
                }
            });
        }catch (Exception e){}
    }

}
