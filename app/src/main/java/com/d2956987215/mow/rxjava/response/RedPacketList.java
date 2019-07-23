package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by lq on 2018/3/28.
 */

public class RedPacketList extends BaseResponse {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 12
         * couponId : 1
         * couponName : 优惠红包
         * couponType : 1
         * couponMoney : 100
         * spendMoney : 0
         * validStartTime : null
         * validEndTime : null
         * receiveTime : 2018-03-28 14:50:47
         * couponStatus : 1
         */

        private String id;
        private String couponId;
        private String couponName;
        private String couponType;
        private String couponMoney;
        private String spendMoney;
        private Object validStartTime;
        private Object validEndTime;
        private String receiveTime;
        private String couponStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getCouponType() {
            return couponType;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public String getCouponMoney() {
            return couponMoney;
        }

        public void setCouponMoney(String couponMoney) {
            this.couponMoney = couponMoney;
        }

        public String getSpendMoney() {
            return spendMoney;
        }

        public void setSpendMoney(String spendMoney) {
            this.spendMoney = spendMoney;
        }

        public Object getValidStartTime() {
            return validStartTime;
        }

        public void setValidStartTime(Object validStartTime) {
            this.validStartTime = validStartTime;
        }

        public Object getValidEndTime() {
            return validEndTime;
        }

        public void setValidEndTime(Object validEndTime) {
            this.validEndTime = validEndTime;
        }

        public String getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getCouponStatus() {
            return couponStatus;
        }

        public void setCouponStatus(String couponStatus) {
            this.couponStatus = couponStatus;
        }
    }
}
