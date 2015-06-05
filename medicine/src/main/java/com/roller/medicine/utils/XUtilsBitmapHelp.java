package com.roller.medicine.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.lidroid.xutils.BitmapUtils;
import com.roller.medicine.R;
import com.roller.medicine.httpservice.Constants;

public class XUtilsBitmapHelp {
	private XUtilsBitmapHelp() {
	}

	private static BitmapUtils bitmapUtils;
	
	private static BitmapUtils headBitMapUtils;

	/**
	 * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
	 * 
	 * @param appContext
	 *            application context
	 * @return
	 */
//	public static BitmapUtils getBitmapUtilsInstance(Context appContext) {
//		if (bitmapUtils == null) {
//			bitmapUtils = new BitmapUtils(appContext);
//			bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
//			bitmapUtils
//					.configDefaultLoadingImage(R.drawable.public_img_loading);
//			bitmapUtils
//					.configDefaultLoadFailedImage(R.drawable.public_img_error);
//		}
//		return bitmapUtils;
//	}
	/**
	 * 图片频道图片加载方法
	 * @param appContext
	 * @param failedImage
	 * @return
	 */
	public static BitmapUtils getBitmapUtilsInstance(Context appContext,int failedImage) {
		if (bitmapUtils == null) {
			bitmapUtils = new BitmapUtils(appContext);
			bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		}
		bitmapUtils
				.configDefaultLoadingImage(R.color.public_bg);
		bitmapUtils
				.configDefaultLoadFailedImage(failedImage);
		return bitmapUtils;
	}
	
	public static BitmapUtils getBitmapUtilsInstance(Context appContext,int loadingImg, int failedImg) {
		if (headBitMapUtils == null) {
			headBitMapUtils = new BitmapUtils(appContext);
			headBitMapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		}
		if(loadingImg != Constants.TAG.TAG_NONE)
			headBitMapUtils.configDefaultLoadingImage(loadingImg);
		if(failedImg != Constants.TAG.TAG_NONE)
			headBitMapUtils.configDefaultLoadFailedImage(failedImg);
		return headBitMapUtils;
	}
	
}