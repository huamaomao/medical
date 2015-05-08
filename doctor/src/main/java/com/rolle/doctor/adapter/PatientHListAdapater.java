package com.rolle.doctor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.android.common.viewmodel.ModelEnum;
import com.android.common.viewmodel.ViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.litesuits.http.response.Response;
import com.rolle.doctor.R;
import com.rolle.doctor.viewmodel.UserModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class PatientHListAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private   List data;

    final  static int TYPE_0=0;
    final  static int TYPE_1=1;
    final  static int TYPE_2=2;
    private UserModel model;

    public PatientHListAdapater(Context mContext, List data) {
        this.mContext = mContext;
        this.data = data;
        model=new UserModel(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       switch (viewType){
           case TYPE_0:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_patient_head,parent,false)){};
           case TYPE_1:
               return new RecyclerView.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_patient_table,parent,false)){};
           default:
               return new RecyclerView.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_patient_grid,parent,false)){};
       }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder ,int position) {
        if (position==0){
            final  ViewHolder holder=(ViewHolder)viewHolder;
            final List<String> startData=new ArrayList<>();
            startData.add("2015-1-1");
            YearSpinnerAdpater adpater=new YearSpinnerAdpater(mContext,R.layout.sp_check_text,startData.toArray());
            holder.spStart.setAdapter(adpater);
            initPieChart( holder.pieChart);

            model.requestBloodList(holder.spStart.getSelectedItem().toString(), new ViewModel.ModelListener() {
                @Override
                public void model(Response response, Object o) {

                }

                @Override
                public void errorModel(ModelEnum modelEnum) {

                }

                @Override
                public void view() {

                }
            });
        }

    }

    private void initPieChart(PieChart pieChart) {

        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setDragDecelerationFrictionCoef(0.95f);

        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");

        pieChart.setCenterTextTypeface(Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Light.ttf"));

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
        yVals1.add(new Entry(3, 0));
        yVals1.add(new Entry(3, 1));
        yVals1.add(new Entry(3, 2));
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("正常 0次");
        xVals.add("偏低 6次");
        xVals.add("偏高 6次");

        PieDataSet dataSet = new PieDataSet(yVals1, "血糖数");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);

        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);
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
