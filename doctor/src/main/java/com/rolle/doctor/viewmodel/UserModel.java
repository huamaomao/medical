package com.rolle.doctor.viewmodel;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.ActivityModel;
import com.android.common.util.CommonUtil;
import com.android.common.util.Constants;
import com.android.common.util.Log;
import com.android.common.util.MD5;
import com.android.common.util.ViewUtil;
import com.android.common.view.IView;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.request.Request;
import com.litesuits.http.request.content.FileBody;
import com.litesuits.http.request.content.MultipartBody;
import com.litesuits.http.request.content.StringBody;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.content.multi.FilePart;
import com.litesuits.http.request.content.multi.StringPart;
import com.litesuits.http.request.param.HttpMethod;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ColumnsValue;
import com.rolle.doctor.domain.BloodResponse;
import com.rolle.doctor.domain.ContactBean;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.Recommended;
import com.rolle.doctor.domain.Token;
import com.rolle.doctor.domain.UploadPicture;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.domain.UserResponse;
import com.rolle.doctor.domain.Wallet;
import com.rolle.doctor.domain.WalletBill;
import com.rolle.doctor.ui.BaseActivity;
import com.rolle.doctor.ui.LoginActivity;
import com.rolle.doctor.ui.MainActivity;
import com.rolle.doctor.ui.RegisterChooseActivity;
import com.rolle.doctor.util.RequestApi;
import com.rolle.doctor.util.TimeUtil;
import com.rolle.doctor.util.UrlApi;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/21 0021.
 */
public class UserModel  extends ViewModel {
    private static Token token;
    public  DataBase db;
    private  Context mContext;
    public UserModel(Context context){
        this.db=LiteOrm.newCascadeInstance(context, com.rolle.doctor.util.Constants.DB_NAME);
        this.mContext=context;
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

    public void requestUserInfo(final ModelListener<User> listener){
        execute(RequestApi.requestUserInfo(getToken().token), new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                UserResponse user = res.getObject(UserResponse.class);
                if (CommonUtil.notNull(user)) {
                    switch (user.statusCode) {
                        case "200":
                            // 更新 token id
                            getToken().userId = user.user.id;
                            setToken(getToken());
                            saveUser(user.user);
                            listener.model(res, user.user);
                            break;
                        case "300":
                            listener.errorModel(null, res);
                            break;
                    }
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                listener.errorModel(e, res);
                listener.view();
            }
        });
    }

