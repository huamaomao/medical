package com.rolle.doctor.domain;

import com.android.common.domain.ResponseMessage;

import java.util.List;

/**
 * @author Hua_
 * @Description:
 * @date 2015/7/23 - 10:03
 */
public final class ContactListResponse extends ResponseMessage {
    private List<Item> list;

    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }

    public static class Item{
        private String id;
        private String typeId;
        private String sex;
        private String statusId;
        private String nickname;
        private String mobile;
        private int isAudit;
        private String age;
        private String headImage;
        private long createTime;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getStatusId() {
            return statusId;
        }

        public void setStatusId(String statusId) {
            this.statusId = statusId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIsAudit() {
            return isAudit;
        }

        public void setIsAudit(int isAudit) {
            this.isAudit = isAudit;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
