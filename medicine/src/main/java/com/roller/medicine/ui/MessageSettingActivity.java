package com.roller.medicine.ui;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.util.ActivityModel;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.roller.medicine.R;
import com.roller.medicine.base.BaseLoadingToolbarActivity;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;
import com.roller.medicine.viewmodel.GotyeService;
import com.squareup.picasso.Picasso;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class MessageSettingActivity extends BaseLoadingToolbarActivity{


    @InjectView(R.id.tbtn_swich)
    SwitchCompat tbtn_swich;
    @InjectView(R.id.iv_photo)
    ImageView iv_photo;
    @InjectView(R.id.tv_name)
    TextView tv_name;

    private UserInfo user;
    private DataModel userModel;
    private GotyeService gotyeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_setting);

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("聊天信息");
        userModel=new DataModel();
        user=getIntent().getParcelableExtra(AppConstants.ITEM);

        tbtn_swich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    gotyeService.removeFriendBlocked(user.id + "");
                }else {
                    gotyeService.addFriendBlocked(user.id + "");
                }
                Log.d(gotyeService.isFriendBlocked(user.id+""));
                //tbtn_swich.setChecked(gotyeService.isFriendBlocked(user.id+""));
            }
        });
        Picasso.with(this).load(DataModel.getImageUrl(user.headImage)).placeholder(R.drawable.icon_default).
                transform(new CircleTransform()).into(iv_photo);
        tv_name.setText(user.nickname);
        gotyeService=new GotyeService();
        tbtn_swich.setChecked(gotyeService.isFriendBlocked(user.id+""));

    }


    @OnClick(R.id.btn_del_message)
    void  doDelMessage(){
        gotyeService.clearMessage(user.id+"");
        showLongMsg("记录清除成功");
    }



    @OnClick(R.id.rl_item_0)
    void doUserDetianl(){
        Bundle bundle=new Bundle();
        bundle.putParcelable(AppConstants.ITEM,user);
        ViewUtil.openActivity(DoctorDetialActivity.class, bundle, this, ActivityModel.ACTIVITY_MODEL_1);
    }

    @OnClick(R.id.rl_item_1)
    void doNote(){
        Bundle bundle=new Bundle();
        bundle.putParcelable(AppConstants.ITEM,user);
        ViewUtil.openActivity(NoteActivity.class, bundle, this, ActivityModel.ACTIVITY_MODEL_1);
    }
  /*  @OnClick(R.id.rl_item_2)
    void doFindMessage(){

    }*/

}
