package com.android.common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;

import com.android.common.R;
import com.android.common.util.Log;

/**
 * CleanEditText
 */
public class CleanEditText extends AppCompatEditText {

    private Drawable imgDel;
    /******
     * 是否启用复制粘贴
     */
    private boolean flag=false;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

  

    public CleanEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CleanEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CleanEditText(Context context) {
        super(context);
        init();
    }

    /***
     * 初始化
     */
    public void init() {
        imgDel = getResources().getDrawable(R.drawable.icon_delete);


        // 对EditText文本状态监听
        this.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (count>0){
                    setDrawble();
                }else {
                    cleanDrawble();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return flag;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return flag;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return flag;
            }
        });
    }

    /***
     * 设置图片
     */
    public void setDrawble() {
        this.setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null,imgDel, null);
    }
    public void cleanDrawble(){
        this.setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null,null, null);
    }

    /***
     * 设置删除事件监听
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgDel != null && event.getAction() == MotionEvent.ACTION_UP) {
            boolean isClean =(event.getX() > (getWidth() - getTotalPaddingRight()))&&
                    (event.getX() < (getWidth() - getPaddingRight()));
            if (isClean) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

}
