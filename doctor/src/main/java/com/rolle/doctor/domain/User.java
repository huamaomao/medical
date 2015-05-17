package com.rolle.doctor.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.common.domain.ResponseMessage;
import com.litesuits.orm.db.annotation.Table;
import com.rolle.doctor.util.Constants;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 用户
 */
@Table("user")
public class User extends ResponseMessage  implements Serializable,Parcelable {
    /****message***/
    public String message;
    public String date;
    public int messageNum;

    /****好友关系***/
    public int friendId;

    public int id;
    public String nickname;
    public String noteName;
    public String photo;
    public String photoId;
    public String email;
    public String intro;
    public String address;
    public String regionId;


    /***工作地址**/
    public String workAddress;
    /***地区**/
    public String workRegionId;
    public String workRegion;
    /**职称***/
    public String jobId;
    /**科室***/
    public String departmentId;
    /***所在医院**/
    public String hospitalName;
    public String tel;
    public String sex= Constants.SEX_BOY;
    public String age;
    public String userName;
    public String minNum;
    public String maxNum;
    public String token;
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
    /****创建时间***/
    public Long createTime;
    public int state=STATUS_REGISTER;
    public int updateState=NO_UPDATE;
    public static final int UPDATE=0;
    public static final int NO_UPDATE=1;
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

    public DoctorDetail doctorDetail=new DoctorDetail();

