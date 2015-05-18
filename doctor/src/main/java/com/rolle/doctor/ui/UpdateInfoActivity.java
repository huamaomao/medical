package com.rolle.doctor.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.DateUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.domain.UploadPicture;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import butterknife.InjectView;
import butterknife.OnClick;

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
        ViewUtil.startPictureActivity(getContext());
    }

    @OnClick(R.id.ll_item_1)
    void onDate(){
        timePicker.show();
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
        userModel.uploadPicture("71", user.headImage,new ViewModel.ModelListener<UploadPicture>(){
            @Override
            public void model(Response response, UploadPicture o) {
                user.photoId=o.id;
            }

            @Override
            public void errorModel(HttpException e, Response response) {
                msgShow("图片上传失败...");
            }

            @Override
            public void view() {

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
        if (Constants.SEX_GIRL.equals(user.sex)){
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
                        user.sex = Constants.SEX_BOY;
                        break;
                    case R.id.rb_tab_2:
                        user.sex = Constants.SEX_GIRL;
                        break;
                }
            }
        });
        tv_date.setText(CommonUtil.initTextNull(user.birthday));
        Picasso.with(getContext()).load(user.headImage).placeholder(R.drawable.icon_default).
                transform(new CircleTransform()).into(iv_photo);
        loadingFragment.setMessage("正在提交数据...");
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
        userModel.requestSaveUser(user, new ViewModel.ModelListener() {
            @Override
            public void model(Response response, Object o) {

            }

            @Override
            public void errorModel(HttpException e, Response response) {

            }

            @Override
            public void view() {
                hideLoading();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_save:
                updateUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
