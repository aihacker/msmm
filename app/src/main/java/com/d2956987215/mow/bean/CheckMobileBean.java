package com.d2956987215.mow.bean;

import com.d2956987215.mow.rxjava.response.BaseResponse;

public class CheckMobileBean extends BaseResponse {


    /**
     * status_code : 200
     * data : {"isregister":1}
     * message : 手机号已注册请登录！
     */

    private DataBean data;
    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * isregister : 1
         */

        private int isregister;

        public int getIsregister() {
            return isregister;
        }

        public void setIsregister(int isregister) {
            this.isregister = isregister;
        }
    }
}
