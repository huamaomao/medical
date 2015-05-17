package com.rolle.doctor.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.UploadPicture;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 认证
 */
public class AuthenticationActivity extends BaseLoadingActivity{

    @InjectView(R.id.iv_add_doctor)
    ImageView iv_add_doctor;
    @InjectView(R.id.iv_add_idcard)
    ImageView iv_add_idcard;
    private UserModel userModel;
    private User user;
    private boolean flag;
    private String doctorPath;
    private String doctorUrl;
    private String idcardPath;
    private String idcardUrl;

    /*****初始化上传数***/
    private int status=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
    }
    /******
     *医生执照
     */
    @OnClick(R.id.iv_add_doctor)
    void onUploadDoctor(){
        flag=false;
        ViewUtil.startPictureActivity(getContext());
    }
    /******
     *身份证执照
     */
    @OnClick(R.id.iv_add_idcard)
    void onUploadIdCard(){
        flag=true;
        ViewUtil.startPictureActivity(getContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 选取图片的返回值
        if (requestCode == com.android.common.util.Constants.CODE_PIC) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    if (flag){
                        iv_add_idcard.setTag(ViewUtil.getRealFilePath(getContext(), uri));
                        Picasso.with(this).load(new File(iv_add_idcard.getTag().toString())).placeholder(R.drawable.icon_photo_add).
                                resize(120,120).into(iv_add_idcard);
                    }else {
                        iv_add_doctor.setTag(ViewUtil.getRealFilePath(getContext(), uri));
                        Picasso.with(this).load(new File(iv_add_doctor.getTag().toString())).placeholder(R.drawable.icon_photo_add).
                                 resize(120, 120).into(iv_add_doctor);
                    }
                }
            }
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("doctorPath", doctorPath);
        outState.putString("idcardPath",idcardPath);
        super.onSaveInstanceState(outState, outPersistentState);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        doctorPath=savedInstanceState.getString("doctorPath");
        idcardPath=savedInstanceState.getString("idcardPath");
    }

    /*****
     *      24  身份证     26  执照
     */
    private void uploadPhoto(){
        if (CommonUtil.isEmpty(iv_add_doctor.getTag().toString())){
            msgShow("请上传从医证件照...");
            return;
        }
        if (CommonUtil.isEmpty(iv_add_idcard.getTag().toString())){
            msgShow("请上传身份证件照...");
            return;
        }
        showLoading();
        status=0;
        userModel.uploadPicture("24",iv_add_idcard.getTag().toString(), new ViewModel.ModelListener<UploadPicture>() {
            @Override
            public void model(Response response, UploadPicture o) {
                user.photoId = o.idList[0];
                success();
            }

            @Override
            public void errorModel(HttpException e, Response response) {

            }

            @Override
            public void view() {
                hideLoading();
            }
        });
        userModel.uploadPicture("26", iv_add_doctor.getTag().toString(), new ViewModel.ModelListener<UploadPicture>() {
            @Override
            public void model(Response response, UploadPicture o) {
                user.photoId = o.idList[0];
                success();
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
    protected void initView() {
        super.initView();
        userModel=new UserModel(getContext());
        user=userModel.getLoginUser();
        setBackActivity("认证");
    }

    private void success(){
        status++;
        if (status==2){
            hideLoading();
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
                uploadPhoto();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
