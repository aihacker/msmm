package com.d2956987215.mow.rxjava.response;


import java.util.List;

public class SelfSupportResponse extends BaseResponse{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 一号店
         * pic : http://images.maishoumm.com/dianpu.png
         * pid : mm_32490747_43608460_27453650408
         * seller_id : 1
         */

        private String title;
        private String pic;
        private String pid;
        private String seller_id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }
    }
}
