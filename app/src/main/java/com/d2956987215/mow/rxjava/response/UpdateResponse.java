package com.d2956987215.mow.rxjava.response;

/**
 * Created by lq on 2018/3/20.
 */

public class UpdateResponse extends BaseResponse {

    /**
     * data : {"id":"1","app_version":1,"content":"新版本震撼来袭"}
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
         * id : 1
         * app_version : 1
         * content : 新版本震撼来袭
         */

        private String id;
        private int app_version;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getApp_version() {
            return app_version;
        }

        public void setApp_version(int app_version) {
            this.app_version = app_version;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
