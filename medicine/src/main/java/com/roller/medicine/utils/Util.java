package com.roller.medicine.utils;

import com.android.common.util.CommonUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/20 - 15:15
 */
public final class Util {
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


}