    public static class DoctorDetail implements Parcelable{
        public int id;
        public String workAddress;
        public String workRegionId;
        public String hospitalName;
        public String jobId;
        public String departmentsId;
        public String speciality;
        public String createTime;
        public String disabled;
        public String jobAddress;
        public String doctorTitle;
        public String department;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.workAddress);
            dest.writeString(this.workRegionId);
            dest.writeString(this.hospitalName);
            dest.writeString(this.jobId);
            dest.writeString(this.departmentsId);
            dest.writeString(this.speciality);
            dest.writeString(this.createTime);
            dest.writeString(this.disabled);
            dest.writeString(this.jobAddress);
            dest.writeString(this.doctorTitle);
            dest.writeString(this.department);
        }

        public DoctorDetail() {
        }

        private DoctorDetail(Parcel in) {
            this.workAddress = in.readString();
            this.workRegionId = in.readString();
            this.hospitalName = in.readString();
            this.jobId = in.readString();
            this.departmentsId = in.readString();
            this.speciality = in.readString();
            this.createTime = in.readString();
            this.disabled = in.readString();
            this.jobAddress = in.readString();
            this.doctorTitle = in.readString();
            this.department = in.readString();
        }

        public  static final Creator<DoctorDetail> CREATOR = new Creator<DoctorDetail>() {
            public DoctorDetail createFromParcel(Parcel source) {
                return new DoctorDetail(source);
            }

            public DoctorDetail[] newArray(int size) {
                return new DoctorDetail[size];
            }
        };
    }
    public PatientDetail patientDetail=new PatientDetail();

    public static class PatientDetail implements Parcelable{
        public int id;
        public String createTime;
        public String height;
        public String weight;
        public String diseaseTypeId;
        public String drugAllergy;
        public String health;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.createTime);
            dest.writeString(this.height);
            dest.writeString(this.weight);
            dest.writeString(this.diseaseTypeId);
        }

        public PatientDetail() {
        }

        private PatientDetail(Parcel in) {
            this.createTime = in.readString();
            this.height = in.readString();
            this.weight = in.readString();
            this.diseaseTypeId = in.readString();
        }

        public static final Creator<PatientDetail> CREATOR = new Creator<PatientDetail>() {
            public PatientDetail createFromParcel(Parcel source) {
                return new PatientDetail(source);
            }

            public PatientDetail[] newArray(int size) {
                return new PatientDetail[size];
            }
        };
    }


    /****
     * 是否要提交
     */
    public void setUpdateStatus(){
        this.updateState=UPDATE;
    }
    public void setNoUpdateStatus(){
        this.updateState=NO_UPDATE;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.friendId);
        dest.writeInt(this.id);
        dest.writeString(this.nickname);
        dest.writeString(this.noteName);
        dest.writeString(this.photo);
        dest.writeString(this.photoId);
        dest.writeString(this.email);
        dest.writeString(this.address);
        dest.writeString(this.intro);
        dest.writeString(this.regionId);
        dest.writeString(this.workAddress);
        dest.writeString(this.workRegionId);
        dest.writeString(this.jobId);
        dest.writeString(this.departmentId);
        dest.writeString(this.hospitalName);
        dest.writeString(this.tel);
        dest.writeString(this.sex);
        dest.writeString(this.age);
        dest.writeString(this.userName);
        dest.writeString(this.minNum);
        dest.writeString(this.maxNum);
        dest.writeString(this.token);
        dest.writeString(this.headImage);
        dest.writeString(this.qrCode);
        dest.writeString(this.describe);
        dest.writeString(this.jobAddress);
        dest.writeString(this.hospitalAddress);
        dest.writeString(this.doctorTitle);
        dest.writeString(this.department);
        dest.writeString(this.birthday);
        dest.writeString(this.weight);
        dest.writeString(this.specialty);
        dest.writeString(this.idImage);
        dest.writeString(this.businessLicense);
        dest.writeString(this.mobile);
        dest.writeString(this.status);
        dest.writeString(this.home);
        dest.writeString(this.typeId);
        dest.writeValue(this.createTime);
        dest.writeInt(this.state);
        dest.writeInt(this.updateState);
        dest.writeParcelable(this.doctorDetail, flags);
        dest.writeParcelable(this.patientDetail, flags);
    }

    private User(Parcel in) {
        this.friendId = in.readInt();
        this.id = in.readInt();
        this.nickname = in.readString();
        this.noteName = in.readString();
        this.photo = in.readString();
        this.photoId = in.readString();
        this.email = in.readString();
        this.address = in.readString();
        this.intro = in.readString();
        this.regionId = in.readString();
        this.workAddress = in.readString();
        this.workRegionId = in.readString();
        this.jobId = in.readString();
        this.departmentId = in.readString();
        this.hospitalName = in.readString();
        this.tel = in.readString();
        this.sex = in.readString();
        this.age = in.readString();
        this.userName = in.readString();
        this.minNum = in.readString();
        this.maxNum = in.readString();
        this.token = in.readString();
        this.headImage = in.readString();
        this.qrCode = in.readString();
        this.describe = in.readString();
        this.jobAddress = in.readString();
        this.hospitalAddress = in.readString();
        this.doctorTitle = in.readString();
        this.department = in.readString();
        this.birthday = in.readString();
        this.weight = in.readString();
        this.specialty = in.readString();
        this.idImage = in.readString();
        this.businessLicense = in.readString();
        this.mobile = in.readString();
        this.status = in.readString();
        this.home = in.readString();
        this.typeId = in.readString();
        this.createTime = (Long) in.readValue(Long.class.getClassLoader());
        this.state = in.readInt();
        this.updateState = in.readInt();
        this.doctorDetail = in.readParcelable(DoctorDetail.class.getClassLoader());
        this.patientDetail = in.readParcelable(PatientDetail.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("message='").append(message).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", messageNum=").append(messageNum);
        sb.append(", friendId=").append(friendId);
        sb.append(", id=").append(id);
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append(", noteName='").append(noteName).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", photoId='").append(photoId).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", intro='").append(intro).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", regionId='").append(regionId).append('\'');
        sb.append(", workAddress='").append(workAddress).append('\'');
        sb.append(", workRegionId='").append(workRegionId).append('\'');
        sb.append(", jobId='").append(jobId).append('\'');
        sb.append(", departmentId='").append(departmentId).append('\'');
        sb.append(", hospitalName='").append(hospitalName).append('\'');
        sb.append(", tel='").append(tel).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", age='").append(age).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", minNum='").append(minNum).append('\'');
        sb.append(", maxNum='").append(maxNum).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", headImage='").append(headImage).append('\'');
        sb.append(", qrCode='").append(qrCode).append('\'');
        sb.append(", describe='").append(describe).append('\'');
        sb.append(", jobAddress='").append(jobAddress).append('\'');
        sb.append(", hospitalAddress='").append(hospitalAddress).append('\'');
        sb.append(", doctorTitle='").append(doctorTitle).append('\'');
        sb.append(", department='").append(department).append('\'');
        sb.append(", birthday='").append(birthday).append('\'');
        sb.append(", weight='").append(weight).append('\'');
        sb.append(", specialty='").append(specialty).append('\'');
        sb.append(", idImage='").append(idImage).append('\'');
        sb.append(", businessLicense='").append(businessLicense).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", home='").append(home).append('\'');
        sb.append(", typeId='").append(typeId).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", state=").append(state);
        sb.append(", updateState=").append(updateState);
        sb.append(", doctorDetail=").append(doctorDetail);
        sb.append(", patientDetail=").append(patientDetail);
        sb.append('}');
        return sb.toString();
    }
}
