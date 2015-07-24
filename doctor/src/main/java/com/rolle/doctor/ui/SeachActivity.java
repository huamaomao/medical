package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.adapter.RecyclerAdapter;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.FriendListAdapater;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

import static com.rolle.doctor.R.mipmap;
import static com.rolle.doctor.R.mipmap.icon_default;

/**
 * 查询
 */
public class SeachActivity extends BaseLoadingActivity {

    @InjectView(R.id.rv_view)
    RecyclerView lvView;
    private List<User> data;

    private RecyclerAdapter<User> adapater;
    @InjectView(R.id.seach)
    SearchView seachView;
    private UserModel userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("");
        userModel=new UserModel(getContext());
        data=new ArrayList<User>();
        adapater=new RecyclerAdapter(getContext(),data,lvView);
        adapater.empty="查不到用户...";
        adapater.implementRecyclerAdapterMethods(new RecyclerAdapter.RecyclerAdapterMethods<User>() {
            @Override
            public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, User item, int position) {
                Picasso.with(getContext()).load(item.headImage).placeholder(icon_default).
                        transform(new CircleTransform()).into((ImageView) viewHolder.getView(R.id.iv_photo));
                viewHolder.setText(R.id.tv_item_1,item.nickname).setText(R.id.tv_item_0,item.noteName);
            }

            @Override
            public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                return new RecyclerAdapter.ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_seach_friend,viewGroup,false));
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });

        ViewUtil.initRecyclerViewDecoration(lvView, getContext(), adapater);
        lvView.setVisibility(View.GONE);
        seachView.setIconified(false);
        seachView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        seachView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s != null && s.length() > 0) {
                    lvView.setVisibility(View.VISIBLE);
                    adapater.addItemAll(userModel.seachFriendList(s));
                    adapater.checkEmpty();
                }
                return true;
            }
        });
        adapater.setOnClickEvent(new RecyclerAdapter.OnClickEvent<User>() {
            @Override
            public void onClick(View v, User user, int position) {
                if (CommonUtil.isFastClick())return;
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstants.ITEM, user);
                if (AppConstants.USER_TYPE_PATIENT.equals(user.typeId)){
                    ViewUtil.openActivity(PatientHActivity.class, bundle,getContext(), ActivityModel.ACTIVITY_MODEL_1);
                }else {
                    ViewUtil.openActivity(DoctorDetialActivity.class, bundle,getContext(), ActivityModel.ACTIVITY_MODEL_1);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapater.onDestroyReceiver();
    }
}