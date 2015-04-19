package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.rolle.doctor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hua on 2015/4/3.
 */
public class PatientHListAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List data;

    final  static int TYPE_0=0;
    final  static int TYPE_1=1;
    final  static int TYPE_2=2;


    public PatientHListAdapater(Context mContext, List data) {
        this.mContext = mContext;
        this.data = data;
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
            ViewHolder holder=(ViewHolder)viewHolder;
            List<String> startData=new ArrayList<String>();
            startData.add("至2015-1-1");
            startData.add("至2015-2-1");
            startData.add("至2015-3-1");
            startData.add("至2015-4-1");
            YearSpinnerAdpater adpater=new YearSpinnerAdpater(mContext,R.layout.sp_check_text,startData.toArray());
            holder.spStart.setAdapter(adpater);

            List<String> startEnd=new ArrayList<String>();
            startEnd.add("至2015-1-29");
            startEnd.add("至2015-2-29");
            startEnd.add("至2015-3-1");
            startEnd.add("至2015-4-1");
            YearSpinnerAdpater adpaterEnd=new YearSpinnerAdpater(mContext,R.layout.sp_check_text,startData.toArray());
            holder.spEnd.setAdapter(adpaterEnd);
        }

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
        Spinner spStart,spEnd;
        public ViewHolder(View itemView) {
            super(itemView);
            spStart=(Spinner)itemView.findViewById(R.id.sp_start);
            spEnd=(Spinner)itemView.findViewById(R.id.sp_end);
        }

    }
}
