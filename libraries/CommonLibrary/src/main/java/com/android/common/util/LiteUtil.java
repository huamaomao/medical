package com.android.common.util;

import android.content.Context;

import com.litesuits.http.LiteHttpClient;
import com.litesuits.http.async.HttpAsyncExecutor;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/17 - 17:48
 */
public class LiteUtil {
    private static LiteHttpClient client;
    private static HttpAsyncExecutor asyncExecutor;

    public static void initLite(Context context){
        client= LiteHttpClient.newApacheHttpClient(context);
        client.config(context,true,true,false,true);
        asyncExecutor=HttpAsyncExecutor.newInstance(client);
    }

    public static HttpAsyncExecutor  getInstance(){
         return asyncExecutor;
    }


}
