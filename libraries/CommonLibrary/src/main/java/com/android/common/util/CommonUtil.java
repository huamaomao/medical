package com.android.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具
 * Created by Hua on 2014/7/31.
 */
public final class CommonUtil {

    /************************************************数据 method ******************************************************************/

    /***
     * 判断是否可用
     * @param context
     * @return
     */
    public static boolean isNetAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isAvailable());
    }

    /****
     * 判断是否3g网络
     * @param context
     * @return
     */
    public static boolean is3rd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /****
     * 判断是否wifi网络
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /***
     * 初始化时间
     * @param startYear  开始年份>今年
     * @return
     */
    public static Integer[]  getYearList(int startYear){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int endYear=calendar.get(Calendar.YEAR);
        int length=endYear-startYear+1;
        Integer yearList[]=new Integer[length];
        for (int i=0;i<length;i++){
            yearList[i]=endYear-i;
        }
        return yearList;
    }

    /**
     *判断是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
      return (null==str||"".equals(str.trim()))?true:false;
    }
    public static boolean notEmpty(String str){
        return (null==str||"".equals(str)||"null".equals(str))?false:true;
    }

    public static  boolean not0(String str){
        return (null==str||"0".equals(str)||"".equals(str)||"null".equals(str))?false:true;
    }

    /****
     * @  根据对象的属性排序
     * @param list
     * @param fieldName
     * @param flag  ture 正序  false 倒序
     */
    public static void sort(List list,final String fieldName, final boolean flag){
        Collections.sort(list, new Comparator() {
            public int compare(Object a, Object b) {
                int ret =0;
                try {
                    Field field1 = a.getClass().getDeclaredField(fieldName);
                    Field field2 = a.getClass().getDeclaredField(fieldName);
                    field1.setAccessible(true);
                    field2.setAccessible(true);
                   if (flag){
                       ret = Integer.valueOf(field1.get(a).toString()).compareTo(Integer.valueOf(field2.get(b).toString()));
                   }else {
                       ret = Integer.valueOf(field1.get(b).toString()).compareTo(Integer.valueOf(field2.get(a).toString()));
                   }
                } catch (Exception ne) {
                    ne.printStackTrace();
                }
                return ret;
            }
        });
    }

    public static String initTextValue(String text){
        if (isEmpty(text)){
            return "0";
        }else {
            return text;
        }
    }

    public static String initTextEmpty(String text){
        if (isEmpty(text)){
            return "";
        }else {
            return text;
        }
    }


    public static boolean isNull(Object o){
        return o==null?true:false;
    }
    public static boolean notNull(Object o){
        return o==null?false:true;
    }

    public static int parseInt(String str){
        if (isEmpty(str)) return 0;
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /*****
     * 验证手机号 格式
     * @param mobiles
     * @return
     *
     */

    public static boolean isMobileNO(String mobiles){
        return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$").matcher(mobiles).matches();
    }

    /****
     * 验证邮箱格式
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        return Pattern.compile("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$").matcher(email).matches();
    }

    /****
     * 验证昵称
     * @param name
     * @return
     */
    public static boolean checkName(String name){
        String str="^([\\w]|[\\u4e00-\\u9fa5])+";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /****
     *  验证密码
     * @param pwd
     * @return
     */
    public static boolean checkPassword(String pwd){
        if (isEmpty(pwd)||pwd.length()<6||pwd.length()>15)
            return false;
         return true;
    }


    public static String initTextNull(String  str){
        if (isEmpty(str)) return "无";
        return str;
    }
    public static String initTextBlood(String  str){
        if (isEmpty(str)) return "- -";
        return str;
    }

    /****
     * 是否可以提现
     * @param money
     * @return
     */
    public static boolean isCashOutMoney(String money) {
        if (isEmpty(money)){
            return false;
        }
        try {
            double b=Double.parseDouble(money);
            return b<500?false:true;
        }catch (Exception e){
            return false;
        }

    }

    public static String formatMoney(String money){
        if (isEmpty(money)){
            money="0";
        }
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("￥#################0.00");
       return format.format(Double.parseDouble(money));
    }

}
