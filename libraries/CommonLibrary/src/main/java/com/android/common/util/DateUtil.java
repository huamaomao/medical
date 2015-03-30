package com.android.common.util;

import java.text.SimpleDateFormat;
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

    /***
     * 格式日期     MM月dd日
     * @param date
     * @return
     */
    public static String getChineseDate(Date date){
      try {
          return  sdf_datec_format.format(date);
      }catch (Exception e){
          return "";
      }
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

    /****
     * 时间差
     * @param date
     * @param time
     * @return
     */
    public static String  timeDifference(String date,String time){
        lock.lock();
        StringBuilder builder=new StringBuilder(date);

        if (time!=null){
            builder.append(" ");
            builder.append(time);
        }
        Date date1=null;
       try{
           date1=sdf.parse(builder.toString());
           long l=date1.getTime()-System.currentTimeMillis();
           long day=l/(24*60*60*1000);
           long hour=(l/(60*60*1000)-day*24);
           long min=((l/(60*1000))-day*24*60-hour*60);
           long s=(l/1000-day*24*60*60-hour*60*60-min*60);
           if (s>0)
               min=min+1;
           builder=new StringBuilder();
           if (l<0){
               builder.append("已开赛");
           }else {
               builder.append(day).append("天").append(hour).append("小时").append(min).append("分");
           }
           return builder.toString();
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           lock.unlock();
       }
       return "";
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
            return builder.toString().substring(0,builder.length()-3);
        } catch (Exception e) {
            return date;
        }
    }
}
