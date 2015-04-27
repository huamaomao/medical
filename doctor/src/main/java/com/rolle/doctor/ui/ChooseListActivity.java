package com.rolle.doctor.ui;

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
import com.rolle.doctor.presenter.ChooseListPresenter;
import com.rolle.doctor.presenter.LoginPresenter;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Hua_ on 2015/3/27.
 */
public class ChooseListActivity extends BaseActivity implements ChooseListPresenter.IChooseView{

    @InjectView(R.id.rv_view)RecyclerView rv_view;

    private ChooseListPresenter presenter;
    private BaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_choose);
        presenter=new ChooseListPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("");
        final List<Map.Entry<String,String>> data=new ArrayList<>();
        data.add(new AbstractMap.SimpleEntry("1","2"));
        data.add(new AbstractMap.SimpleEntry("1","2"));
        data.add(new AbstractMap.SimpleEntry("1","2"));
        data.add(new AbstractMap.SimpleEntry("1","2"));
        adapter=new BaseRecyclerAdapter(data);
        adapter.implementRecyclerAdapterMethods(new BaseRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(BaseRecyclerAdapter.ViewHolder viewHolder, int i) {
               viewHolder.setText(R.id.tv_name,data.get(i).getValue());
            }

            @Override
            public BaseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                final  BaseRecyclerAdapter.ViewHolder holder=new BaseRecyclerAdapter.ViewHolder(getLayoutInflater().inflate(R.layout.item_list_spinner,viewGroup,false)){};
                return holder;
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });
        adapter.setOnClickEvent(new BaseRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        ViewUtil.initRecyclerView(rv_view,this,adapter);
    }




}
