package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class ArticleResponse extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 新人必学（一）让你微信加爆的吸粉办法
         * pic_url : http://img04.taobaocdn.com:80/tfscom/TB1wJJAQMHqK1RjSZJnXXbNLpXa
         * article_readnum : 888
         * created_at : 1555122002
         */

        private String title;
        private String pic_url;
        private int article_readnum;
        private long created_at;
        private String web_url;

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public int getArticle_readnum() {
            return article_readnum;
        }

        public void setArticle_readnum(int article_readnum) {
            this.article_readnum = article_readnum;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }
    }
}
