package com.android.common.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.common.R;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;

import java.util.List;

/**
 * @author Hua_
 * @Description: 自动设置加载更多
 * @date 2015/8/10 - 11:08
 */
public class MultiplicityRecylerAdapter<T> extends RecyclerAdapter{

    /******page start 2***/
    private int page=2;
    /***is more data ***/
    private boolean isMore=false;
    public static final int TYPE_FOOTER=2;
    /******是否显示 FooterView  *********/
    private boolean isFooterView=false;
    private final static String MORE_MESSAGE_LOADING="加载中...";
    private final static String MORE_MESSAGE_NO_MORE="--End--";

    /********* 默认多少条数据显示footerView*************/
    public static final int DATA_PAGE_SIZE=10;
    /****是否有更多数据****/
    private boolean isMoreData=true;
    private boolean isLoading;

    private OnLoadMoreListener onLoadMoreListener;
    private RecyclerView.OnScrollListener scrollListener;


    public MultiplicityRecylerAdapter(Context mContext, List<T> data,RecyclerView recyclerView, final SwipeRefreshLayout refreshLayout) {
        super(mContext, data, recyclerView);
        ViewUtil.initRecyclerViewDecoration(recyclerView, getContext(), this);

        scrollListener=new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                refreshLayout.setEnabled(((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0);
            }
        };

        scrollListener=new RecyclerOnScrollListener((LinearLayoutManager)recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                if (!isLoading&&isMoreData){
                    isFooterView=true;
                    if (!isFooterView){
                        MultiplicityRecylerAdapter.this.setLoadMore(true);
                    }
                    isLoading=true;
                    if (CommonUtil.notNull(onLoadMoreListener)){
                       onLoadMoreListener.onLoadMore(page);
                    }
                }
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (viewHolder instanceof FooterViewHolder){
            FooterViewHolder footerViewHolder =(FooterViewHolder) viewHolder;
            if (isMoreData)
                footerViewHolder.setMoreLoading();
            else
                footerViewHolder.setNoMoreData();
            return;
        }
        super.onBindViewHolder(viewHolder, position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType==TYPE_FOOTER)
            return new FooterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.refresh_footer,viewGroup,false));
        return super.onCreateViewHolder(viewGroup, viewType);
    }

    /****
     * page ++
     */
    public void setNextPage(List<T> list){
        Log.d("setNextPage。。。。"+list);
        addMoreItem(list);
        if (CommonUtil.isNull(list)||list.size()==0)
            setHasMoreData(false);
    }

    /*****
     * init more
     */
    public void setPageInit(){
        this.page=2;
        this.isLoading=false;
        this.isMore=false;

    }

    @Override
    public int getItemType(int position) {
        if (isFooterView&&position==getItemCount()-1){
            return TYPE_FOOTER;
        }
        return super.getItemType(position);
    }

    public  int getItemCount() {
        int count=mRecyclerAdapterMethods.getItemCount();
        if (isHeadView) ++count;
        if (isFooterView&&count>=DATA_PAGE_SIZE) ++count;
        return count;
    }


    public static class FooterViewHolder extends ViewHolder{
        ProgressBar progressBar;
        TextView tvMore;
        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar=getView(R.id.pb_loading);
            tvMore=getView(R.id.tv_more);
        }

        void setNoMoreData(){
            progressBar.setVisibility(View.GONE);
            tvMore.setText(MORE_MESSAGE_NO_MORE);
        }
        void setMoreLoading(){
            progressBar.setVisibility(View.VISIBLE);
            tvMore.setText(MORE_MESSAGE_LOADING);
        }
    }
    public void setLoadMore(boolean flag){
        this.isFooterView=flag;
        if (this.isFooterView){
            notifyItemInserted(getItemCount() - 1);
        }else {
            notifyItemRemoved(getItemCount() - 1);
        }
    }

    public void setHasMoreData(boolean flag){
        isMoreData=flag;
        notifyItemUpdate(getItemCount()-1);
    }


    public void setOnLoadMoreListener(OnLoadMoreListener loadListener) {
        this.onLoadMoreListener = loadListener;
    }

    public static interface OnLoadMoreListener {
        public void onLoadMore(int page);
    }


}
