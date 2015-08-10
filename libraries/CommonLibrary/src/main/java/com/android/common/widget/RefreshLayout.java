package com.android.common.widget;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import com.android.common.R;
import com.android.common.adapter.RecyclerAdapter;

/*****
 * 刷新   加载更多
 * @author: maple
 */
public final class RefreshLayout extends SwipeRefreshLayout {

    private int mTouchSlop;
    private View mListView;
    private OnMoreListener onMoreListener;
    private View mListViewFooter;
    private int mYDown;
    private int mLastY;

    /******page start 2***/
    private int page=2;
    /***is more data ***/
    private boolean isMore=false;
    private boolean isLoading = false;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //set the footer of the ListView with a ProgressBar in it
    public void setFooterView(Context context, ListView mListView, int layoutId) {
        this.setColorSchemeResources(getResources().getIntArray(R.array.RefreshStyle));
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mListViewFooter = LayoutInflater.from(context).inflate(layoutId, null, false);
        mListView.addFooterView(mListViewFooter);
        mListView.setFooterDividersEnabled(false);
        this.mListView = mListView;
    }

    /****
     *
     * @param mView View is ListView or RecyclerView
     * @throws  RuntimeException  View is ListView or RecyclerView
     */
    public void setFooterView( View mView) {
        this.mListView = mView;
        int res[]=getResources().getIntArray(R.array.RefreshStyle);
        this.setColorSchemeColors(res);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mListViewFooter = LayoutInflater.from(getContext()).inflate(R.layout.refresh_footer, null, false);
        if (mListView instanceof  ListView){
            ListView listView=(ListView)mListView;
            listView.addFooterView(mListViewFooter);
            listView.setFooterDividersEnabled(false);
        }else if (mListView instanceof RecyclerView){

        }else {
            throw new RuntimeException("View  is ListView or RecyclerView !");
        }
    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                mLastY = (int) event.getRawY();
                if (isPullingUp())
                    //you can add view or hint here when pulling up the ListView
                break;

            case MotionEvent.ACTION_UP:
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private boolean canLoad() {
        return isBottom() && !isLoading && isPullingUp()&&isMore;
    }

    private boolean isBottom() {
        if (this.mListView instanceof  ListView){
            ListView listView=(ListView)this.mListView;
            listView.addFooterView(mListViewFooter);
            listView.setFooterDividersEnabled(false);
            if (listView.getCount() > 0) {
                if (listView.getLastVisiblePosition() == listView.getAdapter().getCount() - 1 &&
                        listView.getChildAt(listView.getChildCount() - 1).getBottom() <= listView.getHeight()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPullingUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    private void loadData() {
        if (onMoreListener != null) {
            setLoading(true);
            onMoreListener.onMore(page);
        }
    }

    /****
     * page ++
     */
    public void setNextPage(){
        page++;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            if (isRefreshing()) setRefreshing(false);
            if (this.mListView instanceof  ListView){
                ListView listView=(ListView)this.mListView;
                if (listView.getFooterViewsCount() == 0) {
                    listView.addFooterView(mListViewFooter);
                    listView.setSelection(listView.getAdapter().getCount() - 1);
                } else {
                    listView.setVisibility(VISIBLE);
                }
            }

        } else {
            if (this.mListView instanceof  ListView) {
                ListView listView = (ListView) this.mListView;
                if (listView.getAdapter() instanceof HeaderViewListAdapter) {
                    listView.removeFooterView(mListViewFooter);
                } else {
                    mListViewFooter.setVisibility(View.GONE);
                }
            }

            mYDown = 0;
            mLastY = 0;
        }
    }

    /*****
     * init more
     */
    public void setPageInit(){
        this.page=2;
        this.isLoading=false;
        this.isMore=false;

    }

    public void setOnMoreListener(OnMoreListener loadListener) {
        onMoreListener = loadListener;
    }

    public static interface OnMoreListener {
        public void onMore(int page);
    }


   /* private boolean canChildScrollUp() {
        if(Build.VERSION.SDK_INT < 14) {
            if(!(this.mTarget instanceof AbsListView)) {
                return this.mTarget.getScrollY() > 0;
            } else {
                AbsListView absListView = (AbsListView)this.mTarget;
                return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            }
        } else {
            return ViewCompat.canScrollVertically(this.mTarget, -1);
        }
    }

    private boolean canChildScrollDown() {
        if(Build.VERSION.SDK_INT < 14) {
            if(!(this.mTarget instanceof AbsListView)) {
                return this.mTarget.getScrollY() > 0;
            } else {
                AbsListView absListView = (AbsListView)this.mTarget;
                return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            }
        } else {
            return ViewCompat.canScrollVertically(this.mTarget, -1);
        }
    }*/
}