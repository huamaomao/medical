package com.rolle.doctor.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.DateUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.UploadPicture;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.event.BaseEvent;
import com.rolle.doctor.service.RequestService;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.RequestTag;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Date;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 *
 */
public class UpdateInfoActivity extends BaseLoadingActivity{

    @InjectView(R.id.iv_photo)
    ImageView iv_photo;
    @InjectView(R.id.et_name)
    EditText et_name;
    @InjectView(R.id.rg_group)
    RadioGroup radioGroup;
    @InjectView(R.id.tv_date)
    TextView tv_date;
    private UserModel userModel;
    private User user;
    private SlideDateTimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
    }

    @OnClick(R.id.ll_item_0)
    void onUploadPhoto(){
        if (CommonUtil.isFastClick())return;
        ViewUtil.startPictureActivity(getContext());
    }

    @OnClick(R.id.ll_item_1)
    void onDate(){
        if (CommonUtil.isFastClick())return;
        timePicker.show();
    }

     void onEvent(BaseEvent event)
    {
        if (CommonUtil.notNull(event)&&event.type==BaseEvent.EV_TOKEN_OUT){
            userModel.setLoginOut();

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 选取图片的返回值
        if (requestCode == com.android.common.util.Constants.CODE_PIC) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    user.headImage=ViewUtil.getRealFilePath(getContext(), uri);
                    uploadPhoto();
                }
            }
        }

    }

    private void uploadPhoto(){
        Picasso.with(getContext()).load(new File( user.headImage)).placeholder(R.drawable.icon_default).
                transform(new CircleTransform()).into(iv_photo);
        userModel.uploadPicture("71", user.headImage, new SimpleResponseListener<UploadPicture>() {
            @Override
            public void requestSuccess(UploadPicture info, Response response) {
                user.photoId=info.id;
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                msgShow("图片上传失败...");
            }
        });

    }


    @Override
    protected void initView() {
        super.initView();
        userModel=new UserModel(getContext());
        user=userModel.getLoginUser();
        timePicker=new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        tv_date.setText(DateUtil.formatYMD(date));
                        user.birthday=tv_date.getText().toString();
                    }
                }).setInitialDate(new Date())
                .build();
        setBackActivity("个人信息");
        et_name.setText(user.nickname);
        et_name.setSelection(user.nickname.length());
        if (AppConstants.SEX_GIRL.equals(user.sex)){
            radioGroup.check(R.id.rb_tab_2);
        }else {
            radioGroup.check(R.id.rb_tab_1);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_tab_1:
                    default:
                        user.sex = AppConstants.SEX_BOY;
                        break;
                    case R.id.rb_tab_2:
                        user.sex = AppConstants.SEX_GIRL;
                        break;
                }
            }
        });
        tv_date.setText(CommonUtil.initTextNull(user.birthday));
        Picasso.with(getContext()).load(user.headImage).placeholder(R.drawable.icon_default).
                transform(new CircleTransform()).into(iv_photo);
        loadingFragment.setCommitMessage();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void updateUser(){
        if (CommonUtil.isEmpty(et_name.getText().toString())){
            msgShow("真实姓名不能为空");
            return;
        }
        user.nickname=et_name.getText().toString();
        showLoading();
        userModel.requestSaveUser(user, new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                msgShow("修改成功");
                Util.startRequestService(getContext(),RequestTag.R_USER_UP);
                EventBus.getDefault().post(new BaseEvent(BaseEvent.EV_USER_INFO));
                finish();

            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                new AppHttpExceptionHandler().via(getContext()).handleException(e,info);
            }

            @Override
            public void requestView() {
                hideLoading();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_save:
                if (CommonUtil.isFastClick())return true;
                updateUser();
               return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
