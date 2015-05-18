package com.rolle.doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rolle.doctor.R;
import com.rolle.doctor.domain.CityResponse;

import java.util.List;

/**
 * Created by Administrator on 2015/4/7 0007.
 */
public class CitySpinnerAdpater extends ArrayAdapter {
    public CitySpinnerAdpater(Context context, int resource) {
        super(context, resource);
    }

    public CitySpinnerAdpater(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public CitySpinnerAdpater(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    public CitySpinnerAdpater(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public CitySpinnerAdpater(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public CitySpinnerAdpater(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
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
        holder.tvValue.setText(getItem(position).toString());
        return holder.itemView;
    }
    public  static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvValue;
        public ViewHolder(View itemView) {
            super(itemView);
            tvValue=(TextView)itemView;
        }

    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public Object getItem(int position) {
        CityResponse.Item item=(CityResponse.Item)super.getItem(position);
        return item.name;
    }
};

