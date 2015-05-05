package com.android.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {
	public static String compute(String pwd) {
		if (CommonUtil.isEmpty(pwd)) {
			return "";
		}
		StringBuilder builder=new StringBuilder(pwd);
		builder.append("rolle");
		try {
			byte[] byteArray = builder.toString().getBytes("UTF-8");
			byte[] md5Bytes = MessageDigest.getInstance("MD5")
					.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
            int length= md5Bytes.length;
			for (int i = 0; i <length; i++) {
				int val = md5Bytes[i] & 0xFF;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (NoSuchAlgorithmException e) {
            Log.e(MD5.class.getSimpleName(),e.getMessage());
		} catch (UnsupportedEncodingException e) {
            Log.e(MD5.class.getSimpleName(),e.getMessage());
		}
		return "";
	}
}