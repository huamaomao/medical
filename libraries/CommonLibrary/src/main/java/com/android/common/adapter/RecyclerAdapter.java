package com.android.common.adapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.common.R;
import com.android.common.util.CommonUtil;
import com.android.common.util.DividerItemDecoration;
import java.util.List;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {



    /*****是否显示headView  无网络 无数据 item *******/
    public boolean isHeadView=false;


    /****是否有网络  ***/
    public int status=STATUS_NORMAL;
    public int netStatus=STATUS_NORMAL;

    public static final int STATUS_NETWORK=3;
    /*********正常***********/
    public static final int STATUS_NORMAL=0;
    public static final int STATUS_EMPTY=1;

    /***********无网络 无数据 *******************/
    public static final int TYPE_NET=0;
    /***********正常数据*******************/
    public static final int TYPE_ITEM=1;

    /***********无数据显示消息**********/
    public String empty=null;

    protected Context mContext;
    protected RecyclerView recyclerView;
    private DividerItemDecoration itemDecoration;
    private boolean isItemDecoration;
    protected List<T> mData;
    protected RecyclerAdapterMethods mRecyclerAdapterMethods;
    private OnClickEvent mOnClickEvent;
    /*******是否需要注册网络广播********/

    private RecyclerAdapter(){}

    public RecyclerAdapter(Context mContext, List<T> data){
        this.mContext=mContext;
        this.mData = data;
    }


    public RecyclerAdapter(Context mContext, List<T> data, RecyclerView recyclerView) {
        this.mContext=mContext;
        this.mData = data;
        this.recyclerView=recyclerView;
        itemDecoration=new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        //网络广播
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getContext().registerReceiver(broadcastReceiver,filter);
    }
    /********isNetwork  是否注册网络广播 **********/
    public RecyclerAdapter(Context mContext, List<T> data, RecyclerView recyclerView,boolean isNetwork) {
        this.mContext=mContext;
        this.mData = data;
        this.recyclerView=recyclerView;
        itemDecoration=new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        if (isNetwork){
            //网络广播
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            getContext().registerReceiver(broadcastReceiver, filter);
        }
    }

    /******8
     * 解除广播
     */
    public void onDestroyReceiver(){
        try {
            getContext().unregisterReceiver(broadcastReceiver);
        }catch (Exception e){}
    }



    public void setNetwork(){
        netStatus=STATUS_NETWORK;
        isHeadView=true;
        changeStatus();
    }

    public void setAvailableNetwork(){
        if (status==STATUS_NORMAL){
            netStatus=STATUS_NORMAL;
            isHeadView=false;
            changeStatus();
        }else {
            netStatus=STATUS_NORMAL;
            changeStatus();
        }
    }

    /*****
     * 有数据
     */
    public void setNoEmpty(){
        if (!isItemDecoration){
            recyclerView.addItemDecoration(itemDecoration);
            isItemDecoration=true;
        }
        if (netStatus==STATUS_NORMAL){
            status=STATUS_NORMAL;
            isHeadView=false;
            changeStatus();
        }else {
            status=STATUS_NORMAL;
            changeStatus();
        }
    }

    public void setEmpty(){
        if (isItemDecoration){
            recyclerView.removeItemDecoration(itemDecoration);
            isItemDecoration=false;
        }
        status=STATUS_EMPTY;
        isHeadView=true;
        changeStatus();
    }

    public void checkEmpty(){
        if (mData.size()==0){
            setEmpty();
        }else{
            setNoEmpty();
        }
    }

    public void changeStatus(){
        if (isHeadView){
            notifyDataSetChanged();
        }
    }


    public interface OnClickEvent<T> {
        void onClick(View v,T item,final int position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int position) {
        if (viewHolder instanceof EmptyViewHolder){
           EmptyViewHolder emptyViewHolder =(EmptyViewHolder)viewHolder;
            if (netStatus==STATUS_NORMAL){
                emptyViewHolder.tvNetwork.setVisibility(View.GONE);
            }else {
                emptyViewHolder.tvNetwork.setVisibility(View.VISIBLE);
                //  //设置网络
                emptyViewHolder.tvNetwork.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = null;
                        //判断手机系统的版本   就是3.0或以上版本
                        if(android.os.Build.VERSION.SDK_INT > 13 ) {
                            intent=new Intent(android.provider.Settings.ACTION_SETTINGS);
                        }else{
                            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                        }
                         mContext.startActivity(intent);
                    }
                });
            }
            if (status==STATUS_NORMAL){
                emptyViewHolder.loadView.setVisibility(View.GONE);
                emptyViewHolder.tvEmpty.setVisibility(View.GONE);
            }else {
                emptyViewHolder.loadView.setVisibility(View.VISIBLE);
                emptyViewHolder.tvEmpty.setVisibility(View.VISIBLE);
                if (CommonUtil.notNull(empty))
                    emptyViewHolder.tvEmpty.setText(empty);
            }

        }else {
            mRecyclerAdapterMethods.onBindViewHolder(viewHolder, getItem(position), position);
            if (mOnClickEvent != null)
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnClickEvent.onClick(v,getItem(position), position);
                    }

                });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType==TYPE_NET){
            return new EmptyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.empty_view,viewGroup,false));
        }
        return mRecyclerAdapterMethods.onCreateViewHolder(viewGroup, viewType);
    }




    @Override
    public int getItemViewType(int position) {
        if (position==0&&isHeadView)
            return TYPE_NET;
        return getItemType(position);
    }

    public int getItemType(int position){
        return TYPE_ITEM;
    }


    public void setOnClickEvent(OnClickEvent onClickEvent) {
        mOnClickEvent = onClickEvent;
    }

    public void notifyItemUpdate(int position){
        notifyItemChanged(isHeadView ? position + 1 : position);
    }

    public void notifyItemMove(int position){
        notifyItemRemoved(isHeadView ? position + 1 : position);
    }

    public void notifyItemAdd(int position){
        notifyItemInserted(isHeadView ? position + 1 : position);
    }

    public void notifyItemCount(int position,int count){
        notifyItemRangeInserted(isHeadView ? position + 1: position,count);
    }



    public List<T> getData() {
        return mData;
    }


    public void setData(List<T> data) {
        mData = data;
        checkEmpty();
        notifyDataSetChanged();
    }

    public T getItem(int position){
        if (isHeadView&&position==0)return null;
        return  mData.get(isHeadView ? position - 1 : position);
    }

    public void addItem(T item, int position) {
        mData.add(position, item);
        notifyItemInserted(position);

    }
    public void addItem(T item) {
        int index= mData.indexOf(item);
        if (index!=-1){
            mData.remove(index);
           // notifyItemRemoved(index);
        }
        mData.add(0, item);
        notifyDataSetChanged();
    }

    public void pushItem(T item) {
        int index= mData.indexOf(item);
        if (index!=-1){
            mData.remove(index);
        }
        mData.add(0,item);
       notifyDataSetChanged();
       checkEmpty();
    }


    public void addItemAll(List<T> data) {
        if (data!=null){
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
        checkEmpty();
    }

    public void addMoreItem(List<T> data) {
        int count=getItemCount();
        if (data!=null){
            mData.addAll(data);
            notifyItemCount(count,data.size());
        }
    }

    public void removeItem(T item) {
        int position = mData.indexOf(item);
        if (position < 0)
            return;
        mData.remove(item);
        notifyItemRemoved(position);
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        checkEmpty();

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
        private SparseArray views;
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public  <T extends  View>T getView(int resId){
            if (null==views)views=new SparseArray();
            T t=(T)views.get(resId);
            if (null==t){
               t=(T)itemView.findViewById(resId);
                views.append(resId,t);
            }
            return  t;
        }

        public ViewHolder setText(int resId,CharSequence title){
            ((TextView)getView(resId)).setText(title);
            return this;
        }

        public ViewHolder setOnClickListener(int resId,View.OnClickListener listener){
            (getView(resId)).setOnClickListener(listener);
            return this;
        }
        public void setOnClick(int resId,View.OnClickListener listener){
            getView(resId).setOnClickListener(listener);
        }

        public ViewHolder setImageResource(int resId,int imgId){
            ((ImageView)getView(resId)).setImageResource(imgId);
            return this;
        }
        public ViewHolder setViewHide(int resId){
            getView(resId).setVisibility(View.GONE);
            return this;
        }
        public ViewHolder setViewShow(int resId){
            getView(resId).setVisibility(View.VISIBLE);
            return this;
        }

    }

    public static class EmptyViewHolder extends ViewHolder{
        ImageView loadView;
        TextView tvEmpty,tvNetwork;
        public EmptyViewHolder(View itemView) {
            super(itemView);
            loadView=getView(R.id.iv_load);
            tvEmpty=getView(R.id.tv_empty);
            tvNetwork=getView(R.id.tv_network);

        }
        public void setEmpty(String emptyMsg){
            if (CommonUtil.notNull(tvEmpty)){
                tvEmpty.setText(emptyMsg);
            }
        }
    }

    /***************网络监听广播*****************/
    public BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           if (CommonUtil.isNetAvailable(context)){
                setAvailableNetwork();
           }else {
               setNetwork();
           }
        }
    };



    public Activity getContext(){
        return (Activity)mContext;
    }





    public  int getItemCount() {
        int count=mRecyclerAdapterMethods.getItemCount();
        if (isHeadView) ++count;
        return count;
    }


    public interface RecyclerAdapterMethods<T>{
        void onBindViewHolder(final ViewHolder viewHolder,final T item,final int position);
        ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType);
        int getItemCount();
    }
}