package com.roller.medicine.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;


public class OtherUtils {

	private static long lastClickTime;
	
	/**
	 * 防止重复点击
	 * @return
	 */
	public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();   
        if ( time - lastClickTime < 500) {   
            return true;   
        }   
        lastClickTime = time;   
        return false;   
    }
	private static long lastWorkClickTime;

	/**
	 * 互联网点击
	 * @return
	 */
	public synchronized static boolean isFastWorkClick(int currentTime) {
        long time = System.currentTimeMillis();   
        if ( time - lastWorkClickTime < currentTime) {   
            return true;   
        }   
        lastWorkClickTime = time;   
        return false;   
    }
	/**
	 * 计算数据最小值
	 * 
	 * @param data
	 * @return
	 */
	public static int getMinKey(int[] data) {
		int[] num = data;
		for (int i = 0; i < num.length - 1; i++) {
			for (int j = 0; j < num.length - i - 1; j++) {
				if (num[j] > num[j + 1]) {
					int temp = num[j + 1];
					num[j + 1] = num[i];
					num[j] = temp;
				}
			}
		}
		int min = num[0];
		return min;
	}

	/**
	 * 计算数据最大值
	 * 
	 * @param data
	 * @return
	 */
	public static int getMaxKey(int[] data) {
		int[] num = data;
		for (int i = 0; i < num.length - 1; i++) {
			for (int j = 0; j < num.length - i - 1; j++) {
				if (num[j] < num[j + 1]) {
					int temp = num[j + 1];
					num[j + 1] = num[i];
					num[j] = temp;
				}
			}
		}
		int min = num[0];
		return min;
	}

	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public final static String MD5(String str) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();

	}

	/***
	 * 验证手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean checkTelephone(String phone) {
		String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
		return Pattern.matches(regex, phone);
	}
	
	/**
	 * 字符串转大写
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String toUpperCase(String str)
			throws UnsupportedEncodingException {
		byte[] data = str.getBytes("UTF-8");
		int len = str.length();
		int end = len;
		int dist = 'A' - 'a';
		for (int i = 0; i < end; i++) {
			if (data[i] >= 'a' && data[i] <= 'z') {
				data[i] += dist;
			}
		}
		return new String(data);
	}

	/**
	 * 字符串转小写(未测试)
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String toLowerCase(String str)
			throws UnsupportedEncodingException {
		byte[] data = str.getBytes("UTF-8");
		int len = str.length();
		int end = len;
		int dist = 'a' - 'A';
		for (int i = 0; i < end; i++) {
			if (data[i] >= 'A' && data[i] <= 'Z') {
				data[i] += dist;
			}
		}
		return new String(data);
	}

	/**
	 * json串转成map格式
	 * 
	 * @param jsonstr
	 * @return
	 */
	public static Map<String, Object> getJsonResultMap(String jsonstr) {
		Map<String, Object> valueMap = null;
		try {
			JSONObject jsonObj = new JSONObject(jsonstr);
			valueMap = new HashMap<String, Object>();
			for (int i = 0; i < jsonObj.length(); i++) {
				Iterator<String> keyIter = jsonObj.keys();
				String key;
				Object value;
				while (keyIter.hasNext()) {
					key = keyIter.next();
					value = jsonObj.get(key);
					valueMap.put(key, value);
				}
			}
		} catch (Exception e1) {
//			e1.printStackTrace();
		}
		return valueMap;
	}

	/***
	 * 隐藏输入框
	 * 
	 * @param edit
	 */
	public static void hindEditTextInput(EditText edit) {
		((InputMethodManager) edit.getContext().getSystemService(
				Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
				edit.getWindowToken(), 0);
	}

	/**
	 * 显示软键盘
	 * 
	 * @param edit
	 */
	public static void showEditTextInput(EditText edit) {
		InputMethodManager imm = (InputMethodManager) edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(edit, 0);
	}

	/**
	 * dp转px
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}

	/**
	 * 得到不同的颜色
	 * 
	 * @param colorInt
	 * @return
	 */
	public static int getMoreColor(int colorInt) {
		String[] colors = new String[] { "#0eb8d7", "#32d492", "#ee5858",
				"#ee9058", "#6666FF", "#66FF99", "#99FF00" };
		return Color.parseColor(colors[colorInt % colors.length]);
	}

	/**
	 * 检测Sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	/**
	 * 判断JSON是否符合格式
	 * 
	 * @param json
	 * @return
	 */
	public static boolean isRealJson(String json) {
		try {
			com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
			obj.parse(json);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String TIP = "CJL";


	/**
	 * 计算经纬度
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	/** 地球半径 */
	private static double EARTH_RADIUS = 6378.137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static String getDintance(String melongitude, String melatitude,
			String longitude, String latitude) {
		Double melon = Double.parseDouble(melongitude);
		Double melat = Double.parseDouble(melatitude);
		if (melon == 0 || melat == 0 || "".equals(longitude)
				|| "".equals(latitude) || longitude == null || latitude == null) {
			return "未知距离";
		}
		double otherlon = Double.parseDouble(longitude);
		double otherlat = Double.parseDouble(latitude);
		double radLat1 = rad(melat);
		double radLat2 = rad(otherlat);
		double a = radLat1 - radLat2;
		double b = rad(melon) - rad(otherlon);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = (Math.round(s * 10000) / 10000);

		java.text.NumberFormat formate = java.text.NumberFormat
				.getNumberInstance();
		formate.setMaximumFractionDigits(1);// 设定小数最大位数 ，那么显示的最后会四舍五入的
		String m = formate.format(s);
		return m + "米";
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles)) {
			return false;
		} else {
			return mobiles.matches(telRegex);
		}
	}

	/**
	 * 检查当前网络是否可用
	 * 
	 * @return
	 */
	public static boolean isNetworkAvailable(Activity activity) {
		Context context = activity.getApplicationContext();
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {
			return false;
		} else {
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					Log.e("info", "===状态===" + networkInfo[i].getState());
					Log.e("info", "===类型===" + networkInfo[i].getTypeName());
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * 判定输入汉字
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 检测String是否全是中文
	 * @param name
	 * @return
	 */
	public static boolean checkNameChese(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = 0; i < name.length(); i++) {
			if (!isChinese(cTemp[i])) {
				res = false;
				break;
			}
		}
		return res;
	}
	
}
