package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class NoteActivity extends BaseActivity{

    @InjectView(R.id.et_name)
    EditText et_name;
    private FriendResponse.Item user;

    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("备注信息");
        userModel=new UserModel(getContext());
        user=getIntent().getParcelableExtra(Constants.ITEM);
        if (CommonUtil.notNull(user)){
            et_name.setText(user.noteName);
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
            msgShow("昵称不能为空");
            return;
        }
        user.noteName=et_name.getText().toString();
        userModel.requestAddFriend(user.id + "", et_name.getText().toString(), new ViewModel.ModelListener<ResponseMessage>() {
            @Override
            public void model(Response response, ResponseMessage responseMessage) {
                userModel.saveUser(user);
                msgShow("保存成功");
            }

            @Override
            public void errorModel(ModelEnum modelEnum) {
                msgShow("保存失败");
            }

            @Override
            public void view() {

            }
        });
    }
}
