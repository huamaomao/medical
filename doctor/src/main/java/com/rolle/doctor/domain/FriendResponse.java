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
        /***������ַ**/
        public String workAddress;
        /***����**/
        public String workRegionId;
        /**ҽ��ְ��***/
        public String jobId;
        /***����ҽԺ**/
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

        /*****��ά����Ϣ****/
        public String qrCode;
        /*****���****/
        public String describe;
        /*****������ַ****/
        public String jobAddress;
        /*****����ҽԺ****/
        public String hospitalAddress;
        /*****ҽ��ְ��****/
        public String doctorTitle;
        /*****���ڿ���****/
        public String department;
        /*****ר��****/
        public String specialty;
        public String mobile;
        public String status;
        public String home;
        public String typeId;

    }
}
