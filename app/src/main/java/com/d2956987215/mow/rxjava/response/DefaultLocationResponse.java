package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by lq on 2018/3/22.
 */

public class DefaultLocationResponse extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * addressId : 1
         * userId : 40
         * userName : ttt
         * userPhone : 15617859167
         * areaId1 : 410000
         * areaId2 : 410100
         * areaId3 : 410105
         * communityId : 20925
         * address : xxxxxx
         * isDefault : 1
         * areaName1 : 河南省
         * areaName2 : 郑州市
         * areaName3 : 金水区
         * communityName : 花园路街道
         */
        private int addressId;
        private int userId;
        private String userName;
        private String userPhone;
        private int areaId1;
        private int areaId2;
        private int areaId3;
        private int communityId;
        private String address;
        private int isDefault;
        private String areaName1;
        private String areaName2;
        private String areaName3;
        private String communityName;

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public int getAreaId1() {
            return areaId1;
        }

        public void setAreaId1(int areaId1) {
            this.areaId1 = areaId1;
        }

        public int getAreaId2() {
            return areaId2;
        }

        public void setAreaId2(int areaId2) {
            this.areaId2 = areaId2;
        }

        public int getAreaId3() {
            return areaId3;
        }

        public void setAreaId3(int areaId3) {
            this.areaId3 = areaId3;
        }

        public int getCommunityId() {
            return communityId;
        }

        public void setCommunityId(int communityId) {
            this.communityId = communityId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public String getAreaName1() {
            return areaName1;
        }

        public void setAreaName1(String areaName1) {
            this.areaName1 = areaName1;
        }

        public String getAreaName2() {
            return areaName2;
        }

        public void setAreaName2(String areaName2) {
            this.areaName2 = areaName2;
        }

        public String getAreaName3() {
            return areaName3;
        }

        public void setAreaName3(String areaName3) {
            this.areaName3 = areaName3;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }
    }
}
