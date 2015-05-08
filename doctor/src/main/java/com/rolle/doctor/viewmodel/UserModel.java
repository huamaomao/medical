package com.rolle.doctor.viewmodel;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.Constants;
import com.android.common.util.MD5;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.request.Request;
import com.litesuits.http.request.content.StringBody;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.param.HttpMethod;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ColumnsValue;
import com.rolle.doctor.domain.FriendResponse;
import com.rolle.doctor.domain.Token;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.RequestApi;
import com.rolle.doctor.util.UrlApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/21 0021.
 */
public class UserModel  extends ViewModel {
    private static Token token;
    public  DataBase db;

    public UserModel(Context context){
          this.db=LiteOrm.newCascadeInstance(context, com.rolle.doctor.util.Constants.DB_NAME);
    }
    /****
     * 获取个人资料
     */
    public void requestModel(String token,HttpModelHandler<String> handler){
        execute(RequestApi.requestUserInfo(token), handler);
    }

    public void saveUser(User user){
        db.save(user);
    }

    public void saveUser(FriendResponse.Item user){
        db.save(user);
    }


    /***
     *@Description 登陆
     * @param tel
     * @param listener
     */
    public void  requestModel(String tel,String pwd,final HttpModelHandler<String> listener,OnValidationListener onValidationListener){
        if (!CommonUtil.isMobileNO(tel)){
            if (CommonUtil.notNull(onValidationListener)){
                onValidationListener.errorTel();
            }
            return;
        }
        if (CommonUtil.isEmpty(pwd)||pwd.length()<6||pwd.length()>15){
            onValidationListener.errorPwd();
            return;
        }
        execute(RequestApi.requestLogin(tel, MD5.compute(pwd)), listener);
    }


    /****
     * 获取患者数目
     */
    public long requestModelNum(){
        QueryBuilder builder=new QueryBuilder(FriendResponse.Item.class).where(WhereBuilder.create().
                andIn("typeId", new String[]{com.rolle.doctor.util.Constants.USER_TYPE_PATIENT}));
        return db.queryCount(builder);
    }

    public void requestFriendList() {
        execute(RequestApi.requestFriendList(getToken().token, null), new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                FriendResponse responseMessage = res.getObject(FriendResponse.class);
                if (CommonUtil.notNull(responseMessage)) {
                    if ("200".equals(responseMessage.statusCode) && CommonUtil.notNull(responseMessage.friendList))
                        db.save(responseMessage.friendList);
                }
            }

            @Override
            protected void onFailure(HttpException e, Response res) {

            }
        });
    }

    public void requestFriendList(final ModelListener<List<FriendResponse.Item>> listener) {
        execute(RequestApi.requestFriendList(getToken().token, null), new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                FriendResponse responseMessage = res.getObject(FriendResponse.class);
                if (CommonUtil.notNull(responseMessage)) {
                    if ("200".equals(responseMessage.statusCode) && CommonUtil.notNull(responseMessage.friendList)){
                        db.save(responseMessage.friendList);
                        listener.model(res, responseMessage.friendList);
                        listener.view();
                    }

                }
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                listener.errorModel(null);
                listener.view();
            }
        });
    }


    public void requestRecommendReviewComplaints(String type,HttpModelHandler<String> handler){
        execute(RequestApi.requestRecommendReviewComplaints(token.token, type),handler);
    }



    /*****
     * 获取好友列表  本地　　  患者
     * @return
     */
    public List<FriendResponse.Item>  queryPatientList(){
        QueryBuilder builder=new QueryBuilder(FriendResponse.Item.class).where(WhereBuilder.create().
                equals("typeId", com.rolle.doctor.util.Constants.USER_TYPE_PATIENT));
        List<FriendResponse.Item> ls=db.<FriendResponse.Item>query(builder);
        return ls==null?new ArrayList<FriendResponse.Item>():ls;
    }

    /*****
     * 获取好友列表   医生  营养师　
     * @return
     */
    public List<FriendResponse.Item>  queryFriendList(){
        QueryBuilder builder=new QueryBuilder(FriendResponse.Item.class).where(WhereBuilder.create().
                andIn("typeId", new String[]{com.rolle.doctor.util.Constants.USER_TYPE_DOCTOR,
                        com.rolle.doctor.util.Constants.USER_TYPE_DIETITAN}));
        List<FriendResponse.Item> ls=db.<FriendResponse.Item>query(builder);
        return ls==null?new ArrayList<FriendResponse.Item>():ls;
    }

    /*****
     * 获取好友列表   医生  营养师　
     * @return
     */
    public List<FriendResponse.Item>  querySameFriendList(){
        QueryBuilder builder=new QueryBuilder(FriendResponse.Item.class).where(WhereBuilder.create().
                andIn("typeId", new String[]{getLoginUser().typeId}).and().equals("department", getLoginUser().department));
        List<FriendResponse.Item> ls=db.<FriendResponse.Item>query(builder);
        return ls==null?new ArrayList<FriendResponse.Item>():ls;
    }


    public List<FriendResponse.Item>  seachFriendList(String str){
        QueryBuilder builder=new QueryBuilder(FriendResponse.Item.class).where(WhereBuilder.create().where("nickname like %?", new String[]{str}).or().where("id like %?", new String[]{str}).
                or().where("noteName like %?", new String[]{str}));
        return db.query(builder);
    }


    public FriendResponse.Item getUser(long id){
       return  db.queryById(id, FriendResponse.Item.class);
   }



    /****
     * 获取邀请码
     */
    public void requestPraise(HttpModelHandler<String> handler){
        execute(RequestApi.requestPraise(token.token),handler);
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
                        listener.errorModel(null);
                    }
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                listener.errorModel(null);
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
                        listener.model(res,responseMessage);
                    }else{
                        listener.errorModel(null);
                    }
                }
                listener.view();
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
                listener.errorModel(null);
                listener.view();
            }
        });
    }

    /******
     * 血糖记录
     */
    public void requestBloodList(String date,ModelListener listener){
        StringBuilder url=new StringBuilder(UrlApi.SERVER_NAME);
        url.append(UrlApi.BLOOD_LIST);
        List<NameValuePair> param=new ArrayList<>();
        param.add(new NameValuePair("token",getToken().token));
        Request request=new Request(url.toString()).setMethod(HttpMethod.Post).setHttpBody(new UrlEncodedFormBody(param));
        execute(request, new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {

            }

            @Override
            protected void onFailure(HttpException e, Response res) {

            }
        });
    }


    public Token getToken(){
        if (token==null){
            token=db.queryById(1,Token.class);
        }
        return token==null?new Token():token;
    }
    public void setToken(Token token){
        UserModel.token=token;
        token.status=Token.STATUS_LOGIN;
        db.save(token);
    }

    /******
     *  更改登陆状态
     */
    public void cleanToken(){
        token=getToken();
        token.status=Token.STATUS_NO;
        db.save(token);
    }

    /*****
     * 获取登陆的user信息
     * @return
     */
    public User getLoginUser(){
        return db.queryById(getToken().userId,User.class);
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

    public static  interface OnTelValidationListener{
        void errorTel();
    }
}
