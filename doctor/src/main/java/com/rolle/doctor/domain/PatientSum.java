package com.rolle.doctor.domain;

import com.android.common.domain.ResponseMessage;

/**
 * @author Hua_
 * @Description: 患者数目
 * @date 2015/4/22 - 13:41
 */
public class PatientSum extends ResponseMessage {
    public String friendSum;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PaitentSum{");
        sb.append("friendSum='").append(friendSum).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
