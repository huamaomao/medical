package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;

import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class UpdatePhotoActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_photo);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("修改认证资料");

    }
    @OnClick(R.id.iv_send)
    void toMessageActivity(){
        ViewUtil.openActivity(MessageActivity.class,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_set:
                ViewUtil.openActivity(NoteActivity.class,this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
