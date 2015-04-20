package com.rolle.doctor.util;

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
}
