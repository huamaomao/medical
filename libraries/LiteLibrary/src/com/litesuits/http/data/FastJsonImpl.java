package com.litesuits.http.data;


import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * google gson
 * @author hua
 *         2014-2-26下午11:13:39
 */
public class FastJsonImpl extends Json {

	@Override
	public String toJson(Object src) {
 		return JSON.toJSONString(src);
	}

	@Override
	public <T> T toObject(String json, Class<T> claxx) {
        return JSON.parseObject(json,claxx);
	}

	@Override
	public <T> T toObject(byte[] bytes, Class<T> claxx) {
		return JSON.parseObject(new String(bytes), claxx);
	}

    @Override
    public <T> List<T> toArray(byte[] bytes, Class<T> claxx) {
        return JSON.parseArray(new String(bytes),claxx);
    }

    @Override
    public <T> List<T> toArray(String json, Class<T> claxx) {
        return JSON.parseArray(json,claxx);
    }

}
