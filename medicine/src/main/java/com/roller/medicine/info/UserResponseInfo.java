package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

/**
 * @author Hua_
 * @Description:
 * @date 2015/5/20 - 23:40
 */
public class UserResponseInfo extends ResponseMessage {
    public UserInfo user;

    @Override
    public String toString() {
        return "UserResponseInfo{" +
                "user=" + user +
                "} " + super.toString();
    }
}
