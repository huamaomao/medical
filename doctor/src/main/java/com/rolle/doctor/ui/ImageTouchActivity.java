package com.rolle.doctor.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.android.common.util.CommonUtil;
import com.android.common.widget.ImageViewTouch;
import com.rolle.doctor.R;
import com.rolle.doctor.util.AppConstants;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author Hua_
 * @Description:
 * @date 2015/8/5 - 10:26
 */
public class ImageTouchActivity extends BaseActivity {

    @InjectView(R.id.iv_photo)
    ImageViewTouch imageViewTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
    }



    @Override
    protected void initView() {
        super.initView();
        String path=getIntent().getStringExtra(AppConstants.ITEM);
        if (CommonUtil.notEmpty(path)){
            imageViewTouch.setImageBitmap(BitmapFactory.decodeFile(path),null,0.5f,3f);
        }
        imageViewTouch.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
            @Override
            public void onSingleTapConfirmed() {
                imageViewTouch.destroyDrawingCache();
                finish();
            }
        });
    }


}
