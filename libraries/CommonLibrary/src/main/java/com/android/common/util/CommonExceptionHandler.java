package com.android.common.util;

import android.content.Context;
import com.android.common.domain.Version;
/**
 * Created by Hua_ on 2015/4/16.
 */
public class CommonExceptionHandler{


    /**
     * 自定义异常处理:收集错误信息&发送错误报告
     * @param ex
     * @return true:处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex,Context context) {
        final String crashReport = getCrashReport(context, ex);

        return true;
    }


    /**
     * 获取APP崩溃异常报告
     * @param ex
     * @return
     */
    private String getCrashReport(Context context, Throwable ex) {
        Version version=ViewUtil.getVersion(context);
        StringBuffer exceptionStr = new StringBuffer();
        exceptionStr.append("Version: "+version.versionName+"("+version.code+")\n");
        exceptionStr.append("Android: "+android.os.Build.VERSION.RELEASE+"("+android.os.Build.MODEL+")\n");
        exceptionStr.append("Exception: "+ex.getMessage()+"\n");
        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString()+"\n");
        }
        return exceptionStr.toString();
    }

}
