package com.roller.medicine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.roller.medicine.R;
import com.roller.medicine.info.HomeInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/4/7 0007.
 */
public class YearSpinnerAdpater extends ArrayAdapter {
    public YearSpinnerAdpater(Context context, int resource) {
        super(context, resource);
    }

    public YearSpinnerAdpater(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public YearSpinnerAdpater(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    public YearSpinnerAdpater(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public YearSpinnerAdpater(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public YearSpinnerAdpater(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public Object getItem(int position) {
        Object o=super.getItem(position);
        if (o instanceof String){
             return o;
        }else if (o instanceof HomeInfo.Family){
            HomeInfo.Family family=(HomeInfo.Family)o;
            return family.appellation;
        }
        return o;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        ViewHolder holder=null;
       if (convertView==null) {
           holder=new ViewHolder( View.inflate(getContext(), R.layout.spinner_item_text,
                   null));
           holder.itemView.setTag(holder);
       }else {
           holder=(ViewHolder)convertView.getTag();
       }
        Object o=getItem(position);
        if (o instanceof String){
            holder.tvValue.setText(o.toString());
        }else if (o instanceof HomeInfo.Family){
            HomeInfo.Family family=(HomeInfo.Family)o;
            holder.tvValue.setText(family.appellation);
        }
        return holder.itemView;
    }
    public  static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvValue;
        public ViewHolder(View itemView) {
            super(itemView);
            tvValue=(TextView)itemView;
        }

    }

};

