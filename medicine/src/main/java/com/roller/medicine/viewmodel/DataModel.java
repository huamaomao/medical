package com.roller.medicine.viewmodel;

import android.content.Context;

import com.android.common.domain.LogDomain;
import com.android.common.domain.ResponseMessage;
import com.android.common.domain.Version;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.request.Request;
import com.litesuits.http.request.content.MultipartBody;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.content.multi.FilePart;
import com.litesuits.http.request.content.multi.StringPart;
import com.litesuits.http.request.param.HttpMethod;
import com.litesuits.http.response.Response;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.roller.medicine.info.BloodInfo;
import com.roller.medicine.info.CommentInfo;
import com.roller.medicine.info.DoctorDetialInfo;
import com.roller.medicine.info.FamilytInfo;
import com.roller.medicine.info.FocusInfo;
import com.roller.medicine.info.FriendResponseInfo;
import com.roller.medicine.info.HomeInfo;
import com.roller.medicine.info.CommentDetailInfo;
import com.roller.medicine.info.KnowledgeQuizItemInfo;
import com.roller.medicine.info.LoveInfo;
import com.roller.medicine.info.MessageChatInfo;
import com.roller.medicine.info.MyHomeInfo;
import com.roller.medicine.info.TokenInfo;
import com.roller.medicine.info.UploadPicture;
import com.roller.medicine.info.UserInfo;
import com.roller.medicine.info.UserResponseInfo;
import com.roller.medicine.utils.AppConstants;
import com.roller.medicine.utils.MD5;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * @author Hua_
 * @Description:
 * @date 2015/5/20 - 14:40
 */
public class DataModel extends ViewModel{

    private static TokenInfo token;
    public static DataBase liteOrm;
    private static Context mContext;
    public static void initLiteOrm(Context context){
        mContext=context;
        liteOrm= LiteOrm.newInstance(context, "medicine_lite.db");
    }



    public synchronized static String getImageUrl(String path){
        if (CommonUtil.isEmpty(path)) return null;
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(path);
        return url.toString();
    }

    /*****************************数据库  method***********************************************/

    public void saveMessageChat(MessageChatInfo info){
        info.friendId=getLoginUser().id;
        info.lasttime=System.currentTimeMillis();
        liteOrm.save(info);
    }
    public MessageChatInfo getMessageChat(int friendId){
        QueryBuilder builder=new QueryBuilder(MessageChatInfo.class).where(WhereBuilder.create().
                andEquals("id", friendId).andEquals("friendId", getLoginUser().id));
         List<MessageChatInfo> list=liteOrm.query(builder);
        if (CommonUtil.notNull(list)&&list.size()==1){
           return  list.get(0);
        }
        MessageChatInfo chatInfo=new MessageChatInfo();
        chatInfo.friendId=getLoginUser().id;
        chatInfo.id=friendId;
        return  chatInfo;
    }


    public  TokenInfo getToken(){
        /****做处理  查询token***/
        if (token==null||!token.isLogin()){
            token=liteOrm.queryById(1, TokenInfo.class);
        }
        return token;
    }

    public void setLoginOut(){
        TokenInfo tokenInfo=getToken();
        tokenInfo.setLoginOut();
        liteOrm.save(tokenInfo);
        TokenInfo info=getToken();
        Log.d(info);
    }

    public void setToken(TokenInfo mToken){
        this.token=mToken;
        this.token.setLogin();
        liteOrm.save(this.token);
}

    public void saveUser(UserInfo user){
        liteOrm.save(user);
        if (CommonUtil.isNull(user.doctorDetail))
            user.doctorDetail=new UserInfo.DoctorDetail();
        user.doctorDetail.id=user.id;
        if (CommonUtil.isNull(user.patientDetail))
            user.patientDetail=new UserInfo.PatientDetail();
        user.patientDetail.id=user.id;
        liteOrm.save(user.doctorDetail);
        liteOrm.save(user.patientDetail);
    }

    public UserInfo getUser(long id){
        UserInfo user=liteOrm.queryById(id, UserInfo.class);
        if (user!=null){
            UserInfo.DoctorDetail detail= liteOrm.queryById(id, UserInfo.DoctorDetail.class);
            if (CommonUtil.notNull(detail)){
                user.doctorDetail=detail;
            }
            UserInfo.PatientDetail detail1= liteOrm.queryById(id, UserInfo.PatientDetail.class);
            if (CommonUtil.notNull(detail1)){
                user.patientDetail=detail1;
            }
        }
        return  user;
    }

