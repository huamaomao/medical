package com.rolle.doctor.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.android.common.adapter.RecyclerItemClickListener;
import com.android.common.domain.Version;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.SettingAdapater;
import com.rolle.doctor.domain.ItemInfo;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 设置
 */
public class AboutUsActivity extends BaseActivity{


    @InjectView(R.id.tv_item_4)
    TextView tv_item_4;
    @InjectView(R.id.tv_content)
    TextView tv_content;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

    }
    @OnClick(R.id.rl_item_1)
    void doWeixin(){
        if (CommonUtil.isFastClick())return;
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.tencent.mm");//直接打开微信
        intent.putExtra(Intent.EXTRA_SUBJECT, "share");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //weixin://contacts/profile/gkyCmqDEd-0IreT59xmQ
        //http://weixin.qq.com/r/gkyCmqDEd-0IreT59xmQ
        intent.setData(Uri.parse("http://weixin.qq.com/r/gkyCmqDEd-0IreT59xmQ"));
        startActivity(intent);*/
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("关于我们");
        userModel=new UserModel(getContext());
        Version version=userModel.getVersion();
        if (CommonUtil.notNull(version)){
            tv_content.setText(version.versionsPeculiarity);
        }else
         tv_content.setText("暂无信息");
        //tv_item_4.setText(Html.fromHtml("<a href='weixin://contacts/profile/gkyCmqDEd-0IreT59xmQ'>点击关注我们公众账号</a>"));

    }
}
