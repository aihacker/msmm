package com.d2956987215.mow.rxjava.response;


import java.io.Serializable;

public class SaoMaResponse extends BaseResponse implements Serializable{


    /**
     * data : {"id":105427,"nickname":"糖糖冷读博士","avatar":"https://wwc.alicdn.com/avatar/getAvatar.do?userId=1105393400&width=160&height=160&type=sns","realname":"","mobile":"13915268162","mobileverify":"1","deadline":"2194562625"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 105427
         * nickname : 糖糖冷读博士
         * avatar : https://wwc.alicdn.com/avatar/getAvatar.do?userId=1105393400&width=160&height=160&type=sns
         * realname :
         * mobile : 13915268162
         * mobileverify : 1
         * deadline : 2194562625
         */

        private int id;
        private String nickname;
        private String avatar;
        private String realname;
        private String mobile;
        private String mobileverify;
        private String deadline;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobileverify() {
            return mobileverify;
        }

        public void setMobileverify(String mobileverify) {
            this.mobileverify = mobileverify;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }
    }
}
