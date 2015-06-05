package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.lidroid.xutils.exception.HttpException;
import com.roller.medicine.R;
import com.roller.medicine.adapter.DoctorCommentAdapater;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.httpservice.MedicineDataService;
import com.roller.medicine.info.BaseInfo;
import com.roller.medicine.info.DoctorDetialInfo;
import com.roller.medicine.info.RecommendedInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.myinterface.SimpleResponseListener;
import com.roller.medicine.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import butterknife.InjectView;
import butterknife.OnClick;
/**
 * Created by Hua_ on 2015/3/27.
 */
public class DoctorDetialActivity extends BaseLoadingToolbarActivity{

    @InjectView(R.id.rv_view)
    RecyclerView recyclerView;
    private DoctorCommentAdapater adapater;
    private List<RecommendedInfo.Item> data;
    private MedicineDataService userModel;
    private UserInfo user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detial);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("详细资料");
        userModel=new MedicineDataService();
        user=getIntent().getParcelableExtra(Constants.ITEM);
        if (CommonUtil.isNull(user)){
            finish();
        }
        data=new ArrayList<>();
        data.add(new RecommendedInfo.Item());
        adapater=new DoctorCommentAdapater(data,this,user);
        ViewUtil.initRecyclerView(recyclerView, this, adapater);
        doDoctor();

    }
    @OnClick(R.id.iv_send)
    void toMessageActivity(){
        Bundle bundle=new Bundle();
        bundle.putParcelable(Constants.ITEM, user);
        ViewUtil.openActivity(MessageActivity.class, bundle, this, ActivityModel.ACTIVITY_MODEL_1);
    }


    public void doDoctor(){
       try {
           userModel.requestDoctorDetail(user.id + "", new SimpleResponseListener<DoctorDetialInfo>() {
               @Override
               public void requestSuccess(DoctorDetialInfo info, String result) {
                    if (CommonUtil.notNull(info.user)){
                        adapater.setUserInfo(info.user);
                    }
                   if (CommonUtil.notNull(info.list)){
                       data.addAll(info.list);
                   }
                   adapater.notifyDataSetChanged();
               }

               @Override
               public void requestError(HttpException e, BaseInfo info) {

               }
           });
       }catch (Exception e){

       }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_interest, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_add:
                doAddFriend();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void doAddFriend(){

        showLoading();
        try {
            userModel.requestAddFriendId(user.id + "",  user.noteName, new SimpleResponseListener<BaseInfo>() {
                @Override
                public void requestSuccess(BaseInfo info, String result) {
                    showLongMsg("关注成功");
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
            showLongMsg("关注失败");
        }
    }
}
