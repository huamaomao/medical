package com.rolle.doctor.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.common.adapter.BaseRecyclerAdapter;
import com.android.common.adapter.MessageRecyclerAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewUtil;
import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.baoyz.widget.PullRefreshLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.PublicViewAdapter;
import com.rolle.doctor.adapter.PublicViewHolder;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.RecommendedInfo;
import com.rolle.doctor.domain.RecommendedItemInfo;
import com.rolle.doctor.util.CircleTransform;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.viewmodel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.InjectView;

/**
 * 收到的推荐   赞   推荐
 */
public class RecommendedActivity extends BaseActivity{

    private PublicViewAdapter<RecommendedItemInfo> adapter;
    @InjectView(R.id.refresh)
    PullRefreshLayout refresh;
    @InjectView(R.id.rv_view)
    RecyclerView rv_view;
    private UserModel userModel;
    private BaseRecyclerAdapter<FriendResponse> recyclerAdapter;
    private List<FriendResponse> lsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycker);
        userModel=new UserModel(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("收到的赞");
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
                /*  Picasso.with(getContext()).load(messageUser.headImage).placeholder(R.drawable.icon_default).
                        transform(new CircleTransform()).into((ImageView) viewHolder.getView(R.id.iv_photo));*/
            }

            @Override
            public BaseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new BaseRecyclerAdapter.ViewHolder(getLayoutInflater()
                        .inflate(R.layout.list_item_recommended_itm, viewGroup, false));
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
        userModel.requestPraiseList(new ViewModel.ModelListener<ResponseMessage>() {
            @Override
            public void model(Response response, ResponseMessage responseMessage) {

            }

            @Override
            public void errorModel(ModelEnum modelEnum) {

            }

            @Override
            public void view() {
                refresh.setRefreshing(false);
            }
        });
       /* model.requestPraise(new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                RecommendedInfo recommendedInfo = res.getObject(RecommendedInfo.class);
                if (CommonUtil.notNull(recommendedInfo)){
                    switch (recommendedInfo.statusCode){
                        case "200":
                            mDatas.addAll(recommendedInfo.getList());
                            adapter.notifyDataSetChanged();
                            break;
                        case "300":
                            break;
                    }
                }
            }

            @Override
            protected void onFailure(HttpException e, Response res) {

            }
        });*/
    }

}
