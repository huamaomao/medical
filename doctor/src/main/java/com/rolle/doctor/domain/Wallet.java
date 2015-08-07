package com.rolle.doctor.domain;

import com.android.common.domain.ResponseMessage;

import java.io.Serializable;
import java.util.List;

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
    public List<Item> list;
    public  static final class Item implements Serializable{
        private static final long serialVersionUID = 1229260615062906386L;
        public int id;
        public int userId;
        public String userName;
        public String email;
        public String mobile;
        public String createTime;
        public String type;
        public String name;
        public String account;
        public Boolean isDefault;
    }
}
