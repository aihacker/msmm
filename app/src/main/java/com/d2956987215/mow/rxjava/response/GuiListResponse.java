package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class GuiListResponse extends BaseResponse {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 19
         * type : 1
         * article_title : 买手实操须知（里面有红包）
         * resp_desc : 和你想的可能不一样，所有买手，务必要看，否则会吃大亏！
         * resp_img : images/2/2017/03/N2KIW0MK9EWWWMBEM48KKB30W2ESME.png
         * video_url : null
         * article_readnum : 34
         * article_likenum : 0
         * article_category : 3
         */

        private int id;
        private String type;
        private String article_title;
        private String resp_desc;
        private String resp_img;
        private Object video_url;
        private String article_readnum;
        private int article_likenum;
        private String article_category;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getArticle_title() {
            return article_title;
        }

        public void setArticle_title(String article_title) {
            this.article_title = article_title;
        }

        public String getResp_desc() {
            return resp_desc;
        }

        public void setResp_desc(String resp_desc) {
            this.resp_desc = resp_desc;
        }

        public String getResp_img() {
            return resp_img;
        }

        public void setResp_img(String resp_img) {
            this.resp_img = resp_img;
        }

        public Object getVideo_url() {
            return video_url;
        }

        public void setVideo_url(Object video_url) {
            this.video_url = video_url;
        }

        public String getArticle_readnum() {
            return article_readnum;
        }

        public void setArticle_readnum(String article_readnum) {
            this.article_readnum = article_readnum;
        }

        public int getArticle_likenum() {
            return article_likenum;
        }

        public void setArticle_likenum(int article_likenum) {
            this.article_likenum = article_likenum;
        }

        public String getArticle_category() {
            return article_category;
        }

        public void setArticle_category(String article_category) {
            this.article_category = article_category;
        }
    }
}
