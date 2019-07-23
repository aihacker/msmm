package com.d2956987215.mow.wxapi;

import com.d2956987215.mow.rxjava.response.BaseResponse;

import com.d2956987215.mow.rxjava.response.BaseResponse;

/**
 * crate by lq 2018.1.10
 */

public class WXPayResponse extends BaseResponse {
    /**
     * data : {"orderno":"cc43608951","str":{"appId":"wxa68e3f31ba28d2c5","nonceStr":"vYd6iW7yQOrOWTOH","packAge":"prepay_id=wx201708111521077a6f8c2f2f0213957823","paySign":"4C0FA6E3D7E5AA8F64AFD7BEFC2E7D76","signType":"MD5","timeStamp":"1502436067"}}
     */

    public ResultBean data;

    public static class ResultBean {
        /**
         * orderno : cc43608951
         * str : {"appId":"wxa68e3f31ba28d2c5","nonceStr":"vYd6iW7yQOrOWTOH","packAge":"prepay_id=wx201708111521077a6f8c2f2f0213957823","paySign":"4C0FA6E3D7E5AA8F64AFD7BEFC2E7D76","signType":"MD5","timeStamp":"1502436067"}
         */

        public String orderno;
        public StrBean str;

        public static class StrBean {
            /**
             * appId : wxa68e3f31ba28d2c5
             * nonceStr : vYd6iW7yQOrOWTOH
             * packAge : prepay_id=wx201708111521077a6f8c2f2f0213957823
             * paySign : 4C0FA6E3D7E5AA8F64AFD7BEFC2E7D76
             * signType : MD5
             * timeStamp : 1502436067
             */

            public String appId;
            public String nonceStr;
            public String packAge;
            public String paySign;
            public String signType;
            public String timeStamp;
        }
    }
}
