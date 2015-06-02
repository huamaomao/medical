package com.android.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.List;

public class MessageRecyclerAdapter<T> extends RecyclerView.Adapter<MessageRecyclerAdapter.ViewHolder> {
    public interface RecyclerAdapterMethods<T>{
        void onBindViewHolder(ViewHolder viewHolder,T t,final int i);
        ViewHolder onCreateViewHolder(ViewGroup viewGroup,final int i);
        int getItemCount();
    }


    public interface OnClickEvent<T> {

        void onClick(View v,T t,final int position);
    }
    private LinkedList<T> mData;
    private RecyclerAdapterMethods mRecyclerAdapterMethods;
    private OnClickEvent mOnClickEvent;


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        mRecyclerAdapterMethods.onBindViewHolder(viewHolder, mData.get(i),i);
        if (mOnClickEvent != null)
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickEvent.onClick(v,mData.get(i), i);
                }
            });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return mRecyclerAdapterMethods.onCreateViewHolder(viewGroup, i);
    }

    public void setOnClickEvent(OnClickEvent onClickEvent) {
        mOnClickEvent = onClickEvent;
    }

    public MessageRecyclerAdapter(LinkedList<T> data) {
        mData = data;
    }

    public List<T> getData() {
        return mData;
    }

    public void setData(LinkedList<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void addItem(T item, int position) {
        mData.add(position, item);
        notifyItemInserted(position);
    }
    public void addItem(T item) {
        int index= mData.indexOf(item);
        if (index!=-1){
            mData.remove(index);
            notifyItemChanged(index);
        }
        mData.addFirst(item);
        notifyDataSetChanged();

    }

    public void pushItem(T item){
        mData.push(item);
        notifyDataSetChanged();
    }


    public void addItemAll(List<T> data) {
        if (data==null){
            return;
        }
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void removeItem(T item) {
        int position = mData.indexOf(item);
        if (position < 0)
            return;
        mData.remove(item);
        notifyItemRemoved(position);
    }


    public int getItemCount() {
        return mRecyclerAdapterMethods.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    /**
     * You must call this method to set your normal adapter methods
     *
     * @param callbacks
     */
    public void implementRecyclerAdapterMethods(RecyclerAdapterMethods callbacks) {
        mRecyclerAdapterMethods = callbacks;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public  <T extends  View>T getView(int resId){
            return  (T)ViewHolder.this.itemView.findViewById(resId);
        }

        public ViewHolder setText(int resId,String title){
            ((TextView)getView(resId)).setText(title);
            return this;
        }
        public ViewHolder setImageResource(int resId,int imgId){
            ((ImageView)getView(resId)).setImageResource(imgId);
            return this;
        }


    }

}