package com.rolle.doctor.domain;

import com.android.common.domain.ResponseMessage;

import java.io.Serializable;
import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/5/15 - 14:13
 */
public class WalletBill extends ResponseMessage{

    public List<Item> list;

    public static class Item implements Serializable{
        public int id;
        public int userId;
        /******
         等待审核
         交易成功
         交易失败
         */
        public int  typeId;
        public int  statusId;
        /*****  ****/
        public String typeName;
        /*****交易时间  ****/
        public String tradingTime;
        public String tradingDate;
        /*****  转账方  ****/
        public String tradingUserId;
        /***** 交易说明  title ****/
        public String tradingExplain;
        /*****  交易金额  + -    ****/
        public String accountAmountChange;
        /***** 交易金额 ****/
        public String accountAmount;
        /*****  ****/
        public String frozenAmountChange;
        /*****  ****/
        public String frozenAmount;
        /*****  备注  ****/
        public String remark;
        public long yearMonth;
        public String month;
    }

}
