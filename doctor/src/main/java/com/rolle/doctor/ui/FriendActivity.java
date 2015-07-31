package com.rolle.doctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.common.adapter.RecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
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
import com.rolle.doctor.service.RequestService;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.RequestApi;
import com.rolle.doctor.util.ShareUtils;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.ContactQueryHandler;
import com.rolle.doctor.viewmodel.RequestTag;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;
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
 *
 * 新朋友
 */
public class FriendActivity extends BaseActivity{
     @InjectView(R.id.rv_view) RecyclerView lvView;

    @InjectView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private List<User> data;
    private RecyclerAdapter<User> adapater;
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
        adapater=new RecyclerAdapter(this,data,lvView){
            @Override
            public int getItemType(int position) {
                if (position==0){
                    return 22;
                }
                return  super.getItemType(position);
            }
        };

        adapater.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<User>() {
            @Override
            public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, final User item, final int position) {
                if (position==0){
                    viewHolder.setOnClickListener(R.id.tv_code, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (CommonUtil.isFastClick())return;
                            ViewUtil.openActivity(SeachUserActivity.class, getContext());
                        }
                    });
                    viewHolder.setOnClickListener(R.id.ll_item_0, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onWeixin();
                        }
                    });
                    viewHolder.setOnClickListener(R.id.ll_item_1, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onQQ();
                        }
                    });
                    viewHolder.setOnClickListener(R.id.ll_item_2, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onFriendList();
                        }
                    });
                    return;
                }
                StringBuilder builder = new StringBuilder();
                builder.append(item.age == null ? "0" : item.age);
                builder.append("岁");
                viewHolder.setText(R.id.tv_item_0, item.nickname);
                TextView textSex = viewHolder.getView(R.id.tv_item_2);
                textSex.setText(builder.toString());
                if (Util.isDoctor(item.typeId)) {
                    viewHolder.getView(R.id.iv_type).setVisibility(View.VISIBLE);
                }
                Picasso.with(getContext()).load(RequestApi.getImageUrl(item.headImage)).placeholder(R.mipmap.icon_default).
                        transform(new CircleTransform()).into((ImageView) viewHolder.getView(R.id.iv_photo));
                if ("0".equals(item.sex)) {
                    textSex.setBackgroundResource(R.drawable.round_bg_boy);
                    textSex.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.mipmap.icon_boy), null, null, null);
                } else {
                    textSex.setBackgroundResource(R.drawable.round_bg_girl);
                    textSex.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.mipmap.icon_girl), null, null, null);
                }
                Button btn_status = viewHolder.getView(R.id.btn_status);
                if (CommonUtil.notEmpty(item.statusId)) {
                    switch (item.statusId) {
                        case "76":
                            btn_status.setText("接受");
                            btn_status.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    doAddFriend(item.id + "", position);
                                }
                            });
                            break;
                        case "77":
                            btn_status.setText("已接受");
                            btn_status.setBackgroundResource(R.color.write);
                            break;

                    }
                }
            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                if (viewType==22)
                    return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_new_friend_head, viewGroup, false));
                return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_contact, viewGroup, false));
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });
        ViewUtil.initRecyclerViewDecoration(lvView, getContext(), adapater);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
        adapater.setOnClickEvent(new RecyclerAdapter.OnClickEvent<User>() {
            @Override
            public void onClick(View v, User item, int position) {
                if (position==0)return;
                if (CommonUtil.isFastClick()) return;
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstants.ITEM, item);
                if (AppConstants.USER_TYPE_PATIENT.equals(item.typeId)) {
                    ViewUtil.openActivity(PatientHActivity.class, bundle, getContext());
                } else {
                    ViewUtil.openActivity(DoctorDetialActivity.class, bundle, getContext());
                }
            }
        });
        requestData();

    }

    void doAddFriend(String userId,final int position){
        showLoading();
        userModel.requestAgreeFriend(userId, new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                // update
                msgShow("操作成功...");
                //position
                User user = data.get(position);
                user.statusId = "77";
                adapater.notifyItemUpdate(position);
                Intent intent = new Intent(getContext(), RequestService.class);
                Bundle bundle = new Bundle();
                bundle.putInt(RequestTag.TAG, RequestTag.R_USER_FRIEND);
                startService(intent);
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

    private void requestData(){
        refresh.setRefreshing(true);
        refresh.refreshDrawableState();
        userModel.requestNewFriend(new SimpleResponseListener<FriendResponse>() {
            @Override
            public void requestSuccess(FriendResponse info, Response response) {
                adapater.addItemAll(info.list);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }

            @Override
            public void requestView() {
               refresh.setRefreshing(false);
            }
        });
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

    //@OnClick(R.id.ll_item_0)
    void onWeixin(){
        if (CommonUtil.isFastClick())return;
        umSocialService.directShare(getContext(), SHARE_MEDIA.WEIXIN, listener);
    }

    //@OnClick(R.id.ll_item_1)
    void onQQ(){
        if (CommonUtil.isFastClick())return;
        umSocialService.directShare(getContext(), SHARE_MEDIA.QQ, listener);
    }
    ///@OnClick(R.id.ll_item_2)
    void onFriendList(){
        if (CommonUtil.isFastClick())return;
        ViewUtil.openActivity(FriendListActivity.class, FriendActivity.this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        adapater.onDestroyReceiver();
    }
}
