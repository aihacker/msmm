package com.d2956987215.mow.rxjava.response;

/**
 * Created by lq on 2018/3/26.
 */

public class GetRedPacket extends BaseResponse {


    /**
     * data : {"couponName":"优惠红包","couponId":"1","couponMoney":"100","couponDes":"优惠红包","dataFlag":"1"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * couponName : 优惠红包
         * couponId : 1
         * couponMoney : 100
         * couponDes : 优惠红包
         * dataFlag : 1
         */

        private String couponName;
        private String couponId;
        private String couponMoney;
        private String couponDes;
        private String dataFlag;

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getCouponMoney() {
            return couponMoney;
        }

        public void setCouponMoney(String couponMoney) {
            this.couponMoney = couponMoney;
        }

        public String getCouponDes() {
            return couponDes;
        }

        public void setCouponDes(String couponDes) {
            this.couponDes = couponDes;
        }

        public String getDataFlag() {
            return dataFlag;
        }

        public void setDataFlag(String dataFlag) {
            this.dataFlag = dataFlag;
        }
    }
}
