package com.rolle.doctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.common.adapter.RecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.AppHttpExceptionHandler;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.service.RequestService;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.ShareUtils;
import com.rolle.doctor.util.Util;
import com.rolle.doctor.viewmodel.RequestTag;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import butterknife.InjectView;
import butterknife.OnClick;
import static com.rolle.doctor.R.mipmap.icon_default;

/**
 * 查询
 */
public class SeachUserActivity extends BaseLoadingActivity {

    @InjectView(R.id.rv_view)
    RecyclerView lvView;
    @InjectView(R.id.et_seach)
    EditText et_seach;

    private List<User> data;
    private RecyclerAdapter<User> adapater;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_user);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("");
        userModel=new UserModel(getContext());
        data=new ArrayList();
        adapater=new RecyclerAdapter(getContext(),data,lvView);
        adapater.empty="查不到用户...";
        loadingFragment.setRequestMessage();
        adapater.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<User>() {
            @Override
            public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, final User item, final int position) {
                Picasso.with(getContext()).load(item.headImage).placeholder(icon_default).
                        transform(new CircleTransform()).into((ImageView) viewHolder.getView(R.id.iv_photo));
                viewHolder.setText(R.id.tv_item_0, item.nickname);
                StringBuilder builder = new StringBuilder();
                builder.append(item.age == null ? "0" : item.age);
                builder.append("岁");
                TextView tvSex=viewHolder.getView(R.id.tv_item_2);
                tvSex.setText(builder.toString());
                if ("0".equals(item.sex)) {
                    tvSex.setBackgroundResource(R.drawable.round_bg_boy);
                    tvSex.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.mipmap.icon_boy), null, null, null);
                } else {
                    tvSex.setBackgroundResource(R.drawable.round_bg_girl);
                    tvSex.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.mipmap.icon_girl), null, null, null);
                }
                if (Util.isDoctor(item.typeId)){
                    viewHolder.getView(R.id.iv_type).setVisibility(View.VISIBLE);
                    StringBuilder builder1=new StringBuilder("擅长:");
                    if (CommonUtil.notNull(item.doctorDetail)){
                        builder1.append(CommonUtil.initTextNull(item.doctorDetail.speciality));
                    }else {
                        builder1.append("无");
                    }
                    viewHolder.setText(R.id.tv_item_1,builder1.toString());
                }else{
                    viewHolder.setText(R.id.tv_item_1,CommonUtil.isEmpty(item.intro)?"无":item.intro);
                }

               Button btn_status= viewHolder.getView(R.id.btn_status);
                if (CommonUtil.notEmpty(item.statusId)) {
                    switch (item.statusId) {
                        case "76":
                            btn_status.setText("添加");
                            btn_status.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    doAdd(item.id + "", position);
                                }
                            });
                            break;
                        case "77":
                            btn_status.setText("已添加");
                            btn_status.setBackgroundResource(R.color.write);
                            break;

                    }
                }

            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_user_add, viewGroup, false));
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
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstants.ITEM, item);
                if (AppConstants.USER_TYPE_PATIENT.equals(item.typeId)) {
                    ViewUtil.openActivity(PatientHActivity.class, bundle, getContext());
                } else {
                    ViewUtil.openActivity(DoctorDetialActivity.class, bundle, getContext());
                }
            }
        });

        ViewUtil.initRecyclerViewDecoration(lvView, getContext(), adapater);

    }
    @OnClick(R.id.btn_next)
    void doSeach(){
        String txt=et_seach.getText().toString();
        if (CommonUtil.isEmpty(txt))return;
            userModel.requestSeachUser(txt, new SimpleResponseListener<FriendResponse>() {
                @Override
                public void requestSuccess(FriendResponse info, Response response) {
                    adapater.addItemAll(info.list);
                    adapater.empty = "查不到用户...";
                }

                @Override
                public void requestError(HttpException e, ResponseMessage info) {
                    adapater.empty = "网络异常...";
                }

                @Override
                public void requestView() {
                    adapater.checkEmpty();
                }
            });
    }

    public void doAdd(String userId,final int position){
        showLoading();
        userModel.requestAddFriendID(userId, null, new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
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
                new AppHttpExceptionHandler().via(getContext()).handleException(e, info);
            }

            @Override
            public void requestView() {
                hideLoading();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapater.onDestroyReceiver();
    }
}