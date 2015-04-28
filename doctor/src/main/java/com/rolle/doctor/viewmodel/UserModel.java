package com.rolle.doctor.viewmodel;

import android.content.Context;

import com.android.common.domain.ResponseMessage;
import com.android.common.util.CommonUtil;
import com.android.common.util.Constants;
import com.android.common.util.MD5;
import com.android.common.viewmodel.ViewModel;
import com.litesuits.http.request.content.StringBody;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ColumnsValue;
import com.rolle.doctor.domain.Token;
import com.rolle.doctor.domain.User;
import com.rolle.doctor.util.RequestApi;

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
     * 获取患者数目
     */
    public void requestModel(String token,String type,HttpModelHandler<String> handler){
        execute(RequestApi.requestFriendList(token, type),handler);
    }

    /****
     * 获取患者数目
     */
    public void requestModelUserCode(HttpModelHandler<String> handler){
        execute(RequestApi.requestUserInviteCode(token.token),handler);
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
