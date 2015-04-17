package com.android.common.domain;

import java.io.Serializable;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/17 - 13:10
 */
public class ResponseMessage implements Serializable {
    /*******状态码*******/
    public String statusCode;
    /******信息代码********/
    public  String code;
    /*******message*******/
    public  String message;

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "statusCode='" + statusCode + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
