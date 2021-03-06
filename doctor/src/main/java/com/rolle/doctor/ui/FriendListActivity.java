package com.rolle.doctor.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.RecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.DividerItemDecoration;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.domain.ContactBean;
import com.rolle.doctor.domain.ContactListResponse;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

import static com.rolle.doctor.R.mipmap;

/**
 * Created by Hua_ on 2015/3/27.
 * 新朋友   通讯录
 */
public class FriendListActivity extends BaseLoadingActivity{
    @InjectView(R.id.rv_view) RecyclerView lvView;
    @InjectView(R.id.refresh) PullRefreshLayout refresh;

    private List<User> data;
    private RecyclerAdapter<User> adapater;
    private UserModel userModel;
    private ContactQueryHandler handler;
    private List<ContactBean> contactBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycker);
        userModel=new UserModel(this);
        handler=new ContactQueryHandler(getContentResolver(), new ContactQueryHandler.HandleListener() {
                @Override
                public void setAdapter(List<ContactBean> list) {
                    contactBeans=list;
                    requestData();
                }
        });
    }

    /****
     *
     */
    private void requestData(){
        userModel.requestNewFriendList(contactBeans, new SimpleResponseListener<List<User>>() {
            @Override
            public void requestSuccess(List<User> info, Response response) {
                adapater.addItemAll(info);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }

            @Override
            public void requestView() {
                refresh.setRefreshing(false);
                adapater.checkEmpty();
            }
        });

    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("通讯录好友");
        loadingFragment.setAddMessage();
        data=new ArrayList<>();
        adapater=new RecyclerAdapter(getContext(),data,lvView);
        ViewUtil.initRecyclerViewDecoration(lvView, getContext(), adapater);
        adapater.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<User>() {
            @Override
            public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, final User item, final int position) {
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
                            btn_status.setText("添加");
                            btn_status.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    doAddFriend(item.id+"", position);
                                }
                            });
                            break;
                        case "77":
                            btn_status.setText("已添加");
                            btn_status.setBackgroundResource(R.color.write);
                            break;
                        case "145":
                            btn_status.setText("邀请");
                            btn_status.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ShareUtils.smsContent(getContext(), item.mobile);
                                }
                            });
                            break;
                    }
                }

            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_contact, viewGroup, false));
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });
        adapater.setOnClickEvent(new RecyclerAdapter.OnClickEvent<User>() {
            @Override
            public void onClick(View v, User item, int position) {
                if (CommonUtil.isFastClick()) return;
                if ("145".equals(item.statusId))return;
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstants.ITEM, item);
                if (AppConstants.USER_TYPE_PATIENT.equals(item.typeId)) {
                    ViewUtil.openActivity(PatientHActivity.class, bundle, getContext());
                } else {
                    ViewUtil.openActivity(DoctorDetialActivity.class, bundle, getContext());
                }
            }
        });


        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.queryList();
            }
        });
        refresh.setRefreshing(true);
        handler.queryList();
    }


    void doAddFriend(String userId,final int position){
        showLoading();
        userModel.requestAddFriendID(userId, null, new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                // update
                msgShow("添加成功...");
                //position
                User user=data.get(position);
                user.statusId="77";
                adapater.notifyItemUpdate(position);
                Intent intent=new Intent(getContext(),RequestService.class);
                Bundle bundle=new Bundle();
                bundle.putInt(RequestTag.TAG, RequestTag.R_USER_FRIEND);
                startService(intent);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                new AppHttpExceptionHandler().via(getContext()).handleException(e,info);
            }

            @Override
            public void requestView() {
               hideLoading();
            }
        });
   }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapater.onDestroyReceiver();
    }
}
