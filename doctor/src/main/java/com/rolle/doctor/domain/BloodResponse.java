package com.rolle.doctor.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.common.domain.ResponseMessage;
import com.litesuits.orm.db.annotation.Table;

import java.util.List;
import java.util.Objects;

/**
 * @author Hua_
 * @Description: 血糖记录
 * @date 2015/4/28 - 9:42
 */
public class BloodResponse extends ResponseMessage {

    public String lowSum;
    public String normalSum;
    public String highSum;

}