    /*****
     * @Description  注册填写用户名
     */
    public void  requestRegister(String tel,String verifycode,String password,final ModelListener<User> listener){
        execute(RequestApi.requestRegister(tel, verifycode, password), new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                Token token = res.getObject(Token.class);
                if (CommonUtil.notNull(token)) {
                    switch (token.statusCode) {
                        case "200":
                            setToken(token);
                            requestUserInfo(new ModelListener<User>() {
                                @Override
                                public void model(Response response, User user) {
                                    listener.model(response, user);
                                }

                                @Override
                                public void errorModel(HttpException e, Response response) {
                                    listener.errorModel(e, response);
                                }

                                @Override
                                public void view() {
                                    listener.view();
                                }
                            });
                            break;
                        case "300":
                            listener.errorModel(null,res);
                            listener.view();
                            break;
                    }
                }
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                listener.errorModel(e,res);
                listener.view();
            }
        });
    }




    /***
     *@Description 找回密码
     * @param listener
     */
    public void  requestUpdatePassword(String mobile,String verifycode,String password, final ModelListener<Token> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("user_sp/updatePassword.json");
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("mobile", mobile));
        param.add(new NameValuePair("password", MD5.compute(password)));
        param.add(new NameValuePair("verifycode", verifycode));
        com.litesuits.http.request.Request request=new com.litesuits.http.request.Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, final com.litesuits.http.response.Response res) {
                Log.d(data);
                final  Token token=res.getObject(Token.class);
                if (CommonUtil.notNull(token)){
                    switch (token.statusCode){
                        case "200":
                            setToken(token);
                            requestUserInfo(new ModelListener<User>() {
                                @Override
                                public void model(Response response, User user) {
                                    listener.model(res, token);
                                }

                                @Override
                                public void errorModel(HttpException e, Response response) {
                                    listener.errorModel(e, response);
                                }

                                @Override
                                public void view() {
                                    listener.view();
                                }
                            });
                            break;
                        case "300":
                            listener.errorModel(null, res);
                            break;
                    }
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, com.litesuits.http.response.Response res) {
                listener.errorModel(e,res);
                listener.view();
            }
        });
    }
    /***
     *@Description 登陆
     * @param tel
     * @param listener
     */
    public void  requestLogin(String tel,String pwd,final ModelListener<User> listener){
        execute(RequestApi.requestLogin(tel, MD5.compute(pwd)), new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                Token token=res.getObject(Token.class);
                if (CommonUtil.notNull(token)){
                    switch (token.statusCode){
                        case "200":
                            setToken(token);
                            requestUserInfo(new ModelListener<User>() {
                                @Override
                                public void model(Response response, User user) {
                                    listener.model(response, user);
                                }

                                @Override
                                public void errorModel(HttpException e, Response response) {
                                    listener.errorModel(e, response);
                                }

                                @Override
                                public void view() {
                                    listener.view();
                                }
                            });
                            break;
                        case "300":
                            listener.errorModel(null,res);
                            break;
                    }
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                listener.errorModel(e,res);
                listener.view();
            }
        });
    }


    /****
     * 获取患者数目
     */
    public long requestModelNum(){
        QueryBuilder builder=new QueryBuilder(User.class).where(WhereBuilder.create().
                andIn("typeId", new String[]{com.rolle.doctor.util.Constants.USER_TYPE_PATIENT}).andEquals("friendId",getLoginUser().id));
        return db.queryCount(builder);
    }

    public void requestFriendList() {
        execute(RequestApi.requestFriendList(getToken().token, null), new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                FriendResponse responseMessage = res.getObject(FriendResponse.class);
                if (CommonUtil.notNull(responseMessage)) {
                    if ("200".equals(responseMessage.statusCode) && CommonUtil.notNull(responseMessage.friendList)) {
                        saveFriendList(responseMessage.friendList);
                    }
                }
            }

            @Override
            protected void onFailure(HttpException e, Response res) {

            }
        });
    }

    public void saveFriendList(List<User> list){
        for (User user:list){
            user.friendId=getLoginUser().id;
            saveUser(user);
        }
    }

    public void requestFriendList(final ModelListener<List<User>> listener) {
        execute(RequestApi.requestFriendList(getToken().token, null), new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                FriendResponse responseMessage = res.getObject(FriendResponse.class);
                if (CommonUtil.notNull(responseMessage)) {
                    if ("200".equals(responseMessage.statusCode) && CommonUtil.notNull(responseMessage.friendList)) {
                        saveFriendList(responseMessage.friendList);
                        listener.model(res, responseMessage.friendList);
                        listener.view();
                    }

                }
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                Log.d(mContext);
                requestHandle(e, res, mContext);
                listener.errorModel(e,res);
                listener.view();
            }
        });
    }



    /*****
     * 获取好友列表  本地　　患者
     * @return
     */
    public List<User>  queryPatientList(){
        QueryBuilder builder=new QueryBuilder(User.class).where(WhereBuilder.create().
                equals("typeId", com.rolle.doctor.util.Constants.USER_TYPE_PATIENT).andEquals("friendId",getLoginUser().id));
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
                andIn("typeId", new String[]{com.rolle.doctor.util.Constants.USER_TYPE_DOCTOR,
                        com.rolle.doctor.util.Constants.USER_TYPE_DIETITAN}).andEquals("friendId", getLoginUser().id));
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

    */
    public void requestModelUserCode(HttpModelHandler<String> handler){
        execute(RequestApi.requestUserInviteCode(token.token),handler);
    }




    /****
     * 填写邀请码
     */
    public void requestWriteInviteCode(String inviteCode, final ModelListener<ResponseMessage> listener){
        execute(RequestApi.requestSaveInviteCode(getToken().token, inviteCode),new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                ResponseMessage   responseMessage= res.getObject(ResponseMessage.class);
                if (CommonUtil.notNull(responseMessage)){
                    if ("200".equals(responseMessage.statusCode)){
                      listener.model(res,responseMessage);
                    }else{

                    }
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                requestHandle(e,res,mContext);
                listener.errorModel(e,res);
                listener.view();
            }
        });
    }

    public void requestAddFriend(String userId,String noteName,final ModelListener<ResponseMessage> listener){
        execute(RequestApi.requestAddFriend(getToken().token, userId, noteName),new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                ResponseMessage   responseMessage= res.getObject(ResponseMessage.class);
                if (CommonUtil.notNull(responseMessage)){
                    if ("200".equals(responseMessage.statusCode)){
                        listener.model(res, responseMessage);
                    }
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                requestHandle(e,res,mContext);
                listener.errorModel(e, res);
                listener.view();
            }
        });
    }

    /******
     * 血糖记录
     */
    public void requestBloodList(String date,String userId,final ModelListener<BloodResponse> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.BLOOD_LIST);
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token",getToken().token));
        param.add(new NameValuePair("userId",userId));
        param.add(new NameValuePair("startTime",date));
        Request request=new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                BloodResponse response=res.getObject(BloodResponse.class);
                if (CommonUtil.notNull(response)){
                    if ("200".equals(response.statusCode)){
                        listener.model(res, response);
                    }
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                requestHandle(e, res, mContext);
                listener.errorModel(e, res);
            }
        });
    }

    /******
     * 保存用户信息
     */
    public void requestSaveUser(final User user, final ModelListener<ResponseMessage> listener){
        user.token=getToken().token;
        user.id=getToken().userId;
        execute(RequestApi.requestUpdUser(user), new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                ResponseMessage message=res.getObject(ResponseMessage.class);
                if (CommonUtil.notNull(message)||"200".equals(message.statusCode)){
                    listener.model(res,res.getObject(ResponseMessage.class));
                    saveUser(user);
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                requestHandle(e,res,mContext);
                listener.errorModel(e, res);
                listener.view();
            }
        });
    }

    /******
     * 上传图片
     * @param typeId
     * @param path
     * @param listener
     */
    public void  uploadPicture(String typeId,String path, final ModelListener<UploadPicture> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("image_sp/uploadImage.json");
        MultipartBody body=new MultipartBody();
        body.addPart(new StringPart("token",getToken().token));
        body.addPart(new StringPart("typeId", typeId));
        body.addPart(new FilePart("files", new File(path), "image/jpeg"));
        Request request=new Request(url.toString()).setHttpBody(body).setMethod(HttpMethod.Post);

        execute(request, new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                Log.d(data);
                UploadPicture message=res.getObject(UploadPicture.class);
                if (CommonUtil.notNull(message)&&"200".equals(message.statusCode)){
                    listener.model(res,message);
                }else{
                    listener.errorModel(null, res);
                }
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                requestHandle(e, res, mContext);
                listener.errorModel(e, res);
            }
        });
    }


    /******
     * 收到的 赞
     * @param listener
     */
    public void requestPraiseList( final ModelListener<List<Recommended.Item>> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("praise_sp/getPraiseUserListByMap.json");
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        Request request=new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new HttpModelHandler<String>(){
            @Override
            protected void onSuccess(String data, Response res) {
                Log.d(data);
                Recommended message=res.getObject(Recommended.class);
                if (CommonUtil.notNull(message)&&"200".equals(message.statusCode)&&CommonUtil.notNull(message.list)){
                    listener.model(res,message.list);
                }else{
                    listener.errorModel(null,res);
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                requestHandle(e,res,mContext);
                listener.errorModel(e, res);
                listener.view();
            }
        });

    }

    /******
     *  新朋友
     * @param listener
     */
    public void requestNewFriendList(List<ContactBean> list, final ModelListener<List<Recommended.Item>> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("user_sp/getNewFriendList.json");
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        if (CommonUtil.notNull(list)){
            for (ContactBean bean:list){
                 bean.setDesplayName(URLEncoder.encode(bean.getDesplayName()));
            }
            param.add(new NameValuePair("list",JSON.toJSONString(list)));
            Log.d(param.get(1));
        }
        Request request=new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new HttpModelHandler<String>(){
            @Override
            protected void onSuccess(String data, Response res) {
                Log.d(data);
                Recommended message=res.getObject(Recommended.class);
                if (CommonUtil.notNull(message)&&"200".equals(message.statusCode)&&CommonUtil.notNull(message.list)){
                    listener.model(res,message.list);
                }else{
                    listener.errorModel(null,res);
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                requestHandle(e,res,mContext);
                listener.errorModel(e,res);
                listener.view();
            }
        });

    }

    /******
     * 评论列表
     * @param listener
     */
    public void requestMessageList(final ModelListener<List<Recommended.Item>> listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append("message_sp/getMessageListByMap.json");
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token",getToken().token));
        Request request=new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new HttpModelHandler<String>(){
            @Override
            protected void onSuccess(String data, Response res) {
                Log.d(data);
                Recommended message=res.getObject(Recommended.class);
                if (CommonUtil.notNull(message)&&"200".equals(message.statusCode)&&CommonUtil.notNull(message.list)){
                    listener.model(res,message.list);
                }else{
                    listener.errorModel(null,res);
                }
                listener.view();
            }
            @Override
            protected void onFailure(HttpException e, Response res) {
                requestHandle(e,res,mContext);
                listener.errorModel(e,res);
                listener.view();
            }
        });
    }

    /******
     * 收到的投诉
     * @param listener
     */
    public void requestMessageRecord(final ModelListener<ResponseMessage> listener) {
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("message_sp/getMessageRecordByMap.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                Log.d(data);
                ResponseMessage message = res.getObject(ResponseMessage.class);
                if (CommonUtil.notNull(message) && "200".equals(message.statusCode)) {
                    listener.model(res, message);
                } else {
                    listener.errorModel(null,res);
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                requestHandle(e,res,mContext);
                listener.errorModel(e,res);
                listener.view();
            }
        });
    }


    /*******
     * 保存个人钱包
     * @param wallet
     */
    public void saveWallet(Wallet wallet){
        if (CommonUtil.notNull(wallet)){
            wallet.userId=getLoginUser().id;
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
                .andEquals("userId", getLoginUser().id));
        List<Wallet> list=db.query(builder);
        if (list!=null){
            Wallet wallet=list.get(0);
            QueryBuilder builderList=new QueryBuilder(Wallet.Item.class).where(WhereBuilder.create()
                    .andEquals("userId", getLoginUser().id));
            List<Wallet.Item> items=db.query(builderList);
            wallet.list=items;
            return wallet;
        }
        return null;
    }

    /******
     * 请求钱包
     * @param listener
     */
    public void requestWallet(final ModelListener<Wallet> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("wallet_sp/getWalletByToken.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                Log.d(data);
                Wallet message = res.getObject(Wallet.class);
                if (CommonUtil.notNull(message) && "200".equals(message.statusCode)) {
                    listener.model(res, message);
                    saveWallet(message);
                } else {
                    listener.errorModel(null,res);
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                listener.model(res, getWallet());
                listener.view();
            }
        });
    }

    /******
     * 提现
     * @param listener
     */
    public void requestWalletMoney(String money,final ModelListener<Wallet> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("withdraw_sp/withdrawApply.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("money", money));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                Log.d(data);
                Wallet message = res.getObject(Wallet.class);
                if (CommonUtil.notNull(message) && "200".equals(message.statusCode)) {
                    listener.model(res, message);
                } else {
                    listener.errorModel(null,res);
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                requestHandle(e,res,mContext);
                listener.errorModel(e, res);
                listener.view();
            }
        });
    }
    /******
     * 添加支付宝账号
     * @param listener
     */
    public void requestAddWalletAcounnt(String alipayNo,final ModelListener<ResponseMessage> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("alipay_sp/saveAlipay.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("alipayNo", alipayNo));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                Log.d(data);
                ResponseMessage message = res.getObject(ResponseMessage.class);
                if (CommonUtil.notNull(message) && "200".equals(message.statusCode)) {
                    listener.model(res, message);
                } else {
                    listener.errorModel(null,res);
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                requestHandle(e,res,mContext);
                listener.errorModel(e,res);
                listener.view();
            }
        });
    }

    /******
     * 账单列表
     * @param listener
     */
    public void requestAddWalletBill(final ModelListener<List<WalletBill.Item>> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("withdraw_sp/withdrawLog.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                Log.d(data);
                WalletBill message = res.getObject(WalletBill.class);
                if (CommonUtil.notNull(message) && "200".equals(message.statusCode)&&CommonUtil.notNull(message.list)){
                    List<WalletBill.Item> temap=new ArrayList<WalletBill.Item>();
                    for (WalletBill.Item item:message.list){
                        item.yearMonth= TimeUtil.getYm(item.tradingTime);
                        item.month= TimeUtil.getMM(item.tradingTime);
                        item.userId=getLoginUser().id;
                        item.tradingDate=TimeUtil.formmatYmd(item.tradingTime);
                        temap.add(item);
                        db.save(item);
                    }
                    listener.model(res,temap);
                } else {
                    listener.errorModel(null,res);
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res){
                List<WalletBill.Item> list=queryWalletBill();
                if (CommonUtil.notNull(list))
                    listener.model(res,list);
                listener.errorModel(e,res);
                listener.view();
            }
        });
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
    public void requestSaveFeedback(String content,final ModelListener<ResponseMessage> listener){
        StringBuilder url = new StringBuilder(UrlApi.SERVER_NAME);
        url.append("feedback_sp/saveFeedback.json");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("token", getToken().token));
        param.add(new NameValuePair("content", content));
        Request request = new Request(url.toString()).setHttpBody(new UrlEncodedFormBody(param)).setMethod(HttpMethod.Post);
        execute(request, new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                Log.d(data);
                ResponseMessage message = res.getObject(ResponseMessage.class);
                if (CommonUtil.notNull(message) && "200".equals(message.statusCode)) {
                    listener.model(res, message);
                } else {
                    listener.errorModel(null,res);
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res){
                listener.errorModel(e,res);
                listener.view();
            }
        });
    }

    public Token getToken(){
        if (token==null){
            token=db.queryById(1,Token.class);
        }
        return token;
    }
    public void setToken(Token token){
        this.token=token;
        this.token.setLogin();
        db.save(this.token);
    }
    public void setLoginOut(){
        getToken().setLoginOut();
        db.save(getToken());
    }


    /*****
     * 获取登陆的user信息
     * @return
     */
    public User getLoginUser(){
        final User user=getUser(getToken().userId);
        if (CommonUtil.notNull(user)&&user.updateState==User.UPDATE){
            requestUpdateUser(user, new HttpModelHandler<String>() {
                @Override
                protected void onSuccess(String data, Response res) {
                    Log.d("更新用户信息成功.......");
                    user.setNoUpdateStatus();
                    saveUser(user);
                }

                @Override
                protected void onFailure(HttpException e, Response res) {
                    Log.d("更新用户信息失败.......");
                }
            });
        }
        return user;
    }

    /*****
     *
     * @param
     */
    public void requestUpdateUser(User user,HttpModelHandler<String> handler){
        execute(RequestApi.requestUpdUser(user),handler);
    }

    public static  interface OnValidationListener{
        void errorTel();
        void errorPwd();
    }

}
