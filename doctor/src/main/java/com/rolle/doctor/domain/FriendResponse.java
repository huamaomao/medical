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

    public List<Item> friendList;

    @Table("friendList")
    public static class Item implements Parcelable {
        public int id;
        public String nickname;
        public String headImage;
        public String email;
        public String intro;
        public String address;
        public String regionId;
        /**
         * 工作地址*
         */
        public String workAddress;
        /**
         * 地区*
         */
        public String workRegionId;
        /**
         * 医生职称**
         */
        public String jobId;
        /**
         * 所在医院*
         */
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
        /**
         * **二维码信息***
         */
        public String qrCode;
        /**
         * **简介***
         */
        public String describe;
        /**
         * **工作地址***
         */
        public String jobAddress;
        /**
         * **所在医院***
         */
        public String hospitalAddress;
        /**
         * **医生职称***
         */
        public String doctorTitle;
        /**
         * **所在科室***
         */
        public String department;
        /**
         * **专长***
         */
        public String specialty;
        public String mobile;
        public String status;
        public String home;
        public String typeId;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.nickname);
            dest.writeString(this.headImage);
            dest.writeString(this.email);
            dest.writeString(this.intro);
            dest.writeString(this.address);
            dest.writeString(this.regionId);
            dest.writeString(this.workAddress);
            dest.writeString(this.workRegionId);
            dest.writeString(this.jobId);
            dest.writeString(this.hospitalName);
            dest.writeString(this.tel);
            dest.writeString(this.sex);
            dest.writeString(this.age);
            dest.writeString(this.userName);
            dest.writeString(this.remarks);
            dest.writeString(this.type);
            dest.writeString(this.minNum);
            dest.writeString(this.maxNum);
            dest.writeString(this.token);
            dest.writeString(this.noteName);
            dest.writeString(this.qrCode);
            dest.writeString(this.describe);
            dest.writeString(this.jobAddress);
            dest.writeString(this.hospitalAddress);
            dest.writeString(this.doctorTitle);
            dest.writeString(this.department);
            dest.writeString(this.specialty);
            dest.writeString(this.mobile);
            dest.writeString(this.status);
            dest.writeString(this.home);
            dest.writeString(this.typeId);
        }

        public Item() {
        }

        private Item(Parcel in) {
            this.id = in.readInt();
            this.nickname = in.readString();
            this.headImage = in.readString();
            this.email = in.readString();
            this.intro = in.readString();
            this.address = in.readString();
            this.regionId = in.readString();
            this.workAddress = in.readString();
            this.workRegionId = in.readString();
            this.jobId = in.readString();
            this.hospitalName = in.readString();
            this.tel = in.readString();
            this.sex = in.readString();
            this.age = in.readString();
            this.userName = in.readString();
            this.remarks = in.readString();
            this.type = in.readString();
            this.minNum = in.readString();
            this.maxNum = in.readString();
            this.token = in.readString();
            this.noteName = in.readString();
            this.qrCode = in.readString();
            this.describe = in.readString();
            this.jobAddress = in.readString();
            this.hospitalAddress = in.readString();
            this.doctorTitle = in.readString();
            this.department = in.readString();
            this.specialty = in.readString();
            this.mobile = in.readString();
            this.status = in.readString();
            this.home = in.readString();
            this.typeId = in.readString();
        }

        public static final Creator<Item> CREATOR = new Creator<Item>() {
            public Item createFromParcel(Parcel source) {
                return new Item(source);
            }

            public Item[] newArray(int size) {
                return new Item[size];
            }
        };
    }
}
