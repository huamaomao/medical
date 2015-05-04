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

    @Table("friendList")
    public static class Item{
        public int id;
        public String nickname;
        public String headImage;
        public String email;
        public String intro;
        public String address;
        public String regionId;
        /***工作地址**/
        public String workAddress;
        /***地区**/
        public String workRegionId;
        /**医生职称***/
        public String jobId;
        /***所在医院**/
        public String hospitalName;
        public String tel;
        public String sex;
        public String age;
        public String userName;
        public String remarks;
        public String type;
        public String minNum;
        public String maxNum;
        public String token;
        public String noteName;

        /*****二维码信息****/
        public String qrCode;
        /*****简介****/
        public String describe;
        /*****工作地址****/
        public String jobAddress;
        /*****所在医院****/
        public String hospitalAddress;
        /*****医生职称****/
        public String doctorTitle;
        /*****所在科室****/
        public String department;
        /*****专长****/
        public String specialty;
        public String mobile;
        public String status;
        public String home;
        public String typeId;

    }
}
