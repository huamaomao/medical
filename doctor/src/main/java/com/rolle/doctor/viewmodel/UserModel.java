package com.rolle.doctor.viewmodel;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.android.common.domain.ResponseMessage;
import com.android.common.domain.Version;
import com.android.common.util.CommonUtil;
import com.android.common.util.Log;
import com.android.common.util.MD5;
import com.android.common.viewmodel.SimpleResponseListener;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.request.Request;
import com.litesuits.http.request.content.JsonBody;
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
import com.rolle.doctor.domain.BloodResponse;
import com.rolle.doctor.domain.ContactBean;
import com.rolle.doctor.domain.ContactListResponse;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.InviteCodeResponse;
import com.rolle.doctor.domain.Recommended;
import com.rolle.doctor.domain.Token;
import com.rolle.doctor.domain.UploadPicture;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.domain.UserResponse;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.domain.WalletBill;
import com.rolle.doctor.event.BaseEvent;
import com.rolle.doctor.service.RequestService;
import com.rolle.doctor.util.AppConstants;
import com.rolle.doctor.util.RequestApi;
import com.rolle.doctor.util.TimeUtil;
import com.rolle.doctor.util.UrlApi;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/4/21 0021.
 */
public class UserModel  extends ViewModel {
    private static Token token;
    public static DataBase db;
    private  Context mContext;
    public UserModel(Context context){
        this.mContext=context;
        instanceDataBase(context);
    }

    public static synchronized void instanceDataBase(Context context){
        if (CommonUtil.isNull(db)){
            db=LiteOrm.newCascadeInstance(context, AppConstants.DB_NAME);
        }
    }

    public Version getVersion(){
        Version version=new Version();
        version= db.queryById(version.id,Version.class);
        return version;
    }


    @Override
    protected  void loginOut() {
        if (!token.isLogin()) return;
        setLoginOut();
        EventBus.getDefault().post(new BaseEvent(BaseEvent.EV_TOKEN_OUT));
    }

    public void saveUser(User user){
        db.save(user);
        if (CommonUtil.isNull(user.doctorDetail))
            user.doctorDetail=new User.DoctorDetail();
        user.doctorDetail.id=user.id;
        if (CommonUtil.isNull(user.patientDetail))
            user.patientDetail=new User.PatientDetail();
        user.patientDetail.id=user.id;
        db.save(user.doctorDetail);
        db.save(user.patientDetail);
    }

    public void requestUserInfo(final SimpleResponseListener<User> listener){
        execute(RequestApi.requestUserInfo(getToken().token), new SimpleResponseListener<UserResponse>() {
            @Override
            public void requestSuccess(UserResponse user, Response response) {
                getToken().userId = user.user.id;
                setToken(getToken());
                saveUser(user.user);
                listener.requestSuccess(user.user, response);
                listener.requestView();

            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
                listener.requestView();
            }
        });
    }

    /*****
     * @Description  注册填写用户名
     */
    public void  requestRegister(String tel,String verifycode,String password,final SimpleResponseListener<User> listener){

        execute(RequestApi.requestRegister(tel, verifycode, password), new SimpleResponseListener<Token>() {
            @Override
            public void requestSuccess(Token token, Response response) {
                setToken(token);
                requestUserInfo(listener);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
                listener.requestView();
            }

            @Override
            public void requestView() {

            }
        });
    }




