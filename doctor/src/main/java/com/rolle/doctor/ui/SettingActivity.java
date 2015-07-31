package com.rolle.doctor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.domain.ResponseMessage;
import com.android.common.domain.Version;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.SettingAdapater;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity{

    private List<ItemInfo> lsData;
    @InjectView(R.id.rv_view)
    RecyclerView recyclerView;
    private SettingAdapater settingAdapater;
    private UserModel userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        userModel=new UserModel(getContext());

    }

    void onLoginOut(){
        userModel.setLoginOut();
        ViewUtil.startTopActivity(LoginActivity.class, getContext());
        android.os.Process.killProcess(android.os.Process.myPid()); //获取PID
        finish();

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("设置");
        lsData=new ArrayList<ItemInfo>();
        lsData.add(new ItemInfo());
        lsData.add(new ItemInfo("意见反馈"));
        //lsData.add(new ItemInfo("给我们评分"));
        lsData.add(new ItemInfo("关于我们"));
        lsData.add(new ItemInfo(""));
        settingAdapater=new SettingAdapater(getContext(),lsData);
        ViewUtil.initRecyclerViewItem(recyclerView, getContext(), settingAdapater);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 1:
                        ViewUtil.openActivity(FeedbackActivity.class, getContext());
                        break;
                    case 2:
                        ViewUtil.openActivity(AboutUsActivity.class, getContext());
                        break;
                    case 3:
                        onLoginOut();
                        break;
                }
            }
        }));
        doVersion();
    }

    void doVersion(){
        userModel.requestVersion(new SimpleResponseListener<Version>() {
            @Override
            public void requestSuccess(Version info, Response response) {

            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }
        });
    }
}
