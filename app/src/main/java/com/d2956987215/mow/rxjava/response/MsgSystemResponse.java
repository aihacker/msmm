package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class MsgSystemResponse extends BaseResponse{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * content : 恭喜您成功邀请到口口口口园加入［粉象生活］大家庭，快带小伙伴开启粉象创业之旅吧!
         * createTime : 1553756675
         * title : 邀请成功！
         */

        private String content;
        private String createTime;
        private String title;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
