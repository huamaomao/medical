package com.roller.medicine.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;

import com.android.common.domain.ResponseMessage;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.widget.AlertDialog;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.viewmodel.DataModel;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author Hua_
 * @Description:
 * @date 2015/5/7 - 18:56
 */
public class SexDialogFragment extends DialogFragment {

    @InjectView(R.id.tv_item_0)
    CheckedTextView tv_boy;
    @InjectView(R.id.tv_item_1)
    CheckedTextView tv_girl;

    private UserInfo userInfo;
    private DataModel dataModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        dataModel=new DataModel();
        userInfo=dataModel.getLoginUser();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle("性别");
        Toolbar title=(Toolbar)LayoutInflater.from(getActivity()).inflate(R.layout.toolbar,null);
        title.setTitle("性别");
        builder.setCustomTitle(title);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_sex, null);
        ButterKnife.inject(this,view);
        checkSex();
        return builder.setView(view).create();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.tv_item_0,R.id.tv_item_1})
    void doSelection(View view){
        if (view.getId()==R.id.tv_item_0){
            userInfo.sex="0";
        }else {
            userInfo.sex="1";
        }
        checkSex();
        dismiss();
    }

    private void checkSex(){
        if (AppConstants.SEX_GIRL.equals(userInfo.sex)){
            tv_boy.setChecked(false);
            tv_girl.setChecked(true);
        }else {
            tv_boy.setChecked(true);
            tv_girl.setChecked(false);
        }
        dataModel.saveUser(userInfo);
        dataModel.saveDoctor(userInfo, new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {

            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }
        });
    }
}
