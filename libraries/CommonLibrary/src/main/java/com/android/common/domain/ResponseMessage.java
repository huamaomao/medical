package com.android.common.domain;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/17 - 13:10
 */
public class ResponseMessage implements ParameterizedType{
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

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[0];
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    @Override
    public Type getRawType() {
        return ResponseMessage.class;
    }
}
