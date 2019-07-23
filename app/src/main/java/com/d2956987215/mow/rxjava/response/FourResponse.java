package com.d2956987215.mow.rxjava.response;


import java.util.List;

public class FourResponse extends BaseResponse{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * text : 限时抢购
         * desc : 最后疯抢一小时
         * icon : http://msapi.maishoumm.com/uploads/gcatimg/1531134122785743.png
         * type : http://msapi.maishoumm.com/uploads/gcatimg/1531137624280401.png
         * icon1 : http://msapi.maishoumm.com/uploads/gcatimg/1531137897397590.png
         */

        private String text;
        private String desc;
        private String icon;
        private String type;
        private String icon1;
        private String pic;


        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIcon1() {
            return icon1;
        }

        public void setIcon1(String icon1) {
            this.icon1 = icon1;
        }
    }
}
