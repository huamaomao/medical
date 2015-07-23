package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.domain.ContactBean;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.Recommended;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.ShareUtils;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.ContactQueryHandler;
import com.rolle.doctor.viewmodel.UserModel;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 * 新朋友
 */
public class FriendActivity extends BaseActivity{
     @InjectView(R.id.rv_view) RecyclerView lvView;
    private List<User> data;
    private FriendListAdapater adapater;
    private UserModel userModel;

    private UMSocialService umSocialService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        userModel=new UserModel(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("新朋友");
        umSocialService= ShareUtils.initInviteShare(getContext());
        data=new ArrayList<>();
        adapater=new FriendListAdapater(this,data,lvView,FriendListAdapater.TYPE_FRIEND);

    }

    private SocializeListeners.SnsPostListener listener=new SocializeListeners.SnsPostListener() {
        @Override
        public void onStart() {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int eCode, SocializeEntity socializeEntity) {
            String showText = "邀请成功";
            if (eCode != StatusCode.ST_CODE_SUCCESSED) {
                showText = "邀请失败 [" + eCode + "]";
            }
            msgShow(showText);
        }
    };

    @OnClick(R.id.ll_item_0)
    void onWeixin(){
        if (CommonUtil.isFastClick())return;
        umSocialService.directShare(getContext(), SHARE_MEDIA.WEIXIN, listener);
    }

    @OnClick(R.id.ll_item_1)
    void onQQ(){
        if (CommonUtil.isFastClick())return;
        umSocialService.directShare(getContext(), SHARE_MEDIA.QQ, listener);
    }
    @OnClick(R.id.ll_item_2)
    void onFriendList(){
        if (CommonUtil.isFastClick())return;
        ViewUtil.openActivity(FriendListActivity.class, FriendActivity.this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            adapater.onDestroyReceiver();
        }catch (Exception e){}
    }
}
