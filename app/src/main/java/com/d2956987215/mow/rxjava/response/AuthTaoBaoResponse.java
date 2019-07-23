package com.d2956987215.mow.rxjava.response;

public class AuthTaoBaoResponse extends BaseResponse {

    /**
     * data : {"menu":{"dialogtitle":"您的账号目前还不是买手，请升级","righttitle":"立即升级","righttype":3},"Coupon_rul":"https://uland.taobao.com/coupon/edetail?e=4TtDuB7Q5WwGQASttHIRqZ7WnfylDTPha9cN6tmqb%2BwWRJgxS8%2Fxs0OGFXjXf8qJG3S9rDfgY5O%2BYJmTQDwPkZQ5wfGz%2Fu%2BNLL8UyLVw9k0GmY4hFfhtzCZ6Y%2FpkHtT5QS0Flu%2FfbSovkBQlP112cJ5ECHpSy25Ge6L%2Bf9DtnlXwJHGvGkPGRjcK2v3ZVS%2Fe&traceId=0b151de515423640300886834e&union_lens=lensId:0b0175f0_1636_1671c0f6499_bd41&activityId=tigertiger"}
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
         * menu : {"dialogtitle":"您的账号目前还不是买手，请升级","righttitle":"立即升级","righttype":3}
         * Coupon_rul : https://uland.taobao.com/coupon/edetail?e=4TtDuB7Q5WwGQASttHIRqZ7WnfylDTPha9cN6tmqb%2BwWRJgxS8%2Fxs0OGFXjXf8qJG3S9rDfgY5O%2BYJmTQDwPkZQ5wfGz%2Fu%2BNLL8UyLVw9k0GmY4hFfhtzCZ6Y%2FpkHtT5QS0Flu%2FfbSovkBQlP112cJ5ECHpSy25Ge6L%2Bf9DtnlXwJHGvGkPGRjcK2v3ZVS%2Fe&traceId=0b151de515423640300886834e&union_lens=lensId:0b0175f0_1636_1671c0f6499_bd41&activityId=tigertiger
         */

        private String h5url;
        private String title;
        private String text;
        private String btntitle;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getH5url() {
            return h5url;
        }

        public void setH5url(String h5url) {
            this.h5url = h5url;
        }

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

        public String getBtntitle() {
            return btntitle;
        }

        public void setBtntitle(String btntitle) {
            this.btntitle = btntitle;
        }


    }
}
