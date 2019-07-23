package com.d2956987215.mow.rxjava.response;


import java.util.List;

public class ShopAllCouponResponse extends BaseResponse{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 7817630
         * itemid : 542957446424
         * itemtitle : 智利原瓶进口红酒美洲狮三支装
         * itemprice : 889
         * itemendprice : 689
         * sellernick : 天猫超市
         * itemshorttitle : 智利原瓶进口红酒美洲狮三支装
         * itemsale : 69
         * itempic : http://img.alicdn.com/imgextra/i4/725677994/TB22aTQzntYBeNjy1XdXXXXyVXa_!!725677994.jpg
         * shoptype : B
         * quan_id : 31c87b2db8c44f19a7581eb3ea807e4a
         * couponmoney : 200
         * couponurl : https://uland.taobao.com/quan/detail?sellerId=725677994&activityId=31c87b2db8c44f19a7581eb3ea807e4a
         * fqcat : 9
         * tkrates : 20.00
         * videoid : 0
         * share_money : 0
         * give_money : 80.61
         * coupon_id :
         * lm : 0
         * qwf : 0
         */

        private int id;
        private String itemid;
        private String itemtitle;
        private double itemprice;
        private double itemendprice;
        private String sellernick;
        private String itemshorttitle;
        private String itemsale;
        private String itempic;
        private String shoptype;
        private String quan_id;
        private double couponmoney;
        private String couponurl;
        private String fqcat;
        private String tkrates;
        private String videoid;
        private double share_money;
        private double give_money;
        private String coupon_id;
        private double lm;
        private double qwf;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getItemid() {
            return itemid;
        }

        public void setItemid(String itemid) {
            this.itemid = itemid;
        }

        public String getItemtitle() {
            return itemtitle;
        }

        public void setItemtitle(String itemtitle) {
            this.itemtitle = itemtitle;
        }

        public double getItemprice() {
            return itemprice;
        }

        public void setItemprice(double itemprice) {
            this.itemprice = itemprice;
        }

        public double getItemendprice() {
            return itemendprice;
        }

        public void setItemendprice(int itemendprice) {
            this.itemendprice = itemendprice;
        }

        public String getSellernick() {
            return sellernick;
        }

        public void setSellernick(String sellernick) {
            this.sellernick = sellernick;
        }

        public String getItemshorttitle() {
            return itemshorttitle;
        }

        public void setItemshorttitle(String itemshorttitle) {
            this.itemshorttitle = itemshorttitle;
        }

        public String getItemsale() {
            return itemsale;
        }

        public void setItemsale(String itemsale) {
            this.itemsale = itemsale;
        }

        public String getItempic() {
            return itempic;
        }

        public void setItempic(String itempic) {
            this.itempic = itempic;
        }

        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }

        public String getQuan_id() {
            return quan_id;
        }

        public void setQuan_id(String quan_id) {
            this.quan_id = quan_id;
        }

        public double getCouponmoney() {
            return couponmoney;
        }

        public void setCouponmoney(double couponmoney) {
            this.couponmoney = couponmoney;
        }

        public String getCouponurl() {
            return couponurl;
        }

        public void setCouponurl(String couponurl) {
            this.couponurl = couponurl;
        }

        public String getFqcat() {
            return fqcat;
        }

        public void setFqcat(String fqcat) {
            this.fqcat = fqcat;
        }

        public String getTkrates() {
            return tkrates;
        }

        public void setTkrates(String tkrates) {
            this.tkrates = tkrates;
        }

        public String getVideoid() {
            return videoid;
        }

        public void setVideoid(String videoid) {
            this.videoid = videoid;
        }

        public double getShare_money() {
            return share_money;
        }

        public void setShare_money(double share_money) {
            this.share_money = share_money;
        }

        public double getGive_money() {
            return give_money;
        }

        public void setGive_money(double give_money) {
            this.give_money = give_money;
        }

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        public double getLm() {
            return lm;
        }

        public void setLm(double lm) {
            this.lm = lm;
        }

        public double getQwf() {
            return qwf;
        }

        public void setQwf(double qwf) {
            this.qwf = qwf;
        }
    }
}
