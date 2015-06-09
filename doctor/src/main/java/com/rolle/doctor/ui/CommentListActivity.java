package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.Recommended;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.util.TimeUtil;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 评论  留言
 */
public class CommentListActivity extends BaseActivity{

    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    @InjectView(R.id.rv_view)
    RecyclerView rv_view;
    private UserModel userModel;
    private BaseRecyclerAdapter<Recommended.Item> recyclerAdapter;
    private List<Recommended.Item> lsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycker);
        userModel=new UserModel(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("收到的评论");
        refresh.setRefreshStyle(Constants.PULL_STYLE);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
        refresh.setRefreshing(false);
        lsData=new ArrayList<>();
        recyclerAdapter=new BaseRecyclerAdapter<>(lsData);
        recyclerAdapter.implementRecyclerAdapterMethods(new BaseRecyclerAdapter.RecyclerAdapterMethods() {
            @Override
            public void onBindViewHolder(BaseRecyclerAdapter.ViewHolder viewHolder, int i) {
                Recommended.Item item=lsData.get(i);
                Picasso.with(getContext()).load(item.headImage).placeholder(R.drawable.icon_default).
                        transform(new CircleTransform()).into((ImageView) viewHolder.getView(R.id.iv_photo));
                viewHolder.setText(R.id.tv_name, item.nickname);
                viewHolder.setText(R.id.tv_content, item.content);
                viewHolder.setText(R.id.tv_date, TimeUtil.formatyMdHm(item.createTime));
            }

            @Override
            public BaseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new BaseRecyclerAdapter.ViewHolder(getLayoutInflater()
                        .inflate(R.layout.list_item_comment, viewGroup, false));
            }

            @Override
            public int getItemCount() {
                return lsData.size();
            }
        });
        ViewUtil.initRecyclerView(rv_view, getContext(), recyclerAdapter);
        requestData();
    }

    public void requestData(){
        userModel.requestMessageList(new SimpleResponseListener<List<Recommended.Item>>() {
            @Override
            public void requestSuccess(List<Recommended.Item> info, Response response) {
                recyclerAdapter.addItemAll(info);
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

}
