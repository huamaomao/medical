package com.rolle.doctor.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.common.util.CommonUtil;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.rolle.doctor.R;
import com.rolle.doctor.adapter.PublicViewAdapter;
import com.rolle.doctor.adapter.PublicViewHolder;
import com.rolle.doctor.domain.RecommendedInfo;
import com.rolle.doctor.domain.RecommendedItemInfo;
import com.rolle.doctor.util.Constants;
import com.rolle.doctor.viewmodel.UserModel;

import java.util.LinkedList;

import butterknife.InjectView;

/**
 * Created by Dove on 2015/4/24.
 */
public class ComplaintsActivity extends BaseActivity implements PublicViewAdapter.ICommonGetView<RecommendedItemInfo>, PublicViewAdapter.ICommonOnClick {

    private PublicViewAdapter<RecommendedItemInfo> adapter;
    private LinkedList<RecommendedItemInfo> mDatas = new LinkedList<RecommendedItemInfo>();
    @InjectView(R.id.lv_list)
    ListView lv_list;
    private UserModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended);
        model=new UserModel(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setBackActivity("投诉");
        RecommendedItemInfo itemInfo = new RecommendedItemInfo();
        mDatas.add(itemInfo);
        itemInfo = new RecommendedItemInfo();
        mDatas.add(itemInfo);
        itemInfo = new RecommendedItemInfo();
        mDatas.add(itemInfo);
        itemInfo = new RecommendedItemInfo();
        mDatas.add(itemInfo);
        adapter = new PublicViewAdapter<RecommendedItemInfo>(this,mDatas,R.layout.list_item_recommended_itm,this,this, Constants.NO);
        lv_list.setAdapter(adapter);
        request();
    }

    public void request(){

        model.requestRecommendReviewComplaints("23",new HttpModelHandler<String>() {
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
        });
    }

    @Override
    public void commonGetView(PublicViewHolder helper, RecommendedItemInfo item, View.OnClickListener onClick, int position, Object tag) {

    }

    @Override
    public void commonOnClick(View v) {

    }
}
