package com.android.common.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 *
 */
public class DateUtil {

     static final   Lock lock = new ReentrantLock();
    public static final String DATEC_FORMAT = "MM月dd日";
    public static SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf_datec_format = new SimpleDateFormat(DATEC_FORMAT);
    public static SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm:ss");



    public static  String formatChatMessage(String date){
        if (CommonUtil.isEmpty(date)) return "";
       try {
           Date mdate=sdf.parse(date);
           Calendar calendar=Calendar.getInstance();
           calendar.setTime(mdate);
           int year=calendar.get(Calendar.YEAR);
           int month=calendar.get(Calendar.MONTH);
           int day=calendar.get(Calendar.DAY_OF_MONTH);
           int week=calendar.get(Calendar.WEEK_OF_MONTH);
           int hours=calendar.get(Calendar.HOUR_OF_DAY);
           int min=calendar.get(Calendar.MINUTE);
           Calendar ncalendar=Calendar.getInstance();
           int nyear=ncalendar.get(Calendar.YEAR);
           int nmonth=ncalendar.get(Calendar.MONTH);
           int nday=ncalendar.get(Calendar.DAY_OF_MONTH);
           int nweek=ncalendar.get(Calendar.WEEK_OF_MONTH);
           int nhours=ncalendar.get(Calendar.HOUR_OF_DAY);
           int nmin=ncalendar.get(Calendar.MINUTE);
           StringBuilder builder=new StringBuilder();
           builder.append(year<nyear?year:"");

           return builder.toString();
       }catch (Exception e){
           e.printStackTrace();
           return "";
       }
    }

    public static  void  main(String str[]){
        System.out.println(formatChatMessage("2014-04-12 22:22:22"));
    }


    /***
     * this Date   MM-dd  hh:mm
     * @return
     */
    public  static String getNowDate(){
        return mDateFormat.format(new Date());
    }
    /***
     * this Date   yyyy-MM-dd  hh:mm
     * @return
     */
    public  static String getDate(){
        return sdf_date.format(new Date());
    }


  public static  String getTime(){
      return  sdf_time.format(new Date());
  }

   public static  Date getDate(String date,String  time){
       StringBuilder builder=new StringBuilder(date);
       builder.append(" ");
       builder.append(time);
       try {
           return  sdf.parse(builder.toString());
       }catch (Exception e){
       }
       return new Date();
   }

    public static  String  getStartTime(String date,String  time){
        StringBuilder builder=new StringBuilder();
        try {
           builder.append(date.substring(5));
            builder.append(" ");
            builder.append(time);
            return builder.toString().substring(0, builder.length() - 3);
        } catch (Exception e) {
            return date;
        }
    }

    public static String formatYMD(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }
}
