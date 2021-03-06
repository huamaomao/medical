package com.android.common.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.common.R;
import com.android.common.domain.Version;
import com.android.common.service.DownloadService;
import com.android.common.view.IView;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.litesuits.http.response.Response;

import java.util.List;

/**
 * 操作view 工具类
 */
public class ViewUtil {

    /************************************************View 控制  method ******************************************************************/

    public static void openActivity(Class<?> pClass,Activity activity,ActivityModel model){
        openActivity(pClass,null,activity,model,false);
    }

    /*****
     *启动activity 关闭之前的
     * @param pClass
     * @param activity
     */
    public static void startTopActivity(Class<? extends Activity> pClass,Activity activity){
        Intent intent=new Intent(activity,pClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void openActivity(Class<?> pClass,Activity activity){
        openActivity(pClass, null, activity, ActivityModel.ACTIVITY_MODEL_0, false);
    }
    public static void openActivity(Class<?> pClass,Activity activity,boolean flag){
        openActivity(pClass,null,activity,ActivityModel.ACTIVITY_MODEL_0,flag);
    }


    public static void openActivity(Class<?> pClass, Bundle pBundle,Activity activity,ActivityModel type) {
        openActivity(pClass,pBundle,activity,type,false);
    }

    public static void openActivity(Class<?> pClass, Bundle pBundle,Activity activity) {
        openActivity(pClass,pBundle,activity,ActivityModel.ACTIVITY_MODEL_2,false);
    }

    /**
     * 通过类名启动activity
     *
     * @param pClass
     *            要启动的类
     * @param pBundle
     *            要传递的参数
     * @param type
     *           动画
     * @param flag
     *          是否finish
     */
    public static void openActivity(Class<?> pClass, Bundle pBundle,Activity activity,ActivityModel type,boolean flag) {
        Intent intent = new Intent(activity, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        switch (type.value){
            case 0:
                break;
            case 1:
                activity.overridePendingTransition(R.anim.slide_t, R.anim.slide_out_right);
                break;
            case 2:
                activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_t);
                break;
            case 3:
                activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                break;
        }
        activity.startActivity(intent);
        if (flag){
            activity.finish();
        }
    }



    /****
     * 屏幕
     * @param context
     * @return
     */
    public static Display getDisplay(Context context){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }



    /**
     * Fragment 跳转
     * @param fm
     * @param fragmentClass
     * @param args
     * @param id
     */
    public static Fragment turnToFragment(FragmentManager fm, Class<? extends Fragment> fragmentClass,Bundle args,int id) {
        Fragment fragment = fm.findFragmentByTag(fragmentClass.getSimpleName());
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                fragment.setArguments(new Bundle());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if( args != null && !args.isEmpty() ) {
            fragment.getArguments().putAll(args);
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);
        if (CommonUtil.notNull(fm.getFragments())){
            for (Fragment f:fm.getFragments()){
                if (f.isVisible()){
                    ft.hide(f);
                    break;
                }
            }
        }
        ft.show(fragment);
        if(!fragment.isAdded()){
            ft.add(id, fragment, fragmentClass.getSimpleName()).commit();
        } else {
            ft.show(fragment).commit();
        }
        return fragment;
    }

    /*****
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /***
     * 获取版本信息 渠道
     * @param context
     * @return
     */
    public static Version getVersion(Context context){
        try {
            PackageManager packageManager = context.getPackageManager();
            // 0代表是获取版本信息
            Version version=new Version();
            PackageInfo info=packageManager.getPackageInfo(context.getPackageName(),0);
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            version.versionNo=info.versionCode;
            version.versionName=info.versionName;
            if (CommonUtil.notNull(applicationInfo)&&CommonUtil.notNull(applicationInfo.metaData)){
                version.wayNo=applicationInfo.metaData.getString("UMENG_CHANNEL");
            }
            return version;
        }catch (Exception e){

        }
        return null;
    }

    /******
     * initRecyclerView
     * @param recyclerView
     * @param context
     */
    public static void initRecyclerView(RecyclerView recyclerView,Context context,RecyclerView.Adapter adapter){
        if (recyclerView==null){
            return;
        }
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /******
     * initRecyclerView
     * @param recyclerView
     * @param context
     */
    public static void initRecyclerViewItem(RecyclerView recyclerView,Context context,RecyclerView.Adapter adapter){
        if (recyclerView==null){
            return;
        }
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL,true));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    /******
     * initRecyclerView
     * @param recyclerView
     * @param context
     */
    public static void initRecyclerViewDecoration(RecyclerView recyclerView,Context context,RecyclerView.Adapter adapter){
        if (recyclerView==null){
            return;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }



    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /****
     * 图片
     * @param context
     */
    public static  void  startPictureActivity(Activity context){
        Intent intent=new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);;
        intent.setType("image/jpeg");
        context.startActivityForResult(intent, Constants.CODE_PIC);
    }

    /****
     * 图片
     * @param context
     */
    public static  void  startCaptureActivity(Activity context){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);;
        intent.setType("image/jpeg");
        context.startActivityForResult(intent, Constants.CODE_CAPTURE);
    }


    public static void requestHandle(HttpException e,Response response,Context iView){
        IView view=(IView)iView;
        if (e==null){

        }else  if (e instanceof HttpNetException) {
            view.msgShow(e.getMessage());
        } else if (e instanceof HttpServerException) {
            view.msgShow(e.getMessage());
        }
    }
    public static void  showNotification(Context context,String content,String apkUrl){
        android.app.Notification noti=null;
        Intent myIntent = new Intent(context, DownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(Constants.APK_DOWNLOAD_URL, apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int smallIcon = context.getApplicationInfo().icon;
        noti = new NotificationCompat.Builder(context).setTicker(context.getString(R.string.newUpdateAvailable))
                .setContentTitle(context.getString(R.string.newUpdateAvailable)).setContentText(content).setSmallIcon(smallIcon)
                .setContentIntent(pendingIntent).build();
        noti.flags = android.app.Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }

    /******
     * 隐藏键盘
     * @param view
     * @param event
     */
    public static void onHideSoftInput(final Activity activity,final View view,final MotionEvent event){
        if (CommonUtil.isNull(activity))return;
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (CommonUtil.notNull(imm)&&CommonUtil.notNull(view)) {
            if (view instanceof EditText){
                int[] leftTop = { 0, 0 };
                //获取输入框当前的location位置
                view.getLocationInWindow(leftTop);
                int left = leftTop[0];
                int top = leftTop[1];
                int bottom = top + view.getHeight();
                int right = left + view.getWidth();
                if (event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom) {
                    // 点击的是输入框区域，保留点击EditText的事件
                } else {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }else {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


}
