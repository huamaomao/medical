package com.tryhard.workpai.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

public class TimeUtil {
	public TimeUtil(Context paramContext)
	{

	}
	@SuppressLint("SimpleDateFormat")
	public static String customTimeString(String time, String format) {
		String date = time;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.format(time);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return date;
	}

	@SuppressLint("SimpleDateFormat")
	public static String currentLocalLoginTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(System.currentTimeMillis());
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String currentLocalTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		return sdf.format(System.currentTimeMillis());
	}

	@SuppressLint("SimpleDateFormat")
	public static String dateToMessageTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		return sdf.format(new Date(time));
	}

	@SuppressLint("SimpleDateFormat")
	public static String dateToTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		return sdf.format(new Date(time));
	}

	@SuppressLint("SimpleDateFormat")
	public static String toLocalTimeString(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		return sdf.format(System.currentTimeMillis());
	}

	@SuppressLint("SimpleDateFormat")
	public static String currentLocalDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(System.currentTimeMillis());
	}

	public String getCurrenMinute() {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("mm", localCalendar).toString();
	}

	@SuppressLint("SimpleDateFormat")
	public static String currentSplitTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(System.currentTimeMillis());
	}

	@SuppressLint("SimpleDateFormat")
	public static String currentSplitTimeString(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datetime;
		try {
			datetime = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			datetime = new Date();
		}
		sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(datetime);
	}

	public String getCurrentDay() {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("dd", localCalendar).toString();
	}

	public String getCurrentHour() {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("kk", localCalendar).toString();
	}

	public String getCurrentMonth() {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("MM", localCalendar).toString();
	}

	public String getCurrentSecond() {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("ss", localCalendar).toString();
	}

	public String getCurrentYear() {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("yyyy", localCalendar).toString();
	}

	public String getDay(Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("dd", localCalendar).toString();
	}

	public String getHour(Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("kk", localCalendar).toString();
	}

	public String getMinute(Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("mm", localCalendar).toString();
	}

	public String getMonth(Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("MM", localCalendar).toString();
	}

	public String getSecond(Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("ss", localCalendar).toString();
	}

	public String getTime(String paramString) {
		Calendar localCalendar = Calendar.getInstance();
		long l = System.currentTimeMillis();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format(paramString, localCalendar).toString();
	}

	public String getTime(String paramString, Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format(paramString, localCalendar).toString();
	}

	public String getYear(Long paramLong) {
		Calendar localCalendar = Calendar.getInstance();
		long l = paramLong.longValue();
		localCalendar.setTimeInMillis(l);
		return DateFormat.format("yyyy", localCalendar).toString();
	}

	@SuppressLint("SimpleDateFormat")
	public static int compareTime(String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			long start = sdf.parse(startTime).getTime();
			long end = sdf.parse(endTime).getTime();
			long now = System.currentTimeMillis();
			if (start <= now && now < end) {
				return 0;
			} else if (start > now) {
				return -1;
			} else if (now >= end) {
				return 1;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -2;
	}

	/**
	 * 对比两个时间差是否大于5分钟
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean needShowTime(long time1, long time2) {
		int time = (int) (((time1 - time2)) / 60) / 1000;
		if (time >= 5) {
			return true;
		} else {
			return false;
		}
	}

	public static String getVoiceTime(int time) {
		int second = time / 1000;
		if (second <= 0) {
			second = 1;
		}
		return String.valueOf(second) + "\"";
	}

	public static int getVoiceTimeInt(int time) {
		int second = time / 1000;
		if (second <= 0) {
			second = 1;
		}
		return second;
	}

	public static String getDiffTime(String createTime) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {

			Date d1 = new Date(System.currentTimeMillis());// 你也可以获取当前时间
			Date d2 = df.parse(createTime);

			long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别

			long days = diff / (1000 * 60 * 60 * 24);

			long hours = (diff - days * (1000 * 60 * 60 * 24))
					/ (1000 * 60 * 60);

			long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
					* (1000 * 60 * 60))
					/ (1000 * 60);

			if (days > 7) {
				return "一个星期前";
			}
			
			if (days > 0) {
				return ((int) days)+"天前";
			}
			
			if (hours > 0) {
				return ((int) hours)+"小时前";
			}
			
			if (minutes > 0) {
				return ((int) minutes)+"分钟前";
			}

			Log.e("info", "" + days + "天" + hours + "小时" + minutes + "分");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
	
	public static String getDiffTime(long createTime) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			
			Date d1 = new Date(System.currentTimeMillis());// 你也可以获取当前时间
//			Date d2 = df.parse(createTime);
			
			long diff = d1.getTime() - createTime;// 这样得到的差值是微秒级别
			
			long days = diff / (1000 * 60 * 60 * 24);
			
			long hours = (diff - days * (1000 * 60 * 60 * 24))
					/ (1000 * 60 * 60);
			
			long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
					* (1000 * 60 * 60))
					/ (1000 * 60);
			
			if (days > 14) {
				return dateToTime(createTime);
			}
			
			if (days > 7) {
				return "一个星期前";
			}
			
			if (days > 0) {
				return ((int) days)+"天前";
			}
			
			if (hours > 0) {
				return ((int) hours)+"小时前";
			}
			
			if (minutes > 0) {
				return ((int) minutes)+"分钟前";
			}
			
			Log.e("info", "" + days + "天" + hours + "小时" + minutes + "分");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
}

/*
 * Location: C:\Documents and Settings\Administrator\桌面\classes_dex2jar.jar
 * Qualified Name: org.dns.framework.util.TimeUtil JD-Core Version: 0.6.0
 */