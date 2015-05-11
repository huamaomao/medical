package com.rolle.doctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.util.ViewUtil;
import com.android.common.widget.InputMethodLinearLayout;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.CityResponse;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.presenter.ChooseListPresenter;
import com.rolle.doctor.presenter.LoginPresenter;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class ChooseListActivity extends BaseActivity{

    @InjectView(R.id.rv_view)RecyclerView rv_view;

    private BaseRecyclerAdapter adapter;
    private ArrayList<CityResponse.Item> items;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_choose);
        items=new ArrayList<>();
        userModel=new UserModel(getContext());
    }

    @Override
    protected void initView() {
        super.initView();
        final int type=getIntent().getIntExtra(Constants.TYPE,0);
        items=getIntent().getParcelableArrayListExtra(Constants.LIST);
        String title=null;
        switch (type){
            case 0:
                title="选择省份";
                break;
            case 1:
                title="选择城市";
                break;
            case 2:
                title="选择科室";
                break;
            case 3:
                title="选择职称";
                break;
        }
        setBackActivity(title);
        adapter=new BaseRecyclerAdapter(items);
        adapter.implementRecyclerAdapterMethods(new BaseRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(BaseRecyclerAdapter.ViewHolder viewHolder, int i) {
               viewHolder.setText(R.id.tv_name,items.get(i).name);
            }

            @Override
            public BaseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                final  BaseRecyclerAdapter.ViewHolder holder=new BaseRecyclerAdapter.ViewHolder(getLayoutInflater().inflate(R.layout.item_list_spinner,viewGroup,false)){};
                return holder;
            }

            @Override
            public int getItemCount() {
                return items.size();
            }
        });
        adapter.setOnClickEvent(new BaseRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent();
                intent.putExtra(Constants.TYPE, type);
                intent.putExtra(Constants.POSITION, position);
                setResult(200, intent);
                User user=userModel.getLoginUser();
                switch (type){
                    case 1:
                        user.regionId=items.get(position).id;
                        break;
                    case 2:
                        user.departmentId=items.get(position).id;
                        user.department=items.get(position).name;
                        user.setUpdateStatus();
                        break;
                    case 3:
                        user.jobId=items.get(position).id;
                        user.doctorTitle=items.get(position).name;
                        user.setUpdateStatus();
                        break;
                }
                userModel.saveUser(user);
                finish();
            }
        });
        ViewUtil.initRecyclerView(rv_view,this,adapter);
    }

    @Override
    protected void onBackActivty() {
        super.onBackActivty();
    }
}
