package com.android.common.domain;

/**
 * @author Hua_
 * @Description:
 * @date 2015/6/5 - 11:07
 */
public class LogDomain {
    public long id;
    /*******获取机型名称*******/
    public String model;
    /*******sdk version *******/
    public String sdk;
    /*******RELEASE *******/
    public String release;
    public String date;
    public String excetion;
    public int versionCode;
    public String versionName;
    public String channel;
    public String  stackTrace;
    /*****级别****/
    public int level;


    @Override
    public String toString() {
        return "LogDomain{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", sdk='" + sdk + '\'' +
                ", release='" + release + '\'' +
                ", date='" + date + '\'' +
                ", excetion='" + excetion + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", channel='" + channel + '\'' +
                ", stackTrace='" + stackTrace + '\'' +
                ", level=" + level +
                '}';
    }
}
