package com.rolle.doctor.domain;

import com.android.common.domain.ResponseMessage;

import java.util.List;

/**
 * @author Hua_
 * @Description: 预约
 * @date 2015/8/7 - 10:23
 */
public class Appointment extends ResponseMessage{

    private List<Item> list;

    public class Item{
        private Long id;
        private  String doctorId;
        private  String patientId;
        private  String description;
        private  String clinicDate;
        private  String time;
        private  String mobile;
        private  String remark;
        private  String clinicHospital;
        private  String clinicBlock;
        private  String clinicRoom;
        private  String reservationNumber;
        private  String createTime;
        private  String statusName;
        private  String headImage;
        private  String nickname;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getClinicDate() {
            return clinicDate;
        }

        public void setClinicDate(String clinicDate) {
            this.clinicDate = clinicDate;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getClinicHospital() {
            return clinicHospital;
        }

        public void setClinicHospital(String clinicHospital) {
            this.clinicHospital = clinicHospital;
        }

        public String getClinicBlock() {
            return clinicBlock;
        }

        public void setClinicBlock(String clinicBlock) {
            this.clinicBlock = clinicBlock;
        }

        public String getClinicRoom() {
            return clinicRoom;
        }

        public void setClinicRoom(String clinicRoom) {
            this.clinicRoom = clinicRoom;
        }

        public String getReservationNumber() {
            return reservationNumber;
        }

        public void setReservationNumber(String reservationNumber) {
            this.reservationNumber = reservationNumber;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }
}
