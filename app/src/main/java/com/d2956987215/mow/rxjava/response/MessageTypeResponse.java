package com.d2956987215.mow.rxjava.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageTypeResponse extends BaseResponse {

    private int unreadNum;
    private List<DataBean> data;

    public int getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * message : 您的粉丝【口口口口园】在2019-03-28 15:03:27推广成功,预估普通佣金:0.16元,预计结算时间：收货后次月25日结算
         * typeid : 1
         * unreadStatus : 1
         */

        @SerializedName("message")
        private String messageX;
        private int typeid;
        private int unreadStatus;

        public String getMessageX() {
            return messageX;
        }

        public void setMessageX(String messageX) {
            this.messageX = messageX;
        }

        public int getTypeid() {
            return typeid;
        }

        public void setTypeid(int typeid) {
            this.typeid = typeid;
        }

        public int getUnreadStatus() {
            return unreadStatus;
        }

        public void setUnreadStatus(int unreadStatus) {
            this.unreadStatus = unreadStatus;
        }
    }
}
