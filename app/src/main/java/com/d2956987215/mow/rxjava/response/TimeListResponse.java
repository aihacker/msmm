package com.d2956987215.mow.rxjava.response;


import java.util.List;

public class TimeListResponse extends BaseResponse{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * time : 00:00
         * status : 已开抢
         */

        private String id;
        private String time;
        private String status;
        private boolean selete;

        public boolean isSelete() {
            return selete;
        }

        public void setSelete(boolean selete) {
            this.selete = selete;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
