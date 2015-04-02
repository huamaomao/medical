package com.rolle.doctor.ui;

import android.os.Bundle;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class MessageActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("小叶");
    }
    @OnClick(R.id.toolbar_patient)
    void toPatient(){
        ViewUtil.openActivity(PatientHActivity.class,this);
    }

}
