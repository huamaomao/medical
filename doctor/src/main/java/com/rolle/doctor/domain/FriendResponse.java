package com.rolle.doctor.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.common.domain.ResponseMessage;
import com.litesuits.orm.db.annotation.Table;

import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/4/28 - 9:42
 */
public class FriendResponse extends ResponseMessage {

    public List<Item> list;

    @Table("friend")
    public static class Item{
        public String id;
        public String name;
        public String typeId;

    }
}
