package com.android.common.util;

import android.content.Context;
import android.os.Build;

import com.android.common.domain.LogDomain;
import com.android.common.domain.Version;
import com.litesuits.orm.LiteOrm;

import java.util.Date;

/**
 * Created by Hua_ on 2015/4/16.
 */
public class CommonExceptionHandler{
        private LiteOrm liteOrm;

    /**
     * 自定义异常处理:收集错误信息&发送错误报告
     * @param ex
     * @return true:处理了该异常信息;否则返回false
     */
    private LogDomain handleException(Throwable ex,Context context) {
         return getCrashReport(context, ex);
    }


    /**
     * 获取APP崩溃异常报告
     * @param ex
     * @return
     */
    private LogDomain getCrashReport(Context context, Throwable ex) {
        LogDomain logDomain=new LogDomain();
        Version version=ViewUtil.getVersion(context);
        logDomain.versionCode=version.versionCode;
        logDomain.versionName=version.versionName;
        logDomain.model=android.os.Build.MODEL;
        logDomain.release= Build.VERSION.RELEASE;
        logDomain.excetion= ex.getMessage();
        logDomain.date=DateUtil.sdf_date.format(new Date());
        StringBuffer exceptionStr = new StringBuffer();
        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString()+"\n");
        }
        logDomain.stackTrace=exceptionStr.toString();
        return logDomain;
    }




}
