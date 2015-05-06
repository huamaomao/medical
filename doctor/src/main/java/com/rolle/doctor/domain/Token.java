package com.rolle.doctor.domain;

import com.android.common.domain.ResponseMessage;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/21 - 13:59
 */
public class Token extends ResponseMessage {
    public int id=1;
    public String token;
    public String tel;
    public int userId;
    public String pwd;
    public int status;
    public static final int STATUS_LOGIN=0;
    public static final int STATUS_NO=1;

    /*******
     * 0   1-3
     */


}
