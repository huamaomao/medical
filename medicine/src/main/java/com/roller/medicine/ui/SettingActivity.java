package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;

import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.domain.ResponseMessage;
import com.android.common.domain.Version;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.adapter.SettingAdapater;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.info.ItemInfo;
import com.roller.medicine.viewmodel.DataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 设置
 */
public class SettingActivity extends BaseLoadingToolbarActivity{
    private List<ItemInfo> lsData;
    @InjectView(R.id.rv_view)
    RecyclerView recyclerView;
    private SettingAdapater settingAdapater;
    private DataModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        userModel=new DataModel();
        requestVersion();

    }

    public void requestVersion(){

        userModel.requestVersion(new SimpleResponseListener<Version>() {
            @Override
            public void requestSuccess(Version info, Response response) {

            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }
        });
    }

    void onLoginOut(){
        userModel.setLoginOut();
        ViewUtil.startTopActivity(LoginActivity.class,getContext());
        finish();
        android.os.Process.killProcess(android.os.Process.myPid()); //获取PID
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("设置");
        lsData=new ArrayList();
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
                switch (position){
                    case 1:
                        ViewUtil.openActivity(FeedbackActivity.class,getContext());
                        break;
                    case 2:
                        ViewUtil.openActivity(AboutUsActivity.class,getContext());
                        break;
                    case 3:
                        onLoginOut();
                        break;
                }
            }
        }));
    }
}
