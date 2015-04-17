package com.litesuits.http.data;

import java.util.List;

/**
 * with this, we can change json handler easily.
 * alibaba fastjson can not handle private attribute that without getter method.
 * so we choice the google gson.
 *
 * @author MaTianyu
 *         2014-1-14下午11:32:32
 */
public abstract class Json {
    private static Json json;

    Json() {}

    /**
     * get default json handler
     *
     * @return Json
     */
    public static Json get() {
        if (json == null) {
            //json = new FastJson();
            json = new FastJsonImpl();
        }
        return json;
    }

    public abstract String toJson(Object src);

    public abstract <T> T toObject(String json, Class<T> claxx);

    public abstract <T> T toObject(byte[] bytes, Class<T> claxx);

    public abstract <T>List<T> toArray(byte[] bytes, Class<T> claxx);

    public abstract <T>List<T> toArray(String json, Class<T> claxx);
}
