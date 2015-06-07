package com.roller.medicine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.common.adapter.QuickAdapter;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.viewmodel.SimpleResponseListener;
import com.baoyz.widget.PullRefreshLayout;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.response.Response;
import com.roller.medicine.R;
import com.roller.medicine.info.BloodInfo;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.utils.TimeUtil;
import com.roller.medicine.utils.Util;
import com.roller.medicine.viewmodel.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class PatientHListAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private  List<BloodInfo.Item> data;
    final  static int TYPE_0=0;
    final  static int TYPE_1=1;
    final  static int TYPE_2=2;
    private DataModel model;
    private UserInfo user;
    private String selectionItem;
    private QuickAdapter<BloodInfo.Item> quickAdapter;
    private BloodInfo bloodResponse;
    private  List<String> list;
    private PullRefreshLayout refresh;

    public PatientHListAdapater(Context mContext, List<BloodInfo.Item> data,PullRefreshLayout refresh) {
        this.refresh=refresh;
        this.mContext = mContext;
        this.data = data;
        model=new DataModel();
        this.user =model.getLoginUser();
        bloodResponse=new BloodInfo();
        list=new ArrayList<>();
        list.addAll(Util.getPlaintList(user.createTime));
        selectionItem=list.get(0);
        requestData(selectionItem);
        refresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(selectionItem);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       switch (viewType){
           case TYPE_0:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_patient_head,parent,false)){};
           case TYPE_1:
               return new RecyclerView.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_patient_table,parent,false)){};
           default:
               return new RecyclerView.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list,parent,false)){};
       }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder ,int position) {
        if (position==0){
            final  ViewHolder holder=(ViewHolder)viewHolder;
            final List<String> startData=new ArrayList<>();
            startData.addAll(Util.getPlaintList(user.createTime));
            YearSpinnerAdpater adpater=new YearSpinnerAdpater(mContext,R.layout.sp_check_write_text,startData.toArray());
            holder.spStart.setAdapter(adpater);
            holder.spStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectionItem = holder.spStart.getSelectedItem().toString();
                    initBloodList(selectionItem);
                    requestData(selectionItem);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            initPieChart(holder.pieChart);
            if (CommonUtil.notNull(holder.spStart.getSelectedItem()))
                 selectionItem=holder.spStart.getSelectedItem().toString();

        }else if (position!=1){
                    BloodInfo.Item item=data.get(position);
                    setText(viewHolder.itemView, R.id.tv_item_0, item.day);
                    setText(viewHolder.itemView, R.id.tv_item_1, CommonUtil.initTextBlood(item.morningNum));
                    setText(viewHolder.itemView,R.id.tv_item_2, CommonUtil.initTextBlood(item.breakfastStart));
                    setText(viewHolder.itemView,R.id.tv_item_3, CommonUtil.initTextBlood(item.breakfastEnd));
                    setText(viewHolder.itemView,R.id.tv_item_4, CommonUtil.initTextBlood(item.chineseFoodStart));
                    setText(viewHolder.itemView,R.id.tv_item_5, CommonUtil.initTextBlood(item.chineseFoodEnd));
                    setText(viewHolder.itemView,R.id.tv_item_6, CommonUtil.initTextBlood(item.dinnerStart));
                    setText(viewHolder.itemView,R.id.tv_item_7, CommonUtil.initTextBlood(item.dinnerEnd));
                    setText(viewHolder.itemView,R.id.tv_item_8, CommonUtil.initTextBlood(item.beforeGoingToBed));
        }

    }

    public void setText(View view,int res,String str){
        ((TextView)view.findViewById(res)).setText(str);
    }


    private void requestData(String date){
            initBloodList(date);
            model.requestBloodList(date, new SimpleResponseListener<BloodInfo>() {
                @Override
                public void requestSuccess(BloodInfo info, Response response) {
                    bloodResponse=info;
                    notifyItemChanged(1);
                    changeBloodListData(info.list);
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



    private void changeBloodListData(List<BloodInfo.Item> list){
        if (CommonUtil.notNull(list)){
            for (BloodInfo.Item item1:list){
                int m = TimeUtil.getMonthOfDay(item1.createTime);
                item1.day=m+"";
                data.set(m+2,item1);
                notifyItemChanged(m+2);
            }
        }
    }

    private void initBloodList(String date){
        if (CommonUtil.notEmpty(date)){
           try {
               String[] str=date.split("-");
                   int year=Integer.parseInt(str[0]);
                   int month=Integer.parseInt(str[1]);
                   int daySum=0;
                   switch (month){
                           case 2:
                               if (year%4==0) {
                                    daySum=29;
                               }else {
                                   daySum=28;
                               }
                               break;
                           case 1:
                           case 3:
                           case 5:
                           case 7:
                           case 8:
                           case 10:
                           case 12:
                               daySum=31;
                               break;
                            default:
                                   daySum=30;
                                   break;
                       }
               BloodInfo.Item item=null;
                    data.clear();
                    data.add(new BloodInfo.Item());
               data.add(new BloodInfo.Item());
                    for (int i=1;i<=daySum;i++){
                        item=new BloodInfo.Item();
                        item.day=i+"";
                        data.add(i + 1, item);
                    }

           }catch (Exception e){e.printStackTrace();}
        }
    }


    private void initPieChart(PieChart pieChart) {

        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);

        //pieChart.setOnChartValueSelectedListener(this);

        pieChart.setCenterText("血糖数");
        pieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        yVals1.add(new Entry(CommonUtil.parseInt(bloodResponse.normalSum), 0));
        yVals1.add(new Entry(CommonUtil.parseInt(bloodResponse.highSum), 1));
        yVals1.add(new Entry(CommonUtil.parseInt(bloodResponse.lowSum), 2));
        ArrayList<String> xVals = new ArrayList<String>();
        StringBuilder builder=new StringBuilder();
        builder.append("正常").append(" ").append(CommonUtil.parseInt(bloodResponse.normalSum)).append("次");
        xVals.add(builder.toString());
        builder=new StringBuilder();
        builder.append("偏高").append(" ").append(CommonUtil.parseInt(bloodResponse.highSum)).append("次");
        xVals.add(builder.toString());
        builder=new StringBuilder();
        builder.append("偏低").append(" ").append(CommonUtil.parseInt(bloodResponse.lowSum)).append("次");
        xVals.add(builder.toString());
        PieDataSet dataSet = new PieDataSet(yVals1, "血糖数");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList();
       // colors.add(ColorTemplate.getHoloBlue());
        colors.add( Color.parseColor("#c1fa15"));
        colors.add(Color.parseColor("#fe5f00"));
        colors.add(Color.parseColor("#8ac5f7"));
        dataSet.setColors(colors);
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
        pieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
            if (position==0)
                return TYPE_0;
             else  if (position==1)
                return TYPE_1;
            else
                return TYPE_2;


    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        Spinner spStart;
        PieChart pieChart;
        public ViewHolder(View itemView) {
            super(itemView);
            spStart=(Spinner)itemView.findViewById(R.id.sp_start);
            pieChart=(PieChart)itemView.findViewById(R.id.pieChart);
        }
    }
}
