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
    public String userName;
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
    public String birthday;
    public String weight;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getWorkRegionId() {
        return workRegionId;
    }

    public void setWorkRegionId(String workRegionId) {
        this.workRegionId = workRegionId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMinNum() {
        return minNum;
    }

    public void setMinNum(String minNum) {
        this.minNum = minNum;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getJobAddress() {
        return jobAddress;
    }

    public void setJobAddress(String jobAddress) {
        this.jobAddress = jobAddress;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getDoctorTitle() {
        return doctorTitle;
    }

    public void setDoctorTitle(String doctorTitle) {
        this.doctorTitle = doctorTitle;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static int getStatusRegister() {
        return STATUS_REGISTER;
    }

    public static int getStatusFirst() {
        return STATUS_FIRST;
    }

    public static int getStatusTwo() {
        return STATUS_TWO;
    }

    public static int getStatusSuccess() {
        return STATUS_SUCCESS;
    }
}
