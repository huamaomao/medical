package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.rolle.doctor.R;
import com.rolle.doctor.domain.CityResponse;

import java.util.List;

/**
 * Created by Administrator on 2015/4/7 0007.
 */
public class DoctorListAdpater extends RecyclerView.Adapter<DoctorListAdpater.ViewHolder> {
    private List<CityResponse.Item> data;
    private Context mContext;
    private int index=-1;
    public DoctorListAdpater(Context mContext, List<CityResponse.Item> data) {
        this.mContext = mContext;
        this.data = data;
    }

    public void setIndex(int index) {
        this.index = index;
        notifyDataSetChanged();
    }

    public CityResponse.Item getIndex() {
        if (index==-1)
            return null;
        return data.get(index);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.spinner_item_checktext,parent,false));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvValue.setText(data.get(position).name);
        holder.tvValue.setChecked(false);
        holder.tvValue.setBackgroundResource(R.color.write);
        if (index==position)
            holder.tvValue.setChecked(true);
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        CheckedTextView tvValue;
        public ViewHolder(View itemView) {
            super(itemView);
            tvValue=(CheckedTextView)itemView;
        }

    }

};