    /*****
     * 获取登陆的user信息
     * @return
     */
    public UserInfo getLoginUser(){
        final UserInfo user=getUser(getToken().userId);
        // 更改用户信息
        if (CommonUtil.notNull(user)&&user.updateState==UserInfo.UPDATE){
           saveDoctor(user, new SimpleResponseListener<ResponseMessage>() {
               @Override
               public void requestSuccess(ResponseMessage info, Response response) {

               }

               @Override
               public void requestError(HttpException e, ResponseMessage info) {

               }
           });
        }
        return user;
    }

    /*****
     * 获取好友列表   医生  营养师　
     * @return
     */
    public List<UserInfo> queryFriendList(String type){
        QueryBuilder builder=new QueryBuilder(UserInfo.class).where(WhereBuilder.create().
                andEquals("typeId", type));
        return liteOrm.query(builder);
    }

    public void saveFriendList(List<UserInfo> list){
        if (CommonUtil.isNull(list)) return;
        for (UserInfo user:list){
            user.friendId=getLoginUser().id;
            saveUser(user);
        }
    }

    public List<UserInfo>  seachFriendList(String str){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("%").append(str).append("%");
        QueryBuilder builder=new QueryBuilder(UserInfo.class).where(WhereBuilder.create().
                where("nickname like ?  or  userName like ?  or noteName like ?",
                        new String[]{stringBuilder.toString(), stringBuilder.toString(), stringBuilder.toString()}));
        return liteOrm.query(builder);
       // return queryFriend(ls);
    }

    public Version getVersion(){
        Version version=new Version();
        version= liteOrm.queryById(version.id,Version.class);
        return version;
    }



    /*********
     *log 日志操作
     *
     **/
    public void saveLog(LogDomain log){
        liteOrm.save(log);
    }

    public List<LogDomain> queryLogList(){
        QueryBuilder builder=new QueryBuilder(LogDomain.class);
        return liteOrm.query(builder);
    }

    /**********************************************http request method*****************************************************************/

    /*******
     * url 工具
     * @param url
     * @return
     */
    public static String requestUrl(String url){
        StringBuilder builder=new StringBuilder(UrlApi.SERVER_NAME);
        builder.append(url);
        return builder.toString();
    }

