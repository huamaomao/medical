package com.litesuits.http.request.query;

import com.litesuits.http.data.Charsets;
import com.litesuits.http.data.Consts;
import com.litesuits.http.request.param.CustomHttpParam;
import com.litesuits.http.request.param.CustomHttpParam.CustomValueBuilder;
import com.litesuits.http.request.param.HttpParam;
import com.litesuits.http.request.param.NonHttpParam;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * abstract class for build parameter of request url.
 * 
 * @author MaTianyu
 *         2014-1-4下午5:06:37
 */
public abstract class AbstractQueryBuilder {

	protected String charSet = Charsets.UTF_8;

	public LinkedHashMap<String, String> buildPrimaryMap(HttpParam model) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
		if (model == null) { return null; }
		// find all field.
		ArrayList<Field> fieldList = getAllDeclaredFields(model.getClass());
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(fieldList.size());
		// put all field and its value into map
		for (int i = 0, size = fieldList.size(); i < size; i++) {
			Field f = fieldList.get(i);
			f.setAccessible(true);
			String key = f.getName();
			Object value = f.get(model);
			if (value != null) {
				// value is primitive
				if (isPrimitive(value)) {
					map.put(key, value.toString());
				} else if (value instanceof CustomHttpParam) {
					Method methods[] = CustomHttpParam.class.getDeclaredMethods();
					for (Method m : methods) {
						// invoke the method which has specified Annotation
						if (m.getAnnotation(CustomValueBuilder.class) != null) {
							m.setAccessible(true);
							Object v = m.invoke(value);
							if (v != null) {
								map.put(key, v.toString());
							}
							break;
						}
					}
				} else {
					CharSequence cs = buildSencondaryValue(value);
					if (cs != null) {
						map.put(key, cs.toString());
					}
				}
			}
		}
		return map;
	}

	protected abstract CharSequence buildSencondaryValue(Object model);

	/********************* utils method **************************/
	protected StringBuilder buildUriKey(StringBuilder sb, String key) throws UnsupportedEncodingException {
		if (key != null) sb.append(encode(key)).append(Consts.EQUALS);
		return sb;
	}

	public String decode(String content) throws UnsupportedEncodingException {
		return URLDecoder.decode(content, charSet);
	}

	public String encode(String content) throws UnsupportedEncodingException {
		return URLEncoder.encode(content, charSet);
	}

	//	protected boolean isInvalidField(Field f) {
	//		return (f.getAnnotation(NonHttpParam.class) != null) || f.isSynthetic();
	//	}

	protected boolean isInvalidField(Field f) {
		return (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()))
				|| (f.getAnnotation(NonHttpParam.class) != null) || f.isSynthetic();
	}

	protected boolean isPrimitive(Object value) {
		return value instanceof CharSequence || value instanceof Number || value instanceof Boolean
				|| value instanceof Character;
	}

	protected ArrayList<Field> getAllDeclaredFields(Class<?> claxx) {
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
}
