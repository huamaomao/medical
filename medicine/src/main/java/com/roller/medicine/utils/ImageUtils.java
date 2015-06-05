package com.roller.medicine.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;


import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;

public class ImageUtils {
	/**
	 * 根据宽度等比例缩放图
	 * 
	 * @param defaultBitmap
	 * @param width
	 * @return
	 */
	public static Bitmap resizeImageByWidth(Bitmap defaultBitmap,
			int targetWidth) {
		int rawWidth = defaultBitmap.getWidth();
		int rawHeight = defaultBitmap.getHeight();
		float targetHeight = targetWidth * (float) rawHeight / (float) rawWidth;
		float scaleWidth = targetWidth / (float) rawWidth;
		float scaleHeight = targetHeight / (float) rawHeight;
		Matrix localMatrix = new Matrix();
		localMatrix.postScale(scaleHeight, scaleWidth);
		return Bitmap.createBitmap(defaultBitmap, 0, 0, rawWidth, rawHeight,
				localMatrix, true);
	}

	/**
	 * 根据宽度等比例缩放图
	 * 
	 * @param defaultBitmap
	 * @param width
	 * @return
	 */
	public static Drawable resizeImageByWidth(Drawable defaultDrawable,
			int targetWidth) {
		BitmapDrawable img = (BitmapDrawable) defaultDrawable;
		Bitmap bitImg = ImageUtils.resizeImageByWidth(img.getBitmap(),
				targetWidth);
		Drawable drawableImg = new BitmapDrawable(bitImg);
		return drawableImg;
	}

	/**
	 * 将给定图片维持宽高比缩放后，截取正中间的正方形部分。
	 * 
	 * @param bitmap
	 *            原图
	 * @param edgeLength
	 *            希望得到的正方形部分的边长
	 * @return 缩放截取正中部分后的位图。
	 */
	public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
		if (null == bitmap || edgeLength <= 0) {
			return null;
		}
		Bitmap result = bitmap;
		int widthOrg = bitmap.getWidth();
		int heightOrg = bitmap.getHeight();

		if (widthOrg > edgeLength && heightOrg > edgeLength) {
			// 压缩到一个最小长度是edgeLength的bitmap
			int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math
					.min(widthOrg, heightOrg));
			int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
			int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
			Bitmap scaledBitmap;

			try {
				scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,
						scaledHeight, true);
			} catch (Exception e) {
				return null;
			}

			// 从图中截取正中间的正方形部分。
			int xTopLeft = (scaledWidth - edgeLength) / 2;
			int yTopLeft = (scaledHeight - edgeLength) / 2;

			try {
				result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft,
						edgeLength, edgeLength);
				scaledBitmap.recycle();
			} catch (Exception e) {
				return null;
			}
		}

		return result;
	}

	/**
	 * 图片加圆角
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 将bitmap转换为本地文件
	 * 路径/storage/emulated/0/bluecollar/app/images//storage/emulated
	 * /0/bluecollar/app/images/
	 * 
	 * @param bitName
	 *            >>http://www.eoeandroid.com/uc_server/data/avatar/000/00/52/
	 *            85_avatar_middle.jpg
	 * @param mBitmap
	 * @return
	 * @throws IOException
	 */
	/*public static boolean saveBitmapToFile(String bitName, Bitmap mBitmap)
			throws Exception {
		File dirf = new File(Constants.SD_IMAGE_PATH);
		if (!dirf.exists()) {
			dirf.mkdirs();
		}
		FileOutputStream fOut = null;
		bitName = Constants.SD_IMAGE_PATH
				+ bitName.substring(bitName.lastIndexOf("/") + 1);
		File f = new File(bitName);
		Log.i(Constants.LOG_W, "图片本地存放位置:" + bitName);
		fOut = new FileOutputStream(f);
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		fOut.flush();
		if (fOut != null)
			fOut.close();
		return true;
	}*/

	/**
	 * 判断图片是否在SD卡中存在
	 * 
	 * @param bitName
	 * @return
	 */
	/*public static boolean isImgFileExists(String bitName) {
		bitName = Constants.SD_IMAGE_PATH
				+ bitName.substring(bitName.lastIndexOf("/") + 1);
		File f = new File(bitName);
		// Log.i(Constants.LOG_W,bitName+"图片是否存在 : "+f.exists() + "  是图片文件 : " +
		// f.isFile());
		return f.exists() && f.isFile();
	}

	public static String getImgSDAddr(String bitName) {
		bitName = Constants.SD_IMAGE_PATH
				+ bitName.substring(bitName.lastIndexOf("/") + 1);
		return bitName;
	}*/
	
	/**
	 * 旋转原图
	 * 
	 * @Title: initBitmap
	 * @return void
	 * @date 2012-12-13 下午5:37:15
	 */
	public static Bitmap initBitmap(Bitmap mBitmap, int degree) {
		int sampleSize = 1;
		Matrix m = new Matrix();
		m.setRotate(degree);
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();
		Bitmap map;
		try {
			map = Bitmap.createBitmap(mBitmap, 0, 0, width, height, m, true);
		} catch (OutOfMemoryError ooe) {

			m.postScale((float) 1 / sampleSize, (float) 1 / sampleSize);
			map = Bitmap.createBitmap(mBitmap, 0, 0, width, height, m, true);

		}
		return map;
	}
	
	/**
	 * 此处写方法描述
	 * 
	 * @Title: getBitmap
	 * @param intent
	 * @return void
	 * @date 2012-12-13 下午8:22:23
	 */
	public static Bitmap getBitmap(Uri uri,Context ctxt) {
		Bitmap bitmap = null;
		InputStream is = null;
		try {
			is = ImageUtils.getInputStream(uri,ctxt);
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {
				}
			}
		}
		return bitmap;
	}
	
	/**
	 * 获取输入流
	 * 
	 * @Title: getInputStream
	 * @param mUri
	 * @return
	 * @return InputStream
	 * @date 2012-12-14 上午9:00:31
	 */
	public static InputStream getInputStream(Uri mUri,Context ctxt) throws IOException {
		try {
			if (mUri.getScheme().equals("file")) {
				return new java.io.FileInputStream(mUri.getPath());
			} else {
				return ctxt.getContentResolver().openInputStream(mUri);
			}
		} catch (FileNotFoundException ex) {
			return null;
		}
	}
}
