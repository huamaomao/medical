package com.roller.medicine.httpservice;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Map;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.common.util.CommonUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.roller.medicine.info.BaseInfo;
import com.roller.medicine.myinterface.IResponseListener;
import com.roller.medicine.myinterface.SimpleResponseListener;

public class DataHttpService {

	private static DataHttpService dataHttpService;
	public static DataHttpService getInstance(){
		if(dataHttpService==null){
			dataHttpService = new DataHttpService();
		}
		return dataHttpService;
	}
	
	/*
	private PreferencesCookieStore preferencesCookieStore;
	public void configCookie(){
		preferencesCookieStore = new PreferencesCookieStore(BaseApplication.getInstance());
        BasicClientCookie cookie = new BasicClientCookie("test", "hello");
        cookie.setDomain("180.153.108.243");
        cookie.setPath("/");
        preferencesCookieStore.addCookie(cookie);
	}
	*/

/*
Type type = ((ParameterizedType) UIHandler.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	if (type instanceof Class<?>) {
		return res.getObject((Class<T>) type);
	} else if (type instanceof ParameterizedType) {
		return res.getObject((Class<T>) ((ParameterizedType) type).getRawType());
	}
	*/


	public <T extends BaseInfo>void requestByPost(final SimpleResponseListener<T> responseService,final String url, Map<String, Object> mapParams, final Object tag)
			throws Exception {
		StringBuffer data = new StringBuffer();// 创建一个StringBuffer来拼接参数
		final String result = "";// 接收传回来的值
		for (Map.Entry<String, Object> entry : mapParams.entrySet()) {// 拼接参数
			if(entry == null || entry.getValue() == null) continue;
			data.append(entry.getKey()).append("=");
			data.append(URLEncoder.encode(entry.getValue().toString(), "utf-8"));
			data.append("&");
			/*************************仅做测试使用!***************************/
		}
		if (mapParams.size() > 0) {
			if(data.length() > 0){
				data.deleteCharAt(data.length() - 1);
			}
		}
		Log.w("info", url+"?"+data.toString());
		RequestParams params = new RequestParams();
		if(mapParams!=null && !mapParams.isEmpty())
			for (Map.Entry<String, Object> entry : mapParams.entrySet()){
				if(entry == null || entry.getValue() == null) continue;
				params.addBodyParameter(entry.getKey(), URLEncoder.encode(entry.getValue().toString()));
			}
		final HttpUtils http = new HttpUtils();
		http.configSoTimeout(20000);
		//http.seet
		http.send(HttpRequest.HttpMethod.POST,
				url,
				params,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {

					}
					@Override
					public void onLoading(long total, long current, boolean isUploading) {

					}
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						com.android.common.util.Log.d(responseInfo);

						if (CommonUtil.notNull(responseInfo)&&"application/json;charset=UTF-8".equals(responseInfo.contentType.getValue())){
							try {
								Type type = ((ParameterizedType) responseService.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
								T t=null;
								if (type instanceof Class<?>) {
									t=JSON.parseObject(responseInfo.result,(Class<T>) type);
								} else if (type instanceof ParameterizedType) {
									t=JSON.parseObject(responseInfo.result,(Class<T>) ((ParameterizedType) type).getRawType());
								}
								if (CommonUtil.isNull(t)){
									responseService.requestError(null,null);
								}else if ("200".equals(t.statusCode)){
									responseService.requestSuccess(t,responseInfo.result);
								}else{
									responseService.requestError(null, t);
								}
							}catch (Exception e){
								e.printStackTrace();
							}
						}else {
							responseService.requestError(new HttpException("网络异常，无法请求..."), null);
						}

						responseService.requestView();

					}
					@Override
					public void onFailure(HttpException error, String msg) {
						responseService.requestError(error,null);
						responseService.requestView();
					}
				});
	}



	public void requestByPost(final IResponseListener responseService,final String url, Map<String, Object> mapParams, final Object tag)
			throws Exception {
		StringBuffer data = new StringBuffer();// 创建一个StringBuffer来拼接参数
		String result = "";// 接收传回来的值
		for (Map.Entry<String, Object> entry : mapParams.entrySet()) {// 拼接参数
			if(entry == null || entry.getValue() == null) continue;
			data.append(entry.getKey()).append("=");
			data.append(URLEncoder.encode(entry.getValue().toString(), "utf-8"));
			data.append("&");
			/*************************仅做测试使用!***************************/
		}
		if (mapParams.size() > 0) {
			if(data.length() > 0){
				data.deleteCharAt(data.length() - 1);
			}
		}
//		LogUtils.i(url+"?"+data.toString());
//		LogUtils.i(url+"?"+testdata.toString());
		Log.e("info", url+"?"+data.toString());

		RequestParams params = new RequestParams();
		if(mapParams!=null && !mapParams.isEmpty())
		for (Map.Entry<String, Object> entry : mapParams.entrySet()){
			if(entry == null || entry.getValue() == null) continue;
			params.addBodyParameter(entry.getKey(), URLEncoder.encode(entry.getValue().toString()));
		}
        final HttpUtils http = new HttpUtils();
        http.configSoTimeout(20000);
//        http.getParams().setParameter("http.socket.timeout", new Integer(30000)); 
//        http.getHttpClient().getConnectionManager().shutdown();
        http.send(HttpRequest.HttpMethod.POST,
        		url, 
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                    	responseService.onStart(url, tag);
                    }
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    	responseService.onLoading(http, url, total, current, isUploading, tag);
                    }
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
						responseService.onSuccess(url, responseInfo.result, 0, tag);
                    }
                    @Override
                    public void onFailure(HttpException error, String msg) {
                    	responseService.onFailure(url, error, msg, tag);
                    }
                });
    }
	
	
	
	
	public void requestByGet (final IResponseListener responseService,final String url, Map<String, Object> mapParams, final Object tag)
			throws Exception {
        final HttpUtils http = new HttpUtils();
        StringBuffer data = new StringBuffer();
        data.append("?");
        for (Map.Entry<String, Object> entry : mapParams.entrySet()) {// 拼接参数
        	data.append(entry.getKey()).append("=");
			data.append(URLEncoder.encode(entry.getValue().toString()));
			data.append("&");
		}
		if (mapParams.size() > 0) {
			data.deleteCharAt(data.length() - 1);
		}
		data = new StringBuffer();
        http.configCurrentHttpCacheExpiry(1000 * 10);
        http.send(HttpRequest.HttpMethod.GET,
    		url+data,
            new RequestCallBack<String>() {
	            @Override
	            public void onStart() {
	            	responseService.onStart(url, tag);
	            }
	            @Override
	            public void onLoading(long total, long current, boolean isUploading) {
	            	responseService.onLoading(http, url, total, current, isUploading, tag);
	            }
	            @Override
	            public void onSuccess(ResponseInfo<String> responseInfo) {
	            	responseService.onSuccess(url, responseInfo.result, 0, tag);
	            }
	            @Override
	            public void onFailure(HttpException error, String msg) {
	            	responseService.onFailure(url, error, msg, tag);
	            }
    		});
    }
	
	
	/**
	 * 
	 * @param file
	 */
	public void uploadMethod(final IResponseListener responseService,final File file, final String uploadUrl, final Object tag)
			throws Exception {
		uploadMethod(responseService, new File[]{file}, uploadUrl, tag);
	}
	public void uploadMethod(final IResponseListener responseService,final File[] file, final String uploadUrl, final Object tag)
			throws Exception {
		RequestParams params = new RequestParams("utf-8");
		for (File singleFile : file) {
			params.addBodyParameter("file", singleFile);
		}
		
		final HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadUrl, params, new RequestCallBack<String>() {  
            @Override  
            public void onStart() {  
            	responseService.onStart(uploadUrl, tag);
            }  
            @Override  
            public void onLoading(long total, long current,boolean isUploading) {
            	responseService.onLoading(http, uploadUrl, total, current, isUploading, tag);
            }  
            @Override  
            public void onSuccess(ResponseInfo<String> responseInfo) {
				responseService.onSuccess(uploadUrl, responseInfo.result, 0, tag);
			}
		@Override
            public void onFailure(HttpException error, String msg) {  
            	responseService.onFailure(uploadUrl, error, msg, tag);
            }  
        });  
    }  
	
	
}
