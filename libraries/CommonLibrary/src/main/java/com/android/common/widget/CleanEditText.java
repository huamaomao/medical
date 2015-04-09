package com.android.common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.android.common.R;

/**
 * CleanEditText
 */
public class CleanEditText extends EditText {

    private Drawable imgDel;

    public CleanEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
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

        setDrawble();
        // 对EditText文本状态监听
        this.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawble();
            }
        });
    }

    /***
     * 设置图片
     */
    public void setDrawble() {
        this.setCompoundDrawablesWithIntrinsicBounds(null, null,imgDel, null);
    }

    /***
     * 设置删除事件监听
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgDel != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 50;
            if (rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }

}
