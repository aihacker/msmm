package com.d2956987215.mow.rxjava.response;

import java.util.List;

/*
 * Created by fizz on 2017/9/4.
 */
// TODO 主要为了测试，一般支付都是在登录过后，该项目支付需要鉴权，所以需要获取到token和uid才能进行支付
public class XiaLaResponse extends BaseResponse {
    private DataBean retval;


    public DataBean getRetval() {
        return retval;
    }

    public void setRetval(DataBean retval) {
        this.retval = retval;
    }

    public static class DataBean {
        private List<KType> type;

        public List<KType> getType() {
            return type;
        }

        public void setType(List<KType> type) {
            this.type = type;
        }
    }
    public static class KType {
        private  String key;
        private  String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
