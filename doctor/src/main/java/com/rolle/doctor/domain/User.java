package com.rolle.doctor.domain;

import com.android.common.domain.ResponseMessage;
import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 用户
 */
@Table("user")
public class User implements Serializable{
    public int id;
    public String nickname;
    public String photo;
    public String photoId;
    public String email;
    public String intro;
    public String idCardNo;
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
    public String remarks;
    public int resId;
    public String type;
    public String minNum;
    public String maxNum;
    public String token;
    public String time="中午12：00";
    /*****头像地址****/
    public String headImage;
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
    /*****身份证图片地址****/
    public String idImage;
    /*****营业执照图片地址****/
    public String businessLicense;
    public String mobile;
    public String status;
    public String home;
    public String typeId;
    public int state=STATUS_REGISTER;
    /*****注册成功***/
    public static final int STATUS_REGISTER=1;
    /*****第一步***/
    public static final int STATUS_FIRST=2;
    /*****第二步***/
    public static final int STATUS_TWO=3;
    /*****第三步***/
    public static final int STATUS_SUCCESS=4;

    public User() {
    }

    public User(String remarks, String age, String sex, int resId, String nickName,String type) {
        this.remarks = remarks;
        this.age = age;
        this.sex = sex;
        this.resId = resId;
        this.nickname = nickName;
        this.type=type;
    }
    public User(String remarks, String age, String sex, int resId, String nickName,String type,String status) {
        this.remarks = remarks;
        this.age = age;
        this.sex = sex;
        this.resId = resId;
        this.nickname = nickName;
        this.type=type;
        this.status=status;
    }


    public User(int resId, String nickName, String remarks, String sex, String age, String minNum, String maxNum) {
        this.resId = resId;
        this.nickname = nickName;
        this.remarks = remarks;
        this.sex = sex;
        this.age = age;
        this.minNum = minNum;
        this.maxNum = maxNum;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getNickName() {
        return nickname;
    }

    public void setNickName(String nickName) {
        this.nickname = nickName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nickName='" + nickname + '\'' +
                ", photo='" + photo + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", remarks='" + remarks + '\'' +
                ", resId=" + resId +
                ", type='" + type + '\'' +
                ", minNum='" + minNum + '\'' +
                ", maxNum='" + maxNum + '\'' +
                ", time='" + time + '\'' +
                ", headImage='" + headImage + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", describe='" + describe + '\'' +
                ", jobAddress='" + jobAddress + '\'' +
                ", hospitalAddress='" + hospitalAddress + '\'' +
                ", doctorTitle='" + doctorTitle + '\'' +
                ", department='" + department + '\'' +
                ", specialty='" + specialty + '\'' +
                ", idImage='" + idImage + '\'' +
                ", businessLicense='" + businessLicense + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