    /**
     * 发送短信
     */
    public void requestSendSms(String tel,String type,SimpleResponseListener<ResponseMessage> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("mobile", tel));
        param.add(new NameValuePair("typeId", type));
        Request request=new Request(requestUrl(UrlApi.TEL_CODE)).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request,responseService);
    }

    /**
     * 发送短信
     */
    public void requestCheckTelCode(String tel,String code,SimpleResponseListener responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("mobile", tel));
        param.add(new NameValuePair("verifycode", code));
        Request request=new Request(requestUrl(UrlApi.CHECK_TEL_CODE)).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 注册用户
     */
    public void requestRigster(String tel,String nickName,String pwd,String code,String inviteCode,SimpleResponseListener<TokenInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("mobile", tel));
        param.add(new NameValuePair("nickname", nickName));
        param.add(new NameValuePair("inviteCode", inviteCode));
        param.add(new NameValuePair("password", MD5.compute(pwd)));
        param.add(new NameValuePair("typeId", AppConstants.USER_TYPE_PATIENT + ""));
        param.add(new NameValuePair("verifycode", code));
        Request request=new Request(requestUrl(UrlApi.SAVE_USER_INFO)).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }


    /**
     * 获取个人信息
     */
    public void requestUserInfo(SimpleResponseListener<UserResponseInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        Request request=new Request(requestUrl(UrlApi.USER_INFO)).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }
    /**
     * 获取用户信息
     */
    public void requestUserInfo(String userId,SimpleResponseListener<UserResponseInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("userId", userId));
        Request request=new Request(requestUrl("/crm/user_sp/getUserByMap.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * login
     */
    public void requestLogin(String tel,String pwd,SimpleResponseListener<TokenInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("mobile", tel));
        param.add(new NameValuePair("password", MD5.compute(pwd)));
        param.add(new NameValuePair("typeId", AppConstants.USER_TYPE_PATIENT + ""));
        Request request=new Request(requestUrl(UrlApi.LOGIN)).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 重置密码
     */
    public void requestPassword(String tel,String pwd,String verifycode,SimpleResponseListener<TokenInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("mobile", tel));
        param.add(new NameValuePair("verifycode", verifycode));
        param.add(new NameValuePair("password", MD5.compute(pwd)));
        param.add(new NameValuePair("typeId", AppConstants.USER_TYPE_PATIENT + ""));
        Request request=new Request(requestUrl(UrlApi.PASSWORD)).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }


    /**
     * 获取好友列表
     */
    public void requestFriendList(final String type,final SimpleResponseListener<FriendResponseInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("typeId", type));
        Request request=new Request(requestUrl(UrlApi.FRIEND_LIST)).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, new SimpleResponseListener<FriendResponseInfo>() {
            @Override
            public void requestSuccess(FriendResponseInfo info, Response response) {
                saveFriendList(info.list);
                responseService.requestSuccess(info, response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                responseService.requestError(e, info);
            }
            @Override
            public void requestView() {
                responseService.requestView();
            }
        });
    }


    /**
     * add
     */
    public void requestAddFriend(String tel,SimpleResponseListener<ResponseMessage> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("mobile", tel));
        param.add(new NameValuePair("token", getToken().token));
        Request request=new Request(requestUrl(UrlApi.FRIEND_ADD)).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * add
     */
    public void requestAddFriendId(String id,String noteName,SimpleResponseListener responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("userId", id));
        param.add(new NameValuePair("noteName", noteName));
        param.add(new NameValuePair("token", getToken().token));
        Request request=new Request(requestUrl(UrlApi.FRIEND_ADD_ID)).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }
    /**
     * add  savePraise  点赞  医生
     */
    public void requestPraise(String repiyId,SimpleResponseListener responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("typeId", "73"));
        param.add(new NameValuePair("mainUserId", repiyId));
        param.add(new NameValuePair("token", getToken().token));
        Request request=new Request(requestUrl(UrlApi.SAVE_PRAISE)).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }
    /**
     * 给医生留言
     */
    public void requestComment(String repiyId,String content,SimpleResponseListener responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("mainUserId", repiyId));
        param.add(new NameValuePair("content", content));
        Request request=new Request(requestUrl("/crm/message_sp/getMessageListByMap.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }


    /**
     * 获取医生  3  营养师  4
     */
    public void requestDoctorList(String type,final SimpleResponseListener<FriendResponseInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("typeId", type));
        Request request=new Request(requestUrl("/crm/doctor_sp/getDoctorList.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, new SimpleResponseListener<FriendResponseInfo>() {
            @Override
            public void requestSuccess(FriendResponseInfo info, Response response) {
                saveFriendList(info.list);
                responseService.requestSuccess(info, response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                responseService.requestError(e, info);
            }
            @Override
            public void requestView() {
                responseService.requestView();
            }
        });
    }

    /**
     * 获取医生
     */
    public void requestPatientList(final SimpleResponseListener<FriendResponseInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        Request request=new Request(requestUrl("/crm/patient_sp/getPatientList.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, new SimpleResponseListener<FriendResponseInfo>() {
            @Override
            public void requestSuccess(FriendResponseInfo info, Response response) {
                saveFriendList(info.list);
                responseService.requestSuccess(info, response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                responseService.requestError(e, info);
            }

            @Override
            public void requestView() {
                responseService.requestView();
            }

        });
    }

    /**
     * 获取医生详细
     */
    public void requestDoctorDetail(String userId,final SimpleResponseListener<DoctorDetialInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("userId", userId));
        Request request=new Request(requestUrl("/crm/doctor_sp/getDoctorDetail.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }


    /**
     * 获取首页数据
     */
    public FutureTask<String> requestHomeData(String date,final SimpleResponseListener<HomeInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("datetime", date));
        Request request=new Request(requestUrl("/crm/patient_sp/getPatientHistory.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
       return execute(request, responseService);
    }


    /**
    * 保存血糖值
    */
    public void requestSaveBlood(String date,String dateType,String value,String userId,String pharmacy,String sport,
                        String  mood,final SimpleResponseListener<HomeInfo> responseService) {
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("value", value));
        param.add(new NameValuePair("timeBucket", dateType));
        param.add(new NameValuePair("time", date));
        param.add(new NameValuePair("createUserId", userId));
        param.add(new NameValuePair("pharmacy", pharmacy));
        param.add(new NameValuePair("sport", sport));
        param.add(new NameValuePair("mood", mood));
        Request request=new Request(requestUrl("/crm/glycemic_sp/saveGlycemicRecord.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /******
     * 血糖记录
     */
    public void requestBloodList(String date,final SimpleResponseListener<BloodInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("userId", getLoginUser().id + ""));
        param.add(new NameValuePair("startTime", date));
        Request request=new Request(requestUrl("/crm/glycemic_sp/getGlycemicDataByMap.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /************************ no zuo no die **********************************************/

    /**
     * 论坛
     * @param responseService
     */
    public void getPostListByMap(int pageNum,SimpleResponseListener<KnowledgeQuizItemInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("pageNum", pageNum + ""));
        Request request=new Request(requestUrl("/crm/post_sp/getPostListByMap.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 我的关注 我的粉丝
     * @param responseService
     * @param typeId  1 关注我的   2 我的关注
     * @throws Exception
     */
    public void getRelationListByMap(String userId,String typeId,SimpleResponseListener<FocusInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("typeId", typeId));
        param.add(new NameValuePair("userId", userId));
        Request request=new Request(requestUrl("/crm/relation_sp/getRelationListByMap.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 喜欢
     * @param responseService
     * @throws Exception
     */
    public void getPraiseListByMap(String userId,SimpleResponseListener<LoveInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("userId", userId));
        Request request=new Request(requestUrl("/crm/praise_sp/getPraiseListByMap.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }




    /**
     * 保存个人信息
     */
    public void saveDoctor(UserInfo user,SimpleResponseListener<ResponseMessage> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token",getToken().token));
        param.add(new NameValuePair("typeId",user.typeId));
        param.add(new NameValuePair("nickname", user.nickname));
        param.add(new NameValuePair("email",user.email));
        param.add(new NameValuePair("tel",user.tel));
        param.add(new NameValuePair("sex",user.sex));
        param.add(new NameValuePair("age",user.age));
        param.add(new NameValuePair("photoId",user.photoId));
        param.add(new NameValuePair("intro",user.intro));
        param.add(new NameValuePair("address",user.address));
        param.add(new NameValuePair("regionId",user.regionId));
        param.add(new NameValuePair("disease",user.patientDetail.disease));
        param.add(new NameValuePair("height",user.patientDetail.height));
        param.add(new NameValuePair("weight",user.patientDetail.weight));

        param.add(new NameValuePair("userName",user.userName));
        param.add(new NameValuePair("token",user.token));
        param.add(new NameValuePair("birthday", user.birthday));
        Request request=new Request(requestUrl("/crm/user_sp/saveDoctor.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request,responseService);
    }

    /**
     * 得到我的模块数据
     * @param responseService
     * @throws Exception
     */
    public void requestUserHome(String userId,SimpleResponseListener<MyHomeInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("userId", userId));
        Request request=new Request(requestUrl("/crm/user_sp/getUserHome.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }


    /**
     * 点赞
     * @param responseService
     * @throws Exception
     */
    public void savePraise(String postId,String repiyId,String typeId,String mainUserId,SimpleResponseListener<ResponseMessage> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("postId", postId));
        param.add(new NameValuePair("repiyId", repiyId));
        param.add(new NameValuePair("typeId", typeId));
        param.add(new NameValuePair("mainUserId", mainUserId));
        Request request=new Request(requestUrl("/crm/praise_sp/savePraise.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 取消赞
     * @param responseService
     */
    public void deletePraise(String id,String typeId,String replyId,SimpleResponseListener<ResponseMessage> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("id", id));
        param.add(new NameValuePair("typeId", typeId));
        param.add(new NameValuePair("replyId", replyId));
        Request request=new Request(requestUrl("/crm/praise_sp/deletePraise.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 帖子详情
     */
    public void getPostByMap(String postId,String boardId,SimpleResponseListener<CommentDetailInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("postId", postId));
        param.add(new NameValuePair("boardId", boardId));
        Request request=new Request(requestUrl("/crm/post_sp/getPostByMap.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 发表评论
     */
    public void saveReply(String postId,String boardId,String content,String byReplyUserId,SimpleResponseListener<ResponseMessage> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("postId", postId));
        param.add(new NameValuePair("boardId", boardId));
        param.add(new NameValuePair("content", content));
        param.add(new NameValuePair("byReplyUserId", byReplyUserId));
        Request request=new Request(requestUrl("/crm/reply_sp/saveReply.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 举报内容
     */
    public void informReply( String postId,SimpleResponseListener<ResponseMessage> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("postId", postId));
        Request request=new Request(requestUrl("/crm/reply_sp/informReply.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     *  删除评论
     */
    public void deleteReply(String postId,SimpleResponseListener<ResponseMessage> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("postId", postId));
        Request request=new Request(requestUrl("/crm/praise_sp/deletePraise.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 我的评论
     */
    public void getPostReplyListByMap(int page,String userId,SimpleResponseListener<CommentInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("userId", userId));
        param.add(new NameValuePair("pageNum", page+""));
        Request request=new Request(requestUrl("/crm/reply_sp/getReplyListByMap.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 新建家庭成员
     */
    public void saveFamilyGroup(String mobile,String sex,String birthday,String appellation,SimpleResponseListener<ResponseMessage> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("mobile", mobile));
        param.add(new NameValuePair("sex", sex));
        param.add(new NameValuePair("birthday", birthday));
        param.add(new NameValuePair("appellation", appellation));
        Request request=new Request(requestUrl("/crm/family_sp/saveFamilyGroup.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 获取成员列表
     */
    public void getFamilyListByMap(SimpleResponseListener<FamilytInfo> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        Request request=new Request(requestUrl("/crm/family_sp/getFamilyListByMap.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 删除帐号
     */
    public void deleteFamilyGroup(String groupId, String familyGroupId,SimpleResponseListener<ResponseMessage> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("groupId", groupId));
        param.add(new NameValuePair("familyGroupId", familyGroupId));
        Request request=new Request(requestUrl("/crm/family_sp/deleteFamilyGroup.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }

    /**
     * 更改户主
     */
    public void updateFamilyGroup(String groupId, String familyGroupId,SimpleResponseListener<ResponseMessage> responseService){
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("groupId", groupId));
        param.add(new NameValuePair("familyGroupId", familyGroupId));
        Request request=new Request(requestUrl("/crm/family_sp/updateFamilyGroup.json")).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, responseService);
    }


    /******
     * 上传图片
     * @param typeId
     * @param path
     * @param listener
     */
    public void  uploadPicture(String typeId,String path, final SimpleResponseListener<UploadPicture> listener){
        MultipartBody body=new MultipartBody();
        body.addPart(new StringPart("token", getToken().token));
        body.addPart(new StringPart("typeId", typeId));
        body.addPart(new FilePart("file", new File(path), "image/jpeg"));
        Request request=new Request(requestUrl("/crm/image_sp/uploadImage.json")).setHttpBody(body).setMethod(HttpMethod.Post);
        execute(request, listener);
    }


    /******
     * 意见反馈
     * @param listener
     */
    public void requestSaveFeedback(String content,final SimpleResponseListener<ResponseMessage> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/feedback_sp/saveFeedback.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("content", content));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request,listener);
    }
    /******
     * 获取版本信息
     * @param listener
     *  医生版	116
     *  用户版	117
     */
    public void requestVersion(final SimpleResponseListener<Version> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/version_sp/getVersionByMap.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("typeId", "117"));
        param.add(new NameValuePair("wayNo", "rolle"));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new SimpleResponseListener<Version>() {
            @Override
            public void requestSuccess(Version info, Response response) {
                liteOrm.save(info);
                listener.requestSuccess(info,response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
               Version version =getVersion();
                if (CommonUtil.notNull(version)){
                    listener.requestSuccess(version,null);
                }else {
                    listener.requestError(e,info);
                }
            }

            @Override
            public void requestView() {
                listener.requestView();
            }
        });
    }

    /******
     * 获取版本信息
     * @param listener
     *  医生版	116
     *  用户版	117
     */
    public void requestCommitLog(final SimpleResponseListener<ResponseMessage> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/version_sp /getVersionByMap.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("typeId", "117"));
        param.add(new NameValuePair("wayNo", "117"));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request,listener);
    }


}
