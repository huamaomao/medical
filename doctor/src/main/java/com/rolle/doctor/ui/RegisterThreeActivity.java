package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.DoctorSpinnerAdpater;
import com.rolle.doctor.adapter.YearSpinnerAdpater;
import java.util.ArrayList;
import java.util.List;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class RegisterThreeActivity extends BaseActivity{

    @InjectView(R.id.sp_doctor)Spinner spDoctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_three);

    }


    @Override
    protected void initView() {
        super.initView();
        List<String> startData=new ArrayList<String>();
        startData.add("医生");
        startData.add("营养师");
        DoctorSpinnerAdpater adapter = new DoctorSpinnerAdpater(this,R.layout.sp_check_doctor,R.id.tv_item_0,startData);
        spDoctor.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.toolbar_register:
                finish();
                ViewUtil.openActivity(MainActivity.class,this);
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
