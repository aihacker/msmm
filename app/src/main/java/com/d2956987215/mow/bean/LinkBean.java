package com.d2956987215.mow.bean;

import com.d2956987215.mow.rxjava.response.BaseResponse;

public class LinkBean extends BaseResponse {


    /**
     * data : {"type":0,"IsLink":0,"title":"测试"}
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
         * type : 0
         * IsLink : 0
         * title : 测试
         */

        private int type;
        private int IsLink;
        private String title;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIsLink() {
            return IsLink;
        }

        public void setIsLink(int IsLink) {
            this.IsLink = IsLink;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
