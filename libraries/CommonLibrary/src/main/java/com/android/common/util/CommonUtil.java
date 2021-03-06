package com.android.common.util;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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
    private static long lastClickTime;

    /*****
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

    /********
     * 复制
     * @param msg
     */
    @TargetApi(11)
    public static void copy(Context context,String msg){
        try {
            ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (CommonUtil.notNull(cmb)){
                ClipData clip = ClipData.newPlainText("message",msg);
                cmb.setPrimaryClip(clip);
            }
        }catch (Exception e){}

    }

    /********
     * 粘贴
     * @param context
     */
    @TargetApi(11)
    public static String paserText(Context context){
        StringBuilder resultString =new StringBuilder();
        try {
            ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (CommonUtil.notNull(cmb)&&cmb.hasPrimaryClip()){
                ClipData clipData = cmb.getPrimaryClip();
                int count = clipData.getItemCount();
                for (int i = 0; i < count; ++i) {
                    ClipData.Item item = clipData.getItemAt(i);
                    resultString.append(item.coerceToText(context));
                }
            }


        }catch (Exception e){

        }
       return resultString.toString();
    }

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
        return Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$").matcher(email).matches();
    }

    /****
     * 验证昵称
     * 中英文（包括全角字符）、数字、下划线和减号 （全角及汉字算两位）,长度为4-20位
     * @param name
     * @return
     */
    public static boolean checkName(String name){
        String validateStr = "^[\\w\\-－＿[0-9]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]+$";
        Pattern p = Pattern.compile(validateStr);
        boolean rs = false;
        Matcher m = p.matcher(name);
        rs=m.matches();
        if (rs) {
            int strLenth = getStrLength(name);
            if (strLenth < 1 || strLenth > 20) {
                rs = false;
            }
        }
        return rs;
    }

    /**
     * 获取字符串的长度，对双字符（包括汉字）按两位计数
     *
     * @param value
     * @return
     */
    public static int getStrLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
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
        if (isEmpty(str)) return "无";
        return str;
    }

    public static String getSex(String  sex){
        if (isEmpty(sex)) return "无";
        if ("0".equals(0)) return "男";
        return "女";
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
            return b<1?false:true;
        }catch (Exception e){
            return false;
        }

    }
    /****
     * 是否可以提现
     * @param money
     * @return
     */
    public static boolean isCashOutMoney(String money,String outMoney) {
        if (isEmpty(money)){
            return false;
        }
        try {
            double b=Double.parseDouble(money);
            double out=Double.parseDouble(outMoney);
            return b>out?true:false;
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
    public static String numberCount(String values){
        int i=0;
        if (isEmpty(values)){
            i=0;
        }else {
            try {
                i=Integer.valueOf(values);
            }catch (Exception e){}
        }
        i++;
        return String.valueOf(i);
    }

    public static String numberCut(String values){
        int i=0;
        if (isEmpty(values)){
            i=0;
        }else {
            try {
                i=Integer.valueOf(values);
            }catch (Exception e){}
        }
        i--;
        return String.valueOf(i);
    }


   public static void hideInputMethod(Context context,View view){
       InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
       if (CommonUtil.notNull(imm)){
           imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
           imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
       }

   }

}
