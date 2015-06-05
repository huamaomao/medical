package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

import java.util.List;

/**
 * @author Hua_
 * @Description: 医生详细信息
 * @date 2015/5/26 - 21:19
 */
public class DoctorDetialInfo extends ResponseMessage {
    public UserInfo user;
    public List<RecommendedInfo.Item> list;
}
