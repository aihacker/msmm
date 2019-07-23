package com.d2956987215.mow.rxjava.response;

public class HomeAds extends BaseResponse{

    /**
     * data : {"title":"天天0元购","picUrl":"http://static.fenxianglife.com/indexPromo/6323b0ea3be2811952f9387c7f64cd19.jpg","quan_id":"","needLogin":1,"directType":3,"url":"https://www.maishoumm.com","RoleType":1,"activity_type":0,"activity_name":""}
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
         * title : 天天0元购
         * picUrl : http://static.fenxianglife.com/indexPromo/6323b0ea3be2811952f9387c7f64cd19.jpg
         * quan_id :
         * needLogin : 1
         * directType : 3
         * url : https://www.maishoumm.com
         * RoleType : 1
         * activity_type : 0
         * activity_name :
         */

        private String title;
        private String picUrl;
        private String quan_id;
        private int needLogin;
        private int directType;
        private String url;
        private int RoleType;
        private int is_needrecord;
        private int needrecord;
        private String aid;
        private int popType;

        public int getPopType() {
            return popType;
        }

        public void setPopType(int popType) {
            this.popType = popType;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public int getIs_needrecord() {
            return is_needrecord;
        }

        public void setIs_needrecord(int is_needrecord) {
            this.is_needrecord = is_needrecord;
        }

        public int getNeedrecord() {
            return needrecord;
        }

        public void setNeedrecord(int needrecord) {
            this.needrecord = needrecord;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getQuan_id() {
            return quan_id;
        }

        public void setQuan_id(String quan_id) {
            this.quan_id = quan_id;
        }

        public int getNeedLogin() {
            return needLogin;
        }

        public void setNeedLogin(int needLogin) {
            this.needLogin = needLogin;
        }

        public int getDirectType() {
            return directType;
        }

        public void setDirectType(int directType) {
            this.directType = directType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getRoleType() {
            return RoleType;
        }

        public void setRoleType(int RoleType) {
            this.RoleType = RoleType;
        }

    }
}
