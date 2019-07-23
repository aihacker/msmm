package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class MsgEverydayResponse extends BaseResponse {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 25
         * title : 正品老北京足贴排毒祛湿减脂睡眠不足助眠安神失眠脚底艾草去湿气
         * content : 正品老北京足贴排毒祛湿减脂睡眠不足助眠安神失眠脚底艾草去湿气
         * aid : 591583304350
         * quan_id : 2ff4e78575504c87a72f211dcad61319
         * pushTime : 1558492957
         * picUrl : http://maishoumm.oss-cn-beijing.aliyuncs.com/images/2/2019/05/P2CRFCUI9CC89DVD1GV1B7S49CSDFD.jpg
         * url :
         * activityStartTime : 1557737447
         * activityEndTime : 1559811060
         * unreadStatus : 1
         */

        private String id;
        private String title;
        private String content;
        private String aid;
        private String quan_id;
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

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getQuan_id() {
            return quan_id;
        }

        public void setQuan_id(String quan_id) {
            this.quan_id = quan_id;
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
