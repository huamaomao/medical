package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.rolle.doctor.R;

import java.util.List;

/**
 * Created by Administrator on 2015/4/7 0007.
 */
public class DoctorSpinnerAdpater extends ArrayAdapter {
    public int index=0;
    public DoctorSpinnerAdpater(Context context, int resource) {
        super(context, resource);
    }

    public DoctorSpinnerAdpater(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public DoctorSpinnerAdpater(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    public DoctorSpinnerAdpater(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public DoctorSpinnerAdpater(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public DoctorSpinnerAdpater(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        ViewHolder holder=null;
       if (convertView==null) {
           holder=new ViewHolder( View.inflate(getContext(), R.layout.spinner_item_checktext,
                   null));
           holder.itemView.setTag(holder);
       }else {
           holder=(ViewHolder)convertView.getTag();
       }
       holder.tvValue.setText(getItem(position).toString());
       holder.tvValue.setChecked(false);
       if (index==position){
           holder.tvValue.setChecked(true);
       }
        return holder.itemView;
    }
    public  static class ViewHolder extends RecyclerView.ViewHolder{
        CheckedTextView tvValue;
        public ViewHolder(View itemView) {
            super(itemView);
            tvValue=(CheckedTextView)itemView;
        }

    }

};

