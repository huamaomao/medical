package com.rolle.doctor.viewmodel;

import android.content.Context;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.Constants;
import com.android.common.util.MD5;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.request.content.StringBody;
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
        execute(RequestApi.requestUserInfo(token),handler);
    }

    public void saveUser(User user){
        db.save(user);
    }

    public void saveUser(){
        QueryBuilder  builder=new QueryBuilder(User.class).columns(new String[]{"token"});
       /* User user=db.
        ColumnsValue cv = new ColumnsValue(new String[]{"phone"});*/

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
        execute(RequestApi.requestLogin(tel, MD5.compute(pwd)),listener);
    }


    /****
     * 获取患者数目
     */
    public void requestModelNum(String token,HttpModelHandler<String> handler){
        execute(RequestApi.requestPatientNum(token, com.rolle.doctor.util.Constants.USER_TYPE_PATIENT),handler);
    }

    /****
     * 获取好友列表
     * 获取 评论，投诉
     */
    public void requestFriendDoctor(final ModelListener<List<FriendResponse.Item>> listener) {
        execute(RequestApi.requestFriendList(getToken().token, com.rolle.doctor.util.Constants.USER_TYPE_DOCTOR), new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                FriendResponse responseMessage = res.getObject(FriendResponse.class);
                if (CommonUtil.notNull(responseMessage)) {
                    if ("200".equals(responseMessage.statusCode) && CommonUtil.notNull(responseMessage.list)) {
                        listener.model(res, responseMessage.list);
                        db.save(responseMessage.list);
                    } else {
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
    public void requestRecommendReviewComplaints(String type,HttpModelHandler<String> handler){
        execute(RequestApi.requestRecommendReviewComplaints(token.token, type),handler);
    }
    /****
     * 获取好友列表
     */
    public void requestFriendPatient(final ModelListener<List<FriendResponse.Item>> listener){
        execute(RequestApi.requestFriendList(getToken().token, com.rolle.doctor.util.Constants.USER_TYPE_PATIENT),new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                FriendResponse   responseMessage= res.getObject(FriendResponse.class);
                if (CommonUtil.notNull(responseMessage)){
                    if ("200".equals(responseMessage.statusCode)&&CommonUtil.notNull(responseMessage.list)){
                        listener.model(res,responseMessage.list);
                        db.save(responseMessage.list);
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

    /*****
     * 获取好友列表
     * @param type
     * @return
     */
    public List<FriendResponse.Item>  queryFriedList(String type){
        QueryBuilder builder=new QueryBuilder(FriendResponse.Item.class).where(WhereBuilder.create().equals("typeId",type));
         return db.<FriendResponse.Item>query(builder);
    }

    /****
     * 获取好友列表
     * 赞
     */
    public void requestFriendDietitan(final ModelListener<List<FriendResponse.Item>> listener){
        execute(RequestApi.requestFriendList(getToken().token,com.rolle.doctor.util.Constants.USER_TYPE_DIETITAN),new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
                FriendResponse   responseMessage= res.getObject(FriendResponse.class);
                if (CommonUtil.notNull(responseMessage)){
                    if ("200".equals(responseMessage.statusCode)&&CommonUtil.notNull(responseMessage.list)){
                        listener.model(res,responseMessage.list);
                        db.save(responseMessage.list);
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



   public User getUser(String id){
       //execute(RequestApi.requestUserInviteCode(token.token),handler);
       return new User();
   }



    /****
     * 获取邀请码
     */
    public void requestPraise(HttpModelHandler<String> handler){
        execute(RequestApi.requestPraise(token.token),handler);
    }

    /****
            * 获取患者数目
    */
    public void requestModelUserCode(HttpModelHandler<String> handler){
        execute(RequestApi.requestUserInviteCode(token.token),handler);
    }




    /****
     * 填写邀请码
     */
    public void requestWriteInviteCode(String inviteCode, final ModelListener<ResponseMessage> listener){
        execute(RequestApi.requestSaveInviteCode(getToken().token,inviteCode),new HttpModelHandler<String>() {
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
        execute(RequestApi.requestAddFriend(getToken().token,userId,noteName),new HttpModelHandler<String>() {
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


    public Token getToken(){
        if (token==null){
            token=db.queryById(1,Token.class);
        }
        return token==null?new Token():token;
    }
    public void setToken(Token token){
        UserModel.token=token;
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
}
