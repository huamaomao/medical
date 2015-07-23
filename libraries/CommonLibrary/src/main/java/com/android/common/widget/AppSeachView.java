package com.android.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.common.R;

/**
 * Created by Hua_ on 2015/4/1.
 */
public class AppSeachView extends ViewGroup{

    public AppSeachView(Context context) {
        super(context);
        init();
    }

    public AppSeachView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public AppSeachView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AppSeachView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){
        addView(LayoutInflater.from(getContext()).inflate(R.layout.seach_view,null));
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view=getChildAt(0);
       /* *//*float width=getResources().getDimension(R.dimen.seach_width)/2;
        float height=getResources().getDimension(R.dimen.seach_height)/2;
        float left=getMeasuredWidth()/2-width;
        float right=getMeasuredWidth()/2+width;
        float top=getMeasuredHeight()/2-height;
        float button=getMeasuredHeight()/2+height;*//*
        view.layout((int)left,(int)top,(int)right,(int)button);*/
    }
}