    /***
     *@Description 找回密码
     * @param listener
     */
    public void  requestUpdatePassword(String mobile,String verifycode,String password, final SimpleResponseListener<User> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/user_sp/updatePassword.json");
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("mobile", mobile));
        param.add(new NameValuePair("password", MD5.compute(password)));
        param.add(new NameValuePair("verifycode", verifycode));
        com.litesuits.http.request.Request request=new com.litesuits.http.request.Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new SimpleResponseListener<Token>() {
            @Override
            public void requestSuccess(Token token, Response response) {
                setToken(token);
                requestUserInfo(listener);

            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
                listener.requestView();
            }

        });

    }
    /***
     *@Description 登陆
     * @param tel
     * @param listener
     */
    public void  requestLogin(final String tel,String pwd,final SimpleResponseListener<User> listener){
        execute(RequestApi.requestLogin(tel, MD5.compute(pwd)), new SimpleResponseListener<Token>() {
            @Override
            public void requestSuccess(Token info, Response response) {
                info.tel=tel;
                setToken(info);
                requestUserInfo(listener);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
                listener.requestView();
            }
        });

    }


    /****
     * 获取患者数目
     */
    public long requestModelNum(){
        QueryBuilder builder=new QueryBuilder(User.class).where(WhereBuilder.create().
                andIn("typeId", new String[]{AppConstants.USER_TYPE_PATIENT}).andEquals("friendId",getLoginUser().id));
        return db.queryCount(builder);
    }

    public void requestFriendList() {
        execute(RequestApi.requestFriendList(getToken().token, null), new SimpleResponseListener<FriendResponse>() {
            @Override
            public void requestSuccess(FriendResponse info, Response response) {
                saveFriendList(info.list);
                EventBus.getDefault().post(new BaseEvent(BaseEvent.EV_USER_FRIEND));
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {

            }
        });
    }

    public void saveFriendList(List<User> list){
        if (CommonUtil.isNull(list))return;
        for (User user:list){
            user.friendId=getLoginUser().id;
            saveUser(user);
        }
    }

    public void requestFriendList(final SimpleResponseListener<List<User>> listener) {
        execute(RequestApi.requestFriendList(getToken().token, null), new SimpleResponseListener<FriendResponse>() {
            @Override
            public void requestSuccess(FriendResponse info, Response response) {
                saveFriendList(info.list);
                listener.requestSuccess(info.list,response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
            }

            @Override
            public void requestView() {
                listener.requestView();
            }
        });

    }



    /*****
     * 获取好友列表  本地　　患者
     * @return
     */
    public List<User>  queryPatientList(){
        QueryBuilder builder=new QueryBuilder(User.class).where(WhereBuilder.create().
                equals("typeId", AppConstants.USER_TYPE_PATIENT).andEquals("friendId",getLoginUser().id));
        List<User> ls=db.query(builder);
        return ls==null?new ArrayList<User>():ls;
    }

    public  List<User> queryFriend( List<User> list){
        if (CommonUtil.notNull(list)){
            for (User user:list){
                User.DoctorDetail detail= db.queryById(user.id, User.DoctorDetail.class);
                if (CommonUtil.notNull(detail)){
                    user.doctorDetail=detail;
                }
                User.PatientDetail detail1= db.queryById(user.id, User.PatientDetail.class);
                if (CommonUtil.notNull(detail1)){
                    user.patientDetail=detail1;
                }
            }
            return list;
        }
        return new ArrayList<>();
    }

    /*****
     * 获取好友列表   医生  营养师　
     * @return
     */
    public List<User>  queryFriendList(){
        QueryBuilder builder=new QueryBuilder(User.class).where(WhereBuilder.create().
                andIn("typeId", new String[]{AppConstants.USER_TYPE_DOCTOR,
                        AppConstants.USER_TYPE_DIETITAN}).andEquals("friendId", getLoginUser().id));
        List<User> ls=db.query(builder);
        return queryFriend(ls);
    }

    /*****
     * 获取好友列表   医生  营养师　
     * @return
     */
    public List<User>  querySameFriendList(){
        QueryBuilder builder=new QueryBuilder(User.class).where(WhereBuilder.create().
                andIn("typeId", new String[]{getLoginUser().typeId}).andEquals("friendId", getLoginUser().id));
        List<User> ls=db.query(builder);
        return queryFriend(ls);
    }


    public List<User>  seachFriendList(String str){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("%").append(str).append("%");
        QueryBuilder builder=new QueryBuilder(User.class).where(WhereBuilder.create().
                where("nickname like ?  or  userName like ?  or noteName like ?",
                        new String[]{stringBuilder.toString(), stringBuilder.toString(), stringBuilder.toString()}));
        List<User> ls=db.query(builder);
        return queryFriend(ls);
    }


    public User getUser(long id){
        User user=db.queryById(id, User.class);
        if (user!=null){
            User.DoctorDetail detail= db.queryById(id, User.DoctorDetail.class);
            if (CommonUtil.notNull(detail)){
                user.doctorDetail=detail;
            }
            User.PatientDetail detail1= db.queryById(id, User.PatientDetail.class);
            if (CommonUtil.notNull(detail1)){
                user.patientDetail=detail1;
            }
        }
       return  user;
   }



    /****
     *
    */
    public void requestModelUserCode(SimpleResponseListener<InviteCodeResponse> handler){
        execute(RequestApi.requestUserInviteCode(token.token),handler);
    }




    /****
     * 填写邀请码
     */
    public void requestWriteInviteCode(String inviteCode, final SimpleResponseListener<ResponseMessage> listener){
        execute(RequestApi.requestSaveInviteCode(getToken().token, inviteCode), listener);


    }

    public void requestAddFriend(String tel,String noteName,final SimpleResponseListener<ResponseMessage> listener){
        execute(RequestApi.requestAddFriend(getToken().token, tel, noteName), listener);
    }

    /*****
     * 通过id 添加
     * @param userId
     * @param noteName
     * @param listener
     */
    public void requestAddFriendID(String userId,String noteName,final SimpleResponseListener<ResponseMessage> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/relation_sp/saveRelationByUserId.json");
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("token",getToken().token));
        param.add(new NameValuePair("userId",userId));
        param.add(new NameValuePair("noteName",noteName));
        execute( new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param)), listener);
    }

    /****
     * 接受添加
     * @param userId
     * @param listener
     */
    public void requestAgreeFriend(String userId,final SimpleResponseListener<ResponseMessage> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/relation_sp/contactaddApproved.json");
        List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair("token",getToken().token));
        param.add(new NameValuePair("userId",userId));
        execute( new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param)), listener);
    }

    /******
     *新朋友
     *
     */
    public void requestNewFriend(final SimpleResponseListener<FriendResponse> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/user_sp/getNewFriendList.json");
        List<NameValuePair> param=new ArrayList();
        param.add(new NameValuePair("token",getToken().token));
        param.add(new NameValuePair("pageNum","1"));
        execute( new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param)), listener);
    }


    /******
     * 血糖记录
     */
    public void requestBloodList(String date,String userId,final SimpleResponseListener<BloodResponse> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.BLOOD_LIST);
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token",getToken().token));
        param.add(new NameValuePair("userId",userId));
        param.add(new NameValuePair("startTime",date));
        Request request=new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request,listener);
    }
    /******
     * 获取用户信息
     */
    public void requestUserInfo(String userId,final SimpleResponseListener<UserResponse> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/user_sp/getUserByMap.json");
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token",getToken().token));
        param.add(new NameValuePair("userId",userId));
        Request request=new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, listener);
    }

    /******
     * 保存用户信息
     */
    public void requestSaveUser(final User user, final SimpleResponseListener<ResponseMessage> listener){
        user.token=getToken().token;
        user.id=getToken().userId;
        execute(RequestApi.requestUpdUser(user), new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                saveUser(user);
                listener.requestSuccess(info, response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
            }

            @Override
            public void requestView() {
                listener.requestView();
            }
        });
    }
    /******
     * 保存用户信息
     */
    public void requestDoctor(final User user, final SimpleResponseListener<ResponseMessage> listener){
        user.token=getToken().token;
        user.id=getToken().userId;
        execute(RequestApi.requestUpdDoctor(user), new SimpleResponseListener<ResponseMessage>() {
            @Override
            public void requestSuccess(ResponseMessage info, Response response) {
                saveUser(user);
                listener.requestSuccess(info, response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
            }

            @Override
            public void requestView() {
                listener.requestView();
            }
        });
    }



    /******
     * 上传图片
     * @param typeId
     * @param path
     * @param listener
     */
    public void  uploadPicture(String typeId,String path, final SimpleResponseListener<UploadPicture> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/image_sp/uploadImage.json");
        MultipartBody body=new MultipartBody();
        body.addPart(new StringPart("token",getToken().token));
        body.addPart(new StringPart("typeId", typeId));
        body.addPart(new FilePart("file", new File(path), "image/jpeg"));
        Request request=new Request(url.toString()).setHttpBody(body).setMethod(HttpMethod.Post);
        execute(request, listener);
    }


    /******
     * 收到的 赞
     * @param listener
     */
    public void requestPraiseList( final SimpleResponseListener<List<Recommended.Item>> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/praise_sp/getPraiseUserListByMap.json");
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        Request request=new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new SimpleResponseListener<Recommended>() {
            @Override
            public void requestSuccess(Recommended info, Response response) {
                listener.requestSuccess(info.list,response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
            }

            @Override
            public void requestView() {
                listener.requestView();
            }
        });

    }

    /******
     *  新朋友
     * @param listener
     */
    public void requestNewFriendList(final List<ContactBean> list, final SimpleResponseListener<List<User>> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/user_sp/getNewFriendList.json");

        if (CommonUtil.notNull(list)){
            for (ContactBean bean:list){
                 bean.setNickname(URLEncoder.encode(bean.getNickname()));
            }

        }
        //List<NameValuePair> param=new ArrayList<>();
       // param.add(new NameValuePair("token", getToken().token));
       // param.add(new NameValuePair("list", JSON.toJSONString(list)));
        Request request=new Request(url.toString());
        request.setMethod(HttpMethod.Post);
        MultipartBody body = new MultipartBody();
        body.addPart(new StringPart("token",getToken().token));
        body.addPart(new StringPart("JSONString", JSON.toJSONString(list)));

        request.setHttpBody(body);
        execute(request, new SimpleResponseListener<ContactListResponse>() {
            @Override
            public void requestSuccess(ContactListResponse info, Response response) {
                listener.requestSuccess(info.getList(), response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
            }

            @Override
            public void requestView() {
                listener.requestView();
            }
        });

    }

    /******
     * 评论列表
     * @param listener
     */
    public void requestMessageList(final SimpleResponseListener<List<Recommended.Item>> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/message_sp/getMessageListByMap.json");
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        Request request=new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new SimpleResponseListener<Recommended>() {
            @Override
            public void requestSuccess(Recommended info, Response response) {
              listener.requestSuccess(info.list,response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
            }

            @Override
            public void requestView() {
                listener.requestView();
            }
        });
    }

    /******
     * 收到的投诉
     * @param listener
     */
    public void requestMessageRecord(final SimpleResponseListener<ResponseMessage> listener) {
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/message_sp/getMessageRecordByMap.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request,listener);
    }


    /*******
     * 保存个人钱包
     * @param wallet
     */
    public void saveWallet(Wallet wallet){
        if (CommonUtil.notNull(wallet)){
            wallet.id=getLoginUser().id;
            db.save(wallet);
            if (wallet.list!=null){
                for (Wallet.Item item:wallet.list){
                    item.userId=getLoginUser().id;
                    db.save(item);
                }
            }
        }
    }

    public Wallet getWallet(){
        QueryBuilder builder=new QueryBuilder(Wallet.class).where(WhereBuilder.create()
                .andEquals("id", getLoginUser().id));
        List<Wallet> list=db.query(builder);
        //   异常　－－
        /*if (list!=null&&list.size()>0){
            Wallet wallet=list.get(0);
            QueryBuilder builderList=new QueryBuilder(Wallet.Item.class).where(WhereBuilder.create()
                    .andEquals("id", getLoginUser().id));
            List<Wallet.Item> items=db.query(builderList);
            wallet.list=items;
            return wallet;
        }*/
        return new Wallet();
    }

    /******
     * 请求钱包
     * @param listener
     */
    public void requestWallet(final SimpleResponseListener<Wallet> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/wallet_sp/getWalletByToken.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new SimpleResponseListener<Wallet>() {
            @Override
            public void requestSuccess(Wallet info, Response response) {
                listener.requestSuccess(info, response);
                saveWallet(info);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
            }

            @Override
            public void requestView() {
                listener.requestView();
            }
        });
    }

    /******
     * 提现
     * @param listener
     */
    public void requestWalletMoney(String money,final SimpleResponseListener<Wallet> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/withdraw_sp/withdrawApply.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("money", money));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request,listener);
    }
    /******
     * 添加支付宝账号
     * @param listener
     */
    public void requestAddWalletAcounnt(String alipayNo,final SimpleResponseListener<ResponseMessage> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/alipay_sp/saveAlipay.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("alipayNo", alipayNo));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, listener);
    }

    /******
     * 添加银行账号
     * @param listener
     */
    public void requestAddWalletBlankAcounnt(String bankId,String openingAccount,final SimpleResponseListener<ResponseMessage> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/bankcard_sp/saveBankCard.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("bankId", bankId));
        param.add(new NameValuePair("openingAccount", openingAccount));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request,listener);
    }

    /******
     * 账单列表
     * @param listener
     */
    public void requestAddWalletBill(final SimpleResponseListener<List<WalletBill.Item>> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/withdraw_sp/withdrawLog.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        final Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new SimpleResponseListener<WalletBill>() {
            @Override
            public void requestSuccess(WalletBill info, Response response) {
                List<WalletBill.Item> temap=new ArrayList();
                for (WalletBill.Item item:info.list){
                    item.yearMonth= TimeUtil.getYm(item.tradingTime);
                    item.month= TimeUtil.getMM(item.tradingTime);
                    item.userId=getLoginUser().id;
                    item.tradingDate=TimeUtil.formmatYmd(item.tradingTime);
                    temap.add(item);
                    db.save(item);
                }
                listener.requestSuccess(temap,response);
            }

            @Override
            public void requestError(HttpException e, ResponseMessage info) {
                listener.requestError(e, info);
            }

            @Override
            public void requestView() {
                listener.requestView();
            }
        });

    }


    /******
     * 设置支付密码
     * @param listener
     */
    public void requestPayPassword(String password,String verifycode,final SimpleResponseListener<ResponseMessage> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/wallet_sp/setPassword.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("password", MD5.compute(password)));
        param.add(new NameValuePair("verifycode", verifycode));

        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request,listener);
    }
    /******
     * 获取发送短信验证码
     * @param listener
     */
    public void requestSendPayPassword(String password,String verifycode,final SimpleResponseListener<ResponseMessage> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/wallet_sp/setPassword.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("password", MD5.compute(password)));
        param.add(new NameValuePair("verifycode", verifycode));

        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request,listener);
    }

    public List<WalletBill.Item>  queryWalletBill(){
        QueryBuilder builder=new QueryBuilder(WalletBill.Item.class).where(WhereBuilder.create()
                .andEquals("userId", getLoginUser().id));
        return  db.query(builder);
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
     * 查询用户
     * @param listener
     */
    public void requestSeachUser(String search,final SimpleResponseListener<FriendResponse> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("/crm/user_sp/getUserListByMap.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("search", search));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request,listener);
    }

    public  Token getToken(){
        synchronized (this){
            /****做处理  查询token***/
            if (token==null||!token.isLogin()){
                token=db.queryById(1, Token.class);
            }
            return token;
        }
    }
    public void setToken(Token token){
        this.token=token;
        this.token.setLogin();
        db.save(this.token);
    }
    public void setLoginOut(){
        token.setLoginOut();
        db.save(token);
    }


    /*****
     * 获取登陆的user信息
     * @return
     */
    public User getLoginUser(){
        if(CommonUtil.isNull(getToken())){
            return null;
        }
        final User user=getUser(getToken().userId);
        if (CommonUtil.notNull(user)&&user.updateState==User.UPDATE){
            Intent intent=new Intent(mContext, RequestService.class);
            intent.putExtra(RequestTag.TAG,RequestTag.R_UPD_USER);
            mContext.startService(intent);
        }
        return user;
    }

    /*****
     *
     * @param
     */
    public void requestUpdateUser(User user,SimpleResponseListener<ResponseMessage> handler){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.UPD_DOCTOR_INFO);
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("typeId",user.typeId));
        param.add(new NameValuePair("nickname", user.nickname));
        param.add(new NameValuePair("email",user.email));
        param.add(new NameValuePair("tel",user.tel));
        param.add(new NameValuePair("sex",user.sex));
        param.add(new NameValuePair("age",user.age));
        param.add(new NameValuePair("photoId",user.photoId));
        param.add(new NameValuePair("intro",user.intro));
        param.add(new NameValuePair("address",user.address));
        param.add(new NameValuePair("workRegionId",user.doctorDetail.workRegionId));
        param.add(new NameValuePair("specialty",user.doctorDetail.speciality));
        param.add(new NameValuePair("hospitalName",user.doctorDetail.hospitalName));
        param.add(new NameValuePair("userName",user.userName));
        param.add(new NameValuePair("jobId",user.doctorDetail.jobId));
        param.add(new NameValuePair("token",getToken().token));
        param.add(new NameValuePair("birthday", user.birthday));
        Request request=new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request,handler);
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
        param.add(new NameValuePair("typeId", "116"));
        param.add(new NameValuePair("wayNo", "rolle"));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new SimpleResponseListener<Version>() {
            @Override
            public void requestSuccess(Version info, Response response) {
                db.save(info);
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

}
