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

    public User() {
    }
    public User( String age, String sex, int resId, String nickName,String minNum,String maxNum) {
        this.age = age;
        this.sex = sex;
        this.resId = resId;
        this.nickName = nickName;
        this.maxNum=maxNum;
        this.minNum=minNum;
    }

    public User(String remarks, String age, String sex, int resId, String nickName,String type) {
        this.remarks = remarks;
        this.age = age;
        this.sex = sex;
        this.resId = resId;
        this.nickName = nickName;
        this.type=type;
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
