package com.d2956987215.mow.rxjava.response;

public class UploadFileResponse extends BaseResponse {

    private WxQRBean data;

    public WxQRBean getData() {
        return data;
    }

    public void setData(WxQRBean data) {
        this.data = data;
    }

    public static class WxQRBean{
        private String wxqrcode;

        public String getWxqrcode() {
            return wxqrcode;
        }

        public void setWxqrcode(String wxqrcode) {
            this.wxqrcode = wxqrcode;
        }
    }
}
