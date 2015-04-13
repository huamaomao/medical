package com.android.common.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

/**
 * Created by Hua_ on 2015/4/13.
 */
public class InputMethodLinearLayout extends LinearLayout {

    private int width;
    protected OnSizeChangedListenner onSizeChangedListenner;
    private boolean sizeChanged  = false; //变化的标志
    private int height;
    private int screenWidth; //屏幕宽度
    private int screenHeight; //屏幕高度

    public InputMethodLinearLayout(Context context) {
        super(context);
        init();
    }

    public InputMethodLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InputMethodLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public InputMethodLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        DisplayMetrics metrics=new DisplayMetrics();
        ((Activity)getContext()).getWindowManager()
                .getDefaultDisplay().getMetrics(metrics);
        this.screenWidth = metrics.widthPixels;
        this.screenHeight = metrics.heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.width = widthMeasureSpec;
        this.height = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw,
                              int oldh) {
        //监听不为空、宽度不变、当前高度与历史高度不为0
        if ((this.onSizeChangedListenner!= null) && (w == oldw) && (oldw != 0)
                && (oldh != 0)) {
            if ((h >= oldh)
                    || (Math.abs(h - oldh) <= 1 * this.screenHeight / 4)) {
                if ((h <= oldh)
                        || (Math.abs(h - oldh) <= 1 * this.screenHeight / 4))
                    return;
                this.sizeChanged  = false;
            } else {
                this.sizeChanged  = true;
            }
            this.onSizeChangedListenner.onSizeChange(this.sizeChanged ,oldh, h);
            measure(this.width - w + getWidth(), this.height
                    - h + getHeight());
        }
    }
    /**
     * 设置监听事件
     * @param paramonSizeChangedListenner
     */
    public void setOnSizeChangedListenner(
            InputMethodLinearLayout.OnSizeChangedListenner paramonSizeChangedListenner) {
        this.onSizeChangedListenner = paramonSizeChangedListenner;
    }
    /**
     * 大小改变的内部接口
     * @author junjun
     *
     */
    public  interface OnSizeChangedListenner {
        public  void onSizeChange(boolean paramBoolean, int w,int h);
    }
}
