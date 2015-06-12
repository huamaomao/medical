package com.roller.medicine.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.adapter.QuickAdapter;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.ViewHolderHelp;
import com.android.common.util.ViewUtil;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.roller.medicine.R;
import com.roller.medicine.info.HomeAdviceInfo;
import com.roller.medicine.info.HomeInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.ui.MessageActivity;
import com.roller.medicine.utils.CircleTransform;
import com.roller.medicine.utils.Constants;
import com.roller.medicine.viewmodel.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Hua on 2015/4/3.
 */
public class TabHomeAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static  final int TYPE_HEAD=0;
    static  final int TYPE_RECOMMEND=1;
    static  final int TYPE_HEALTH=2;
    static  final int TYPE_ADD=3;
    private Context mContext;
    List<Object> data;
    private HomeInfo homeInfo=null;
    public void setHomeInfo(HomeInfo homeInfo){
        this.homeInfo=homeInfo;
        notifyDataSetChanged();
    }

    public TabHomeAdapater(Context mContext, List<Object> data) {
        this.mContext = mContext;
        this.data = data;
    }

    public HomeInfo getHomeInfo() {
        return homeInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_HEAD)
            return new HeadViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_head,parent,false));
        else
            return new RecommedViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_recommed,parent,false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  HeadViewHolder){
            HeadViewHolder headViewHolder=(HeadViewHolder)holder;
           //homeInfo.familyList
            if (homeInfo.familyList.size()<2){
                headViewHolder.sp_start.setVisibility(View.GONE);
            }
            YearSpinnerAdpater adpater=new YearSpinnerAdpater(mContext,R.layout.sp_check_text,homeInfo.familyList);
            headViewHolder.sp_start.setAdapter(adpater);
            headViewHolder.lineChart.setDescription("");
            headViewHolder.lineChart.setNoDataTextDescription("暂无数据");
            LimitLine ll1 = new LimitLine(11.0f, "高血压 严重");
            ll1.setLineWidth(1f);
            ll1.enableDashedLine(3f, 3f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
            ll1.setTextSize(10f);

            LimitLine ll2 = new LimitLine(5.0f, "低血压 严重");
            ll2.setLineWidth(1f);
            ll2.enableDashedLine(3f, 3f, 0f);
            ll2.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
            ll2.setTextSize(10f);
            YAxis leftAxis = headViewHolder.lineChart.getAxisLeft();
            leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
            leftAxis.addLimitLine(ll1);
            leftAxis.addLimitLine(ll2);
            leftAxis.setAxisMaxValue(30f);
            leftAxis.setAxisMinValue(0f);
            leftAxis.setStartAtZero(false);
            leftAxis.enableGridDashedLine(10f, 10f, 0f);

            // limit lines are drawn behind data (and not on top)
            leftAxis.setDrawLimitLinesBehindData(true);
            headViewHolder.lineChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
//        mChart.invalidate();

            // get the legend (only possible after setting data)
            Legend l =  headViewHolder.lineChart.getLegend();
            // modify the legend ...
            // l.setPosition(LegendPosition.LEFT_OF_CHART);
            l.setForm(Legend.LegendForm.LINE);

            headViewHolder.lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            headViewHolder.lineChart.getAxisRight().setEnabled(false);
            ArrayList<String> xVals = new ArrayList<>();
            xVals.add("");
            xVals.add("凌晨");
            xVals.add("早餐前");
            xVals.add("早餐后");
            xVals.add("午餐前");
            xVals.add("午餐后");
            xVals.add("晚餐前");
            xVals.add("晚餐后");
            xVals.add("睡前");
            xVals.add("24:00");
            ArrayList<Entry> yVals = new ArrayList<>();
            HomeInfo.Glycemic glycemic=homeInfo.glycemic;
            if (CommonUtil.notNull(glycemic.morningNum))
                yVals.add(new Entry(glycemic.morningNum,1));
            if (CommonUtil.notNull(glycemic.breakfastStart))
                yVals.add(new Entry(glycemic.breakfastStart,2));
            if (CommonUtil.notNull(glycemic.breakfastEnd))
                yVals.add(new Entry(glycemic.breakfastEnd,3));
            if (CommonUtil.notNull(glycemic.chineseFoodStart))
                yVals.add(new Entry(glycemic.chineseFoodStart,4));
            if (CommonUtil.notNull(glycemic.chineseFoodEnd))
                yVals.add(new Entry(glycemic.chineseFoodEnd,5));
            if (CommonUtil.notNull(glycemic.dinnerStart))
                yVals.add(new Entry(glycemic.dinnerStart,6));
            if (CommonUtil.notNull(glycemic.dinnerEnd))
                yVals.add(new Entry(glycemic.dinnerEnd,7));
            if (CommonUtil.notNull(glycemic.beforeGoingToBed))
                yVals.add(new Entry(glycemic.beforeGoingToBed,8));
            LineDataSet set1 = new LineDataSet(yVals, "一天血糖值");
            set1.enableDashedLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleSize(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setFillAlpha(65);
            set1.setFillColor(Color.BLACK);
            ArrayList<LineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(xVals, dataSets);
            // set data

            headViewHolder.lineChart.setData(data);


        }else if (holder instanceof  RecommedViewHolder){
            final   RecommedViewHolder recommedViewHolder=(RecommedViewHolder)holder;
            recommedViewHolder.btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //资讯
                    UserInfo user=new UserInfo();
                    user.id=CommonUtil.parseInt(homeInfo.userId);
                    user.nickname=homeInfo.nickname;
                    user.headImage=homeInfo.headImage;
                    Bundle bundle=new Bundle();
                    bundle.putParcelable(Constants.ITEM,user);
                    ViewUtil.openActivity(MessageActivity.class, bundle, (Activity)mContext, ActivityModel.ACTIVITY_MODEL_1);
                }
            });
            recommedViewHolder.tv_name.setText(homeInfo.nickname);
            Picasso.with(mContext).load(DataModel.getImageUrl(homeInfo.headImage)).placeholder(R.drawable.icon_default).
                    transform(new CircleTransform()).into(recommedViewHolder.iv_photo);
            recommedViewHolder.tv_content.setText(homeInfo.advice.doctorAdvice);
            recommedViewHolder.tv_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recommedViewHolder.tv_comment.getText().toString().equals("全文")) {
                        recommedViewHolder.tv_content.setMaxLines(100);
                        recommedViewHolder.tv_comment.setText("收起");
                    } else {
                        recommedViewHolder.tv_content.setMaxLines(2);
                        recommedViewHolder.tv_comment.setText("全文");
                    }
                }
            });

            QuickAdapter<HomeAdviceInfo.Food> adapter=new QuickAdapter<HomeAdviceInfo.Food>(mContext,R.layout.list_item_grid_home) {
                @Override
                protected void convert(ViewHolderHelp viewHolderHelp, HomeAdviceInfo.Food food) {
                    viewHolderHelp.setText(R.id.tv_item_0,food.foodName);
                    Picasso.with(mContext).load(DataModel.getImageUrl(food.url)).placeholder(R.drawable.icon_default).
                           into((ImageView) viewHolderHelp.getView(R.id.iv_photo));
                }
            };
            recommedViewHolder.gv_view.setAdapter(adapter);
            if (CommonUtil.notNull(homeInfo.advice.foods)){
                adapter.addAll(homeInfo.advice.foods);
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (CommonUtil.isNull(homeInfo)){
            return TYPE_ADD;
        }
        if (position==0){
            return TYPE_HEAD;
        }
        if (position==getItemCount()-1)
            return TYPE_HEALTH;
        else
            return TYPE_RECOMMEND;
    }

    public   class HeadViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.sp_start)
        AppCompatSpinner sp_start;
        @InjectView(R.id.lineChart)
        LineChart lineChart;
        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            lineChart.setTouchEnabled(false);
            lineChart.setDragEnabled(false);
            lineChart.setScaleEnabled(false);
            lineChart.setPinchZoom(false);
            lineChart.setHighlightIndicatorEnabled(false);

        }
    }



    public   class RecommedViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.iv_photo)
        ImageView iv_photo;
        @InjectView(R.id.tv_name)
        TextView tv_name;
        @InjectView(R.id.tv_comment)
        TextView tv_comment;
        @InjectView(R.id.tv_content)
        TextView tv_content;

        @InjectView(R.id.btn_next)
        Button btn_next;
        @InjectView(R.id.gv_view)
        GridView gv_view;
        public RecommedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }

}
