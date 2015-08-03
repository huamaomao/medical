//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.android.common.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

public class StickyRecyclerHeadersDecoration extends ItemDecoration {
    private final StickyRecyclerHeadersAdapter mAdapter;
    private final LongSparseArray<View> mHeaderViews = new LongSparseArray();
    private final SparseArray<Rect> mHeaderRects = new SparseArray();

    public StickyRecyclerHeadersDecoration(StickyRecyclerHeadersAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int orientation = this.getOrientation(parent);
        int itemPosition = parent.getChildPosition(view);
        if(this.hasNewHeader(itemPosition)) {
            View header = this.getHeaderView(parent, itemPosition);
            if(orientation == 1) {
                outRect.top = header.getHeight();
            } else {
                outRect.left = header.getWidth();
            }
        }

    }

    public void onDrawOver(Canvas canvas, RecyclerView parent, State state) {
        super.onDrawOver(canvas, parent, state);
        int orientation = this.getOrientation(parent);
        this.mHeaderRects.clear();
        if(parent.getChildCount() > 0 && this.mAdapter.getItemCount() > 0) {
            View firstView = parent.getChildAt(0);
            int firstPosition = parent.getChildPosition(firstView);
            int translationX;
            int translationY;
            if(this.mAdapter.getHeaderId(firstPosition) >= 0L) {
                View i = this.getHeaderView(parent, firstPosition);
                View position = this.getNextView(parent);
                translationX = Math.max(parent.getChildAt(0).getLeft() - i.getWidth(), 0);
                translationY = Math.max(parent.getChildAt(0).getTop() - i.getHeight(), 0);
                int header = parent.getChildPosition(position);
                if(header > 0 && this.hasNewHeader(header)) {
                    View secondHeader = this.getHeaderView(parent, header);
                    if(orientation == 1 && position.getTop() - secondHeader.getHeight() - i.getHeight() < 0) {
                        translationY += position.getTop() - secondHeader.getHeight() - i.getHeight();
                    } else if(orientation == 0 && position.getLeft() - secondHeader.getWidth() - i.getWidth() < 0) {
                        translationX += position.getLeft() - secondHeader.getWidth() - i.getWidth();
                    }
                }

                canvas.save();
                canvas.translate((float)translationX, (float)translationY);
                i.draw(canvas);
                canvas.restore();
                this.mHeaderRects.put(firstPosition, new Rect(translationX, translationY, translationX + i.getWidth(), translationY + i.getHeight()));
            }

            for(int var13 = 1; var13 < parent.getChildCount(); ++var13) {
                int var14 = parent.getChildPosition(parent.getChildAt(var13));
                if(this.hasNewHeader(var14)) {
                    translationX = 0;
                    translationY = 0;
                    View var15 = this.getHeaderView(parent, var14);
                    if(orientation == 1) {
                        translationY = parent.getChildAt(var13).getTop() - var15.getHeight();
                    } else {
                        translationX = parent.getChildAt(var13).getLeft() - var15.getWidth();
                    }

                    canvas.save();
                    canvas.translate((float)translationX, (float)translationY);
                    var15.draw(canvas);
                    canvas.restore();
                    this.mHeaderRects.put(var14, new Rect(translationX, translationY, translationX + var15.getWidth(), translationY + var15.getHeight()));
                }
            }
        }

    }

    private View getNextView(RecyclerView parent) {
        View firstView = parent.getChildAt(0);
        int firstPosition = parent.getChildPosition(firstView);
        View firstHeader = this.getHeaderView(parent, firstPosition);

        for(int i = 0; i < parent.getChildCount(); ++i) {
            View child = parent.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams)child.getLayoutParams();
            if(this.getOrientation(parent) == 1) {
                if(child.getTop() - layoutParams.topMargin > firstHeader.getHeight()) {
                    return child;
                }
            } else if(child.getLeft() - layoutParams.leftMargin > firstHeader.getWidth()) {
                return child;
            }
        }

        return null;
    }

    private int getOrientation(RecyclerView parent) {
        if(parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager)parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else {
            throw new IllegalStateException("StickyListHeadersDecoration can only be used with a LinearLayoutManager.");
        }
    }

    public int findHeaderPositionUnder(int x, int y) {
        for(int i = 0; i < this.mHeaderRects.size(); ++i) {
            Rect rect = (Rect)this.mHeaderRects.get(this.mHeaderRects.keyAt(i));
            if(rect.contains(x, y)) {
                return this.mHeaderRects.keyAt(i);
            }
        }

        return -1;
    }

    public View getHeaderView(RecyclerView parent, int position) {
        long headerId = this.mAdapter.getHeaderId(position);
        View header = (View)this.mHeaderViews.get(headerId);
        if(header == null) {
            ViewHolder viewHolder = this.mAdapter.onCreateHeaderViewHolder(parent);
            this.mAdapter.onBindHeaderViewHolder(viewHolder, position);
            header = viewHolder.itemView;
            if(header.getLayoutParams() == null) {
                header.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
            }

            int widthSpec;
            int heightSpec;
            if(this.getOrientation(parent) == 1) {
                widthSpec = MeasureSpec.makeMeasureSpec(parent.getWidth(), 1073741824);
                heightSpec = MeasureSpec.makeMeasureSpec(parent.getHeight(), 0);
            } else {
                widthSpec = MeasureSpec.makeMeasureSpec(parent.getWidth(), 0);
                heightSpec = MeasureSpec.makeMeasureSpec(parent.getHeight(), 1073741824);
            }

            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams().width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);
            header.measure(childWidth, childHeight);
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
            this.mHeaderViews.put(headerId, header);
        }

        return header;
    }

    private boolean hasNewHeader(int position) {
        return this.getFirstHeaderPosition() == position?true:(this.mAdapter.getHeaderId(position) < 0L?false:(position > 0 && position < this.mAdapter.getItemCount()?this.mAdapter.getHeaderId(position) != this.mAdapter.getHeaderId(position - 1):false));
    }

    private int getFirstHeaderPosition() {
        for(int i = 0; i < this.mAdapter.getItemCount(); ++i) {
            if(this.mAdapter.getHeaderId(i) >= 0L) {
                return i;
            }
        }

        return -1;
    }

    public void invalidateHeaders() {
        this.mHeaderViews.clear();
    }
}
