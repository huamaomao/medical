package com.rolle.doctor.util;

import android.app.Activity;

import java.util.Map;
import java.util.Stack;

public final class ActivityManager {
    private static Stack<Activity> activityStack;
   //  private static Map<String,Stack<Activity>>

    private static ActivityManager instance; 
    private ActivityManager() { 
    } 
    public static ActivityManager getScreenManager() { 
        if (instance == null) { 
            instance = new ActivityManager(); 
        } 
        return instance; 
    }



    //退出栈顶Activity 
    public void popActivity(Activity activity) {
        if (activity != null) { 
           //在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作 
            activity.finish(); 
            activityStack.remove(activity); 
            activity = null; 
        } 
    } 
    //获得当前栈顶Activity 
    public Activity currentActivity() { 
        Activity activity = null; 
       if(!activityStack.empty()) 
         activity= activityStack.lastElement(); 
        return activity; 
    } 
    //将当前Activity推入栈中 
    public void pushActivity(Activity activity) { 
        if (activityStack == null) { 
            activityStack = new Stack<Activity>(); 
        } 
        activityStack.add(activity); 
    } 
    //退出栈中所有Activity 
    public void popAllActivityExceptOne(Class cls) { 
        while (true) { 
            Activity activity = currentActivity(); 
            if (activity == null) { 
                break; 
            } 
            if (activity.getClass().equals(cls)) { 
                break; 
            } 
            popActivity(activity); 
        } 
    } 
}