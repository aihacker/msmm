package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by lq on 2018/3/15.
 */

public class GuideResponse extends BaseResponse {


    /**
     * data : {"list":[{"title":"520","picUrl":"https://images.maishoumm.com/images/2/2019/05/YX3PPPAS6HWOJWKHHA1J6SKOK6LEA3.jpg","picUrlx":"https://images.maishoumm.com/images/2/2019/05/QDZBLXIBIZ2ZVY23X72AD2DXX2YXRI.jpg","aid":15,"quan_id":"","needLogin":0,"needrecord":0,"directType":5,"url":"","created_at":1557986788}],"needrecord":0}
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
         * list : [{"title":"520","picUrl":"https://images.maishoumm.com/images/2/2019/05/YX3PPPAS6HWOJWKHHA1J6SKOK6LEA3.jpg","picUrlx":"https://images.maishoumm.com/images/2/2019/05/QDZBLXIBIZ2ZVY23X72AD2DXX2YXRI.jpg","aid":15,"quan_id":"","needLogin":0,"needrecord":0,"directType":5,"url":"","created_at":1557986788}]
         * needrecord : 0
         */

        private int needrecord;
        private List<ListBean> list;

        public int getNeedrecord() {
            return needrecord;
        }

        public void setNeedrecord(int needrecord) {
            this.needrecord = needrecord;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * title : 520
             * picUrl : https://images.maishoumm.com/images/2/2019/05/YX3PPPAS6HWOJWKHHA1J6SKOK6LEA3.jpg
             * picUrlx : https://images.maishoumm.com/images/2/2019/05/QDZBLXIBIZ2ZVY23X72AD2DXX2YXRI.jpg
             * aid : 15
             * quan_id :
             * needLogin : 0
             * needrecord : 0
             * directType : 5
             * url :
             * created_at : 1557986788
             */

            private String title;
            private String picUrl;
            private String picUrlx;
            private String aid;
            private String quan_id;
            private int needLogin;
            private int needrecord;
            private int directType;
            private String url;
            private int created_at;

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

            public String getPicUrlx() {
                return picUrlx;
            }

            public void setPicUrlx(String picUrlx) {
                this.picUrlx = picUrlx;
            }

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
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

            public int getNeedrecord() {
                return needrecord;
            }

            public void setNeedrecord(int needrecord) {
                this.needrecord = needrecord;
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

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }
        }
    }
}
