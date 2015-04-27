package com.rolle.doctor.util;

import com.android.common.util.CommonUtil;
import com.android.common.view.IView;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/20 - 15:15
 */
public final class Util {
    /****
     * 3医生，4.营养师  5用户
     * @param name
     * @return
     */
    public static String getUserType(String name){
        if ("医生".equals(name)){
            return "3";
        }
        if ("营养师".equals(name)){
            return "4";
        }
        return "";

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

}
