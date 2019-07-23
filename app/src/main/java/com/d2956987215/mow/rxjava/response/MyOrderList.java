package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by lq on 2018/3/26.
 */

public class MyOrderList extends BaseResponse {


    /**
     * data : {"current_page":1,"data":[{"id":"5274998","orderid":"223485795928109296","numid":"536146728914","title":"保温杯女男学生便携水杯子创意时尚大容量304不锈钢商务茶杯定制<font style='color:#FF0000;font-size:0.5rem''>[订单付款]<\/font>","createtime":"2018-09-19 17:31:29","addtime":"1537349489","fkprice":"14.9","xgyg":"4.47","dlyj":3.93,"orderzt":"订单付款","xgyg2":"0.2","pxgyg":"0.92","axgyg":"0.2","userid1":"142","userid2":"142","puserid":"142","auserid":"142","picture":"jj"}]}
     */

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * current_page : 1
         * data : [{"id":"5274998","orderid":"223485795928109296","numid":"536146728914","title":"保温杯女男学生便携水杯子创意时尚大容量304不锈钢商务茶杯定制<font style='color:#FF0000;font-size:0.5rem''>[订单付款]<\/font>","createtime":"2018-09-19 17:31:29","addtime":"1537349489","fkprice":"14.9","xgyg":"4.47","dlyj":3.93,"orderzt":"订单付款","xgyg2":"0.2","pxgyg":"0.92","axgyg":"0.2","userid1":"142","userid2":"142","puserid":"142","auserid":"142","picture":"jj"}]
         */

        private int current_page;
        private List<DataBean> data;
        private List<TypeBean> types;

        public List<TypeBean> getTypes() {
            return types;
        }

        public void setTypes(List<TypeBean> types) {
            this.types = types;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class TypeBean{
            private String id;
            private String title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class DataBean {
            /**
             * id : 5274998
             * orderid : 223485795928109296
             * numid : 536146728914
             * title : 保温杯女男学生便携水杯子创意时尚大容量304不锈钢商务茶杯定制<font style='color:#FF0000;font-size:0.5rem''>[订单付款]</font>
             * createtime : 2018-09-19 17:31:29
             * addtime : 1537349489
             * fkprice : 14.9
             * xgyg : 4.47
             * dlyj : 3.93
             * orderzt : 订单付款
             * xgyg2 : 0.2
             * pxgyg : 0.92
             * axgyg : 0.2
             * userid1 : 142
             * userid2 : 142
             * puserid : 142
             * auserid : 142
             * picture : jj
             */

            private String id;
            private String orderid;
            private String numid;
            private String title;
            private String createtime;
            private String addtime;
            private String fkprice;
            private String xgyg;
            private double dlyj;
            private String orderzt;
            private String xgyg2;
            private String pxgyg;
            private String axgyg;
            private String userid1;
            private String userid2;
            private String puserid;
            private String auserid;
            private String picture;
            private String shoptype;
            private String jstime;

            public String getJstime() {
                return jstime;
            }

            public void setJstime(String jstime) {
                this.jstime = jstime;
            }

            public String getShoptype() {
                return shoptype;
            }

            public void setShoptype(String shoptype) {
                this.shoptype = shoptype;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public String getNumid() {
                return numid;
            }

            public void setNumid(String numid) {
                this.numid = numid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getFkprice() {
                return fkprice;
            }

            public void setFkprice(String fkprice) {
                this.fkprice = fkprice;
            }

            public String getXgyg() {
                return xgyg;
            }

            public void setXgyg(String xgyg) {
                this.xgyg = xgyg;
            }

            public double getDlyj() {
                return dlyj;
            }

            public void setDlyj(double dlyj) {
                this.dlyj = dlyj;
            }

            public String getOrderzt() {
                return orderzt;
            }

            public void setOrderzt(String orderzt) {
                this.orderzt = orderzt;
            }

            public String getXgyg2() {
                return xgyg2;
            }

            public void setXgyg2(String xgyg2) {
                this.xgyg2 = xgyg2;
            }

            public String getPxgyg() {
                return pxgyg;
            }

            public void setPxgyg(String pxgyg) {
                this.pxgyg = pxgyg;
            }

            public String getAxgyg() {
                return axgyg;
            }

            public void setAxgyg(String axgyg) {
                this.axgyg = axgyg;
            }

            public String getUserid1() {
                return userid1;
            }

            public void setUserid1(String userid1) {
                this.userid1 = userid1;
            }

            public String getUserid2() {
                return userid2;
            }

            public void setUserid2(String userid2) {
                this.userid2 = userid2;
            }

            public String getPuserid() {
                return puserid;
            }

            public void setPuserid(String puserid) {
                this.puserid = puserid;
            }

            public String getAuserid() {
                return auserid;
            }

            public void setAuserid(String auserid) {
                this.auserid = auserid;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }
    }
}
