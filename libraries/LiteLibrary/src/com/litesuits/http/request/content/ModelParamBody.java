package com.litesuits.http.request.content;

import com.litesuits.http.data.Consts;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.request.param.CustomHttpParam;
import com.litesuits.http.request.param.HttpParam;
import com.litesuits.http.request.param.NonHttpParam;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/3/11 0011.
 */
public class ModelParamBody extends StringBody {
    public ModelParamBody(HttpParam  param) {
        super(handleString(param), Consts.MIME_TYPE_FORM_URLENCODE, Consts.DEFAULT_CHARSET);
    }

    public ModelParamBody(HttpParam  param, String mimeType, String charset) {
        super(handleString(param), Consts.MIME_TYPE_FORM_URLENCODE, charset);
    }

    private static String handleString(HttpParam  param) {
        if (param == null) return "";
        StringBuilder sb = new StringBuilder();
        // find all field.
        ArrayList<Field> fieldList = getAllDeclaredFields(param.getClass());
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(fieldList.size());
        // put all field and its value into map
        int length=fieldList.size();
        for (int i = 0 ;i < length; i++) {
            Field f = fieldList.get(i);
            f.setAccessible(true);
           try {
               String key = f.getName();
               Object value = f.get(param);
               if (value != null) {
                   sb.append(URLEncoder.encode(key))
                           .append(Consts.EQUALS)
                           .append(URLEncoder.encode(value.toString()));
                   if (i!=length-1) sb.append(Consts.AND);
               }
           }catch (Exception e){}
        }
        return sb.toString();
    }

    public static ArrayList<Field> getAllDeclaredFields(Class<?> claxx) {
        // find all field.
        ArrayList<Field> fieldList = new ArrayList<Field>();
        while (claxx != null && claxx != Object.class) {
            Field[] fs = claxx.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                if (!isInvalidField(f)) {
                    fieldList.add(f);
                }
            }
            claxx = claxx.getSuperclass();
        }
        return fieldList;
    }

    public static boolean isInvalidField(Field f) {
        return (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()))
                || (f.getAnnotation(NonHttpParam.class) != null) || f.isSynthetic();
    }

    @Override
    public String toString() {
        return "StringEntity{" +
                "string='" + string + '\'' +
                ", charset='" + charset + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
