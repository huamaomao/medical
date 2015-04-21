package com.rolle.doctor.domain;

/**
 * 用户
 */
public class User {
    private String id;
    private String nickName;
    private String photo;
    private String sex;
    private String age;
    private String remarks;
    public int resId;
    public String type;
    public String minNum;
    public String maxNum;
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

    public String status;

    public User() {
    }

    public User(String remarks, String age, String sex, int resId, String nickName,String type) {
        this.remarks = remarks;
        this.age = age;
        this.sex = sex;
        this.resId = resId;
        this.nickName = nickName;
        this.type=type;
    }
    public User(String remarks, String age, String sex, int resId, String nickName,String type,String status) {
        this.remarks = remarks;
        this.age = age;
        this.sex = sex;
        this.resId = resId;
        this.nickName = nickName;
        this.type=type;
        this.status=status;
    }


    public User(int resId, String nickName, String remarks, String sex, String age, String minNum, String maxNum) {
        this.resId = resId;
        this.nickName = nickName;
        this.remarks = remarks;
        this.sex = sex;
        this.age = age;
        this.minNum = minNum;
        this.maxNum = maxNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
