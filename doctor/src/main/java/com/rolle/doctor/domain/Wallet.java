package com.rolle.doctor.domain;

import com.android.common.domain.ResponseMessage;

/**
 * @author Hua_
 * @Description: 钱包
 * @date 2015/5/11 - 19:41
 */
public class Wallet extends ResponseMessage{
    public int id;
    public String accountAmount;
    public String frozenAmount;
    public String password;
}
