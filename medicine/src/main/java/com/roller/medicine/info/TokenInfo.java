package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/21 - 13:59
 */
public class TokenInfo extends ResponseMessage {
    public int id=1;
    public String token;
    public String tel;
    public int userId;
    public String pwd;
    public int status=STATUS_LOGINOUT;
    public static final int STATUS_LOGIN=0;
    public static final int STATUS_LOGINOUT=1;

    /***8
     * 是否登陆
     * @return
     */
    public boolean isLogin(){
        return status==STATUS_LOGIN?true:false;
    }

    public void setLogin(){
        status=STATUS_LOGIN;
    }
    public void setLoginOut(){
        status=STATUS_LOGINOUT;
    }
    /*******
     * 0   1-3
     */
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Token{");
        sb.append("status=").append(status);
        sb.append(", id=").append(id);
        sb.append(", tel='").append(tel).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", pwd='").append(pwd).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
