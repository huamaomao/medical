package com.roller.medicine.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCropUtils {

	private Activity act;
	private IImageCropFinish interf;


	/** 压缩图片大小(具体未测试) */
	private int crop = 200;
	/** 本地缓存图片路径(文件夹) */
	private File localFile;
	/** 这个得设置成原本缓存的路径 */
	private String firstPath = "/workpai.test";

	public final static int IMAGE_CROP = 9999;
	public final static int IMAGE_CARERA = 9998;
	public final static int IMAGE_FILE = 9997;


	private Intent getCropImageIntent(Uri paramUri1, Uri paramUri2)
	{
		Intent localIntent = new Intent("com.android.camera.action.CROP");
		localIntent.setDataAndType(paramUri1, "image/*");
		localIntent.putExtra("crop", "true");
		localIntent.putExtra("aspectX", 1);
		localIntent.putExtra("aspectY", 1);
		localIntent.putExtra("outputX", 300);
		localIntent.putExtra("outputY", 300);
		localIntent.putExtra("return-data", false);
		localIntent.putExtra("output", paramUri2);
		return localIntent;
	}

	public ImageCropUtils(Activity act, IImageCropFinish iImgCropFinish) {
		this.interf = iImgCropFinish;
		this.act = act;
		File dirFile = new File(
				(Environment.getExternalStorageDirectory() + firstPath));
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		localFile = new File(dirFile, "myTest.jpg");
	}

	public File getLocalFile(){
		return localFile;
	}

	public void jumpFile() throws Exception {
		if(act == null){
			throw new NullPointerException();
		}

		Intent intent = new Intent("android.intent.action.PICK");
		intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
				"image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", crop);// 输出图片大小
		intent.putExtra("outputY", crop);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("scale", true);

		intent.putExtra("scaleUpIfNeeded", true);
		act.startActivityForResult(intent, IMAGE_FILE);
	}

	public void jumpCamera() throws Exception {
		if(act == null){
			throw new NullPointerException();
		}

		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(localFile));
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", "JPEG");
		act.startActivityForResult(intent, IMAGE_CARERA);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data){
//		if (true){
//			interf.onImageCropFinish(EImgCrop.FAILED, null, null, "没有传入activity");
//			return;
//		}
		if (resultCode != act.RESULT_OK){
			interf.onImageCropFinish(EImgCrop.FAILED, null, null, "没有传入activity");
			return;
		}
		switch (requestCode) {
			case IMAGE_FILE:
			case IMAGE_CROP:
				Bundle bund = data.getExtras();
				Bitmap bitmap = null;
				Log.e("uri", "bund:" + bund);
				if (bund != null) {
					Log.e("uri", "bund_data:" + bund.get("data"));
					bitmap = (Bitmap) bund.get("data");
				}else{
					interf.onImageCropFinish(EImgCrop.FAILED, null, null, "获取图片失败");
					return;
				}
				if(bitmap != null) {
//				Log.e("uri", "占地:" + bitmap.getByteCount() + "-宽:" + bitmap.getWidth() + "-高:" + bitmap.getHeight());
//				mImg.setImageBitmap(bitmap);
					Log.e("uri", "开始缓存本地图片");
					saveInLocal(bitmap);
				}else {
					interf.onImageCropFinish(EImgCrop.FAILED, null, null, "获取图片失败");
					return;
					//TODO 这里应该返回失败操作
				}
				break;
			case IMAGE_CARERA:
				if (localFile != null && localFile.exists()) {//直接output到本地路径了
					Log.e("uri", "data:"+data);
					Intent intent = new Intent();
					intent.setAction("com.android.camera.action.CROP");
					intent.setDataAndType(Uri.fromFile(localFile), "image/*");// mUri是已经选择的图片Uri
					intent.putExtra("crop", "true");
					intent.putExtra("aspectX", 1);// 裁剪框比例
					intent.putExtra("aspectY", 1);
					intent.putExtra("outputX", crop);// 输出图片大小
					intent.putExtra("outputY", crop);
					intent.putExtra("return-data", true);
					act.startActivityForResult(intent, IMAGE_CROP);
				}else {
					//TODO
				}
				break;
			default:
				break;
		}
	}

	/**
	 * 异步Task
	 * @param bitmap
	 */
	private void saveInLocal(final Bitmap bitmap){
		AsyncTask<Bitmap, Integer, File> saveLocalTask = new AsyncTask<Bitmap, Integer, File>() {
			@Override
			protected File doInBackground(Bitmap... arg0) {
				Log.e("uri", "异步中-保存操作");
				File file = localImage(arg0[0], localFile);
				if(file == null) {
					interf.onImageCropFinish(EImgCrop.BITMAP_SUCCESS_BUT_LOCAL_FAILD, bitmap, null, "本地缓存失败");
					return null;
				}else {
					return file;
				}
			}
			@Override
			protected void onPostExecute(File result) {
				super.onPostExecute(result);
				Log.e("uri", "文件生成路径:" + result.getAbsolutePath());
				interf.onImageCropFinish(EImgCrop.SUCCESS, bitmap, result.getAbsolutePath(), "成功");
			}
		};
		saveLocalTask.execute(bitmap);
	}

	/**
	 * 把bitmap写入文件
	 * @param bitmap
	 * @param file
	 * @return
	 */
	public static File localImage(Bitmap bitmap, File file) {
		Bitmap mBitmap = bitmap;
		FileOutputStream b = null;
		try {
			b = new FileOutputStream(file);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}


	public enum EImgCrop {
		BITMAP_SUCCESS_BUT_LOCAL_FAILD, SUCCESS, FAILED
	}

	public interface IImageCropFinish {
		public void onImageCropFinish(EImgCrop eImgCrop, Bitmap bitmap, String filePath , String value);
	}



}
