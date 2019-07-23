package com.d2956987215.mow.rxjava.response;

/*
 * Created by fizz on 2017/9/4.
 */
// TODO 主要为了测试，一般支付都是在登录过后，该项目支付需要鉴权，所以需要获取到token和uid才能进行支付
public class RegisterResponse extends BaseResponse{
    private DataBean data;

    public DataBean getRetval() {
        return data;
    }

    public void setRetval(DataBean retval) {
        this.data = retval;
    }

    public static class DataBean {
        private int id;
        private String token;
        private String roletypes;
        private String deadline;

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getRoletypes() {
            return roletypes;
        }

        public void setRoletypes(String roletypes) {
            this.roletypes = roletypes;
        }

        public int getUid() {
            return id;
        }

        public void setUid(int uid) {
            this.id = uid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
