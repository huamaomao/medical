package com.rolle.doctor.domain;

import com.android.common.domain.ResponseMessage;

import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/7/23 - 10:03
 */
public final class ContactListResponse extends ResponseMessage {
    private List<User> list;
    public List<User> getList() {
        return list;
    }
    public void setList(List<User> list) {
        this.list = list;
    }

}
