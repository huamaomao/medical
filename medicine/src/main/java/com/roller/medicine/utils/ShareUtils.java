package com.roller.medicine.utils;

import android.app.Activity;
import android.text.Html;

import com.roller.medicine.R;
import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * @author Hua_
 * @Description:分享
 * @date 2015/7/8 - 11:39
 */
public final class ShareUtils {
    public static UMSocialService  initShare(Activity context,String title,String content,String targeUrl){
        UMSocialService mController=UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
       /* mController.setShareContent(" ,http://www.umeng.com/social");SHARE_MEDIA.QZONE,
        mController.setShareMedia(new UMImage(context, R.drawable.ic_launcher));*/
        SocializeConfig config = mController.getConfig();
        config.setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.QQ,  SHARE_MEDIA.SINA);
        //增加新浪微博支持
        config.setSsoHandler(new SinaSsoHandler());
        addCustomPlatforms(context);
        mController.setShareContent(content);
        UMImage urlImage = new UMImage(context,R.drawable.ic_launcher);
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(content);
        weixinContent.setTitle(title);
        weixinContent.setTargetUrl(targeUrl);
        weixinContent.setShareMedia(urlImage);
        mController.setShareMedia(weixinContent);
        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(content);
        circleMedia.setTitle(title);
        circleMedia.setShareMedia(urlImage);
        circleMedia.setTargetUrl(targeUrl);
        mController.setShareMedia(circleMedia);
        // 设置QQ空间分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent(content);
        qzone.setTargetUrl(targeUrl);
        qzone.setTitle(title);
        qzone.setShareMedia(urlImage);
        mController.setShareMedia(qzone);
        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent(content);
        qqShareContent.setTitle(title);
        qqShareContent.setShareMedia(urlImage);
        qqShareContent.setTargetUrl(targeUrl);
        mController.setShareMedia(qqShareContent);
        SinaShareContent sinaContent = new SinaShareContent();
        //
        sinaContent.setShareContent(Html.fromHtml(content).toString().substring(0,140));
        mController.setShareMedia(sinaContent);
        return mController;
    }

    /**
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     *       要分享标题 summary : 要分享的文字概述image  url : 图片地址 [以上三个参数至少填写一个] targetUrl
     *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     * @return
     */
    private static void addQQQZonePlatform(Activity activity) {
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, AppConstants.APPID_QQ,  AppConstants.APPID_QQ_KEY);
        //qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
        qqSsoHandler.addToSocialSDK();
        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, AppConstants.APPID_QQ,  AppConstants.APPID_QQ_KEY);
        qZoneSsoHandler.addToSocialSDK();
    }


    /**
     * 添加所有的平台</br>
     */
    private static void addCustomPlatforms(Activity activity) {
        // 添加微信平台
        addWXPlatform(activity);
        // 添加QQ平台
        addQQQZonePlatform(activity);
    }

    /**
     * @功能描述 : 添加微信平台分享
     * @return
     */
    private static void addWXPlatform(Activity activity) {
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(activity, AppConstants.APPID_WEIXIN, AppConstants.APPID_WEIXIN_KEY);
        wxHandler.addToSocialSDK();
        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(activity, AppConstants.APPID_WEIXIN, AppConstants.APPID_WEIXIN_KEY);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

}
