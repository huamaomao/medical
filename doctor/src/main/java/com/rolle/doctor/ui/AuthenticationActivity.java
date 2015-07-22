package com.rolle.doctor.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
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
    @InjectView(R.id.tv_status)
    TextView tv_status;

    private UserModel userModel;
    private User user;
    private boolean flag;
    private MenuItem menuItem;
    private String doctorPath;
    private String idcardPath;


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
    @OnClick(R.id.btn_next)
     void uploadPhoto(){
        if (CommonUtil.isEmpty(iv_add_doctor.getTag().toString())){
            msgShow("请上传从医证件照...");
            return;
        }
        if (CommonUtil.isEmpty(iv_add_idcard.getTag().toString())){
            msgShow("请上传身份证件照...");
            return;
        }
        showLoading();
        userModel.uploadPicture("24", iv_add_idcard.getTag().toString(), new SimpleResponseListener<UploadPicture>() {
            @Override
            public void requestSuccess(UploadPicture info, Response response) {
                user.businessLicense = info.id;
                userModel.uploadPicture("26", iv_add_doctor.getTag().toString(), new SimpleResponseListener<UploadPicture>() {
                    @Override
                    public void requestSuccess(UploadPicture info, Response response) {
                        user.businessLicense = info.id;
                        userModel.requestSaveUser(user, new SimpleResponseListener<ResponseMessage>() {
                            @Override
                            public void requestSuccess(ResponseMessage info, Response response) {
                                msgLongShow("认证已经提交，我们会及时审核...");
                                finish();
                            }

                            @Override
                            public void requestError(HttpException e, ResponseMessage info) {
                                new AppHttpExceptionHandler().via(getContext()).handleException(e, info);
                            }

                            @Override
                            public void requestView() {
                                hideLoading();
                            }
                        });
                    }

                    @Override
                    public void requestError(HttpException e, ResponseMessage info) {
                        msgLongShow("证件上传失败...");
                        hideLoading();
                    }
                });
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                msgLongShow("证件上传失败...");
                hideLoading();
            }
        });

    }


    @Override
    protected void initView() {
        super.initView();
        userModel=new UserModel(getContext());
        userModel.requestUserInfo(new SimpleResponseListener<User>() {
            @Override
            public void requestSuccess(User info, Response response) {

            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }
        });
        setBackActivity("认证");
        initData();
    }

    private void initData(){
        user=userModel.getLoginUser();
        setCommitMessage();
        if ("94".equals(user.isAudit)){
            tv_status.setText("审核中");
            setNoCommit();
        }else if ("95".equals(user.isAudit)){
            tv_status.setText("认证失败");
        }else if ("96".equals(user.isAudit)){
            tv_status.setText("认证成功");
            setNoCommit();
            tv_status.setTextColor(getResources().getColor(R.color.hintText));
        }
    }

    public void setNoCommit(){
        if (CommonUtil.notNull(menuItem))
            menuItem.setEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

        }
        return super.onOptionsItemSelected(item);
    }


}
