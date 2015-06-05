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



    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LogDomain{");
        sb.append("id=").append(id);
        sb.append(", model='").append(model).append('\'');
        sb.append(", sdk='").append(sdk).append('\'');
        sb.append(", release='").append(release).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", excetion='").append(excetion).append('\'');
        sb.append(", versionCode=").append(versionCode);
        sb.append(", versionName='").append(versionName).append('\'');
        sb.append(", channel='").append(channel).append('\'');
        sb.append(", stackTrace='").append(stackTrace).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
