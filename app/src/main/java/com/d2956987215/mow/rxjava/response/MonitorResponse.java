package com.d2956987215.mow.rxjava.response;

public class MonitorResponse extends BaseResponse{

    /**
     * data : {"title":"联系管理员","text":"联系管理员","tj_wx":"maishou68","tj_qrcode":"http://www.maishoumm.com/attachment/images/2/2017/04/UO6Z2XHXVCH5IKX4XYIU9C12II2GQC.jpg"}
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
         * title : 联系管理员
         * text : 联系管理员
         * tj_wx : maishou68
         * tj_qrcode : http://www.maishoumm.com/attachment/images/2/2017/04/UO6Z2XHXVCH5IKX4XYIU9C12II2GQC.jpg
         */

        private String title;
        private String text;
        private String tj_wx;
        private String tj_qrcode;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTj_wx() {
            return tj_wx;
        }

        public void setTj_wx(String tj_wx) {
            this.tj_wx = tj_wx;
        }

        public String getTj_qrcode() {
            return tj_qrcode;
        }

        public void setTj_qrcode(String tj_qrcode) {
            this.tj_qrcode = tj_qrcode;
        }
    }
}
