package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class MsgMoneyListResponse extends BaseResponse{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderAmount : 16
         * content : 您的粉丝【口口口口园】在2019-03-28 15:03:27推广成功,预估普通佣金:0.16元,预计结算时间：收货后次月25日结算
         * createTime : 1553756675
         * orderid : 271071333384429492
         * sourceName : 淘宝
         * commissionAmount : 16.6
         */

        private String orderAmount;
        private String content;
        private String createTime;
        private String orderid;
        private String sourceName;
        private String commissionAmount;

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getSourceName() {
            return sourceName;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }

        public String getCommissionAmount() {
            return commissionAmount;
        }

        public void setCommissionAmount(String commissionAmount) {
            this.commissionAmount = commissionAmount;
        }
    }
}
