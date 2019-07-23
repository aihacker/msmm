package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class MsgActivityResponse extends BaseResponse{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 4650
         * title : 2019年天猫520礼遇季——天猫主会场
         * content : 2019年天猫520礼遇季——天猫主会场
         * pushTime : 1558603455
         * picUrl : http://maishoumm.oss-cn-beijing.aliyuncs.com/images/2/2019/05/PS9ZN4A8AW25FQY2Y5WR54BBWQBT8Q.jpg
         * url : https://msapi.maishoumm.com/1212/xsl.html?user_id=635118
         * activityStartTime : 1555137720
         * activityEndTime : 1559274120
         * unreadStatus : 0
         */

        private String id;
        private String title;
        private String content;
        private long pushTime;
        private String picUrl;
        private String url;
        private long activityStartTime;
        private long activityEndTime;
        private int unreadStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getPushTime() {
            return pushTime;
        }

        public void setPushTime(long pushTime) {
            this.pushTime = pushTime;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getActivityStartTime() {
            return activityStartTime;
        }

        public void setActivityStartTime(long activityStartTime) {
            this.activityStartTime = activityStartTime;
        }

        public long getActivityEndTime() {
            return activityEndTime;
        }

        public void setActivityEndTime(long activityEndTime) {
            this.activityEndTime = activityEndTime;
        }

        public int getUnreadStatus() {
            return unreadStatus;
        }

        public void setUnreadStatus(int unreadStatus) {
            this.unreadStatus = unreadStatus;
        }
    }
}
