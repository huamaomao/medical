package com.roller.medicine.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.common.util.CommonUtil;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.httpservice.MedicineDataService;
import com.roller.medicine.info.BaseInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.myinterface.SimpleResponseListener;
import com.roller.medicine.utils.Constants;

import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class NoteActivity extends BaseLoadingToolbarActivity{

    @InjectView(R.id.et_name)
    EditText et_name;
    private UserInfo user;
    private MedicineDataService userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("备注信息");
        userModel=new MedicineDataService();
        user=getIntent().getParcelableExtra(Constants.ITEM);
        if (CommonUtil.notNull(user)){
            et_name.setText(user.noteName);
            et_name.setSelection(user.noteName.length());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_save:
                setNoteName();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNoteName(){
        if (CommonUtil.isEmpty(et_name.getText().toString())){
            showLongMsg("昵称不能为空");
            return;
        }
        showLoading();
        try {
            userModel.requestAddFriendId(user.id + "", et_name.getText().toString(), new SimpleResponseListener<BaseInfo>() {
                @Override
                public void requestSuccess(BaseInfo info, String result) {
                    showLongMsg("设置成功");
                    user.noteName=et_name.getText().toString();
                    userModel.saveUser(user);
                }

                @Override
                public void requestError(com.lidroid.xutils.exception.HttpException e, BaseInfo info) {
                    if (CommonUtil.notNull(info)) {
                        showLongMsg(info.message);
                    } else if (CommonUtil.notNull(e)) {
                        showLongMsg(e.getMessage());
                    }
                }

                @Override
                public void requestView() {
                    hideLoading();
                }
            });
        }catch (Exception e){
            hideLoading();
            showLongMsg("设置失败");
        }
    }
}
