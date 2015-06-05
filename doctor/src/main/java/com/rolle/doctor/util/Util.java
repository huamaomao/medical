package com.rolle.doctor.util;

import android.content.Context;
import android.graphics.Typeface;

import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.astuetz.PagerSlidingTabStrip;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.rolle.doctor.R;
import com.rolle.doctor.domain.CityResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/20 - 15:15
 */
public final class Util {
    /****
     * 3医生，4.营养师  5用户
     * @return
     */
    public static  List<CityResponse.Item> getUserTypeList(){
        List<CityResponse.Item> list=new ArrayList<>();
        list.add(new CityResponse.Item("3","医生"));
        list.add(new CityResponse.Item("4","营养师"));
        return list;
    }

    /****
     * 3医生，4.营养师  5用户
     * @param name
     * @return
     */
    public static String getUserTitle(String name){
        if (CommonUtil.isEmpty(name))return "0";
        switch (name){
            case "住院医师":
                return "1";
            case "主治医师":
                return "2";
            case "主任医师":
                return "3";
            case "副主任医师":
                return "4";
            default:
                return "0";
        }
    }

    public static boolean errorHandle(HttpException e,IView iView){
        if (e instanceof HttpNetException) {
            HttpNetException netException = (HttpNetException) e;
            iView.msgShow("无网络,请检查网络连接....");
            return false;
        } else if (e instanceof HttpServerException) {
            HttpServerException serverException = (HttpServerException) e;
            iView.msgShow("连接服务器失败....");
            return false;
        }
        return true;
    }


    public static void initTabStrip(PagerSlidingTabStrip tabStrip,Context context){
        tabStrip.setIndicatorColor(context.getResources().getColor(R.color.title));
        tabStrip.setShouldExpand(true);
        //tabStrip.setTextColor();
        tabStrip.setDividerColor(context.getResources().getColor(R.color.write));
        tabStrip.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.font_size_17));
        tabStrip.setIndicatorHeight(ViewUtil.dip2px(context, 3f));
        tabStrip.setUnderlineHeight(ViewUtil.dip2px(context,0.5f));
        tabStrip.setTypeface(Typeface.DEFAULT, 0);
    }


    public static  List<String> getPlaintList(String date){
        long l=0;
        if (CommonUtil.notEmpty(date)){
            l=Long.decode(date);
        }
        List<String> list=new ArrayList<>();
        if (CommonUtil.isNull(date)||l==0){
            list.add(TimeUtil.getYm(System.currentTimeMillis()));
        }else {
            Calendar end=Calendar.getInstance();
            end.setTimeInMillis(System.currentTimeMillis());
            Calendar start=Calendar.getInstance();
            start.setTimeInMillis(l);
            int endYear=end.get(Calendar.YEAR);
            int endMonth=end.get(Calendar.MONTH)+1;
            for (int startYear=start.get(Calendar.YEAR),startMonth=start.get(Calendar.MONTH)+1;startYear<=endYear;){
                if (startYear>=endYear&&startMonth>endMonth){
                    break;
                }
                StringBuilder builder=new StringBuilder();
                builder.append(startYear).append("-").append(startMonth);
                list.add(builder.toString());
                startMonth++;
                if (startMonth==13){
                    startYear++;
                    startMonth=1;
                }
            }
        }
        return list;
    }
    public static void main(String[] args){
        getPlaintList("143020323100");
    }

}
