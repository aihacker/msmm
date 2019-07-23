package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by 闫亚锋 on 2018/10/11
 * desc：
 */
public class MyFavariteResponse extends BaseResponse{
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean{
        private String id;
        private String user_id;
        private String quan_id;
        private String goods_id;
        private String type;
        private String created_at;
        private String goods_title;
        private String goods_pic;
        private String goods_quan;
        private String goods_sale;
        private String goods_endprice;
        private String goods_price;
        private String shop_name;
        private String shoptype;
        private String share_money;
        private boolean choose;
        private String eurl;


        public String getQuan_id() {
            return quan_id;
        }

        public void setQuan_id(String quan_id) {
            this.quan_id = quan_id;
        }

        public String getShare_money() {
            return share_money;
        }

        public void setShare_money(String share_money) {
            this.share_money = share_money;
        }

        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }

        public String getEurl() {
            return eurl;
        }

        public void setEurl(String eurl) {
            this.eurl = eurl;
        }

        public boolean isChoose() {
            return choose;
        }

        public void setChoose(boolean choose) {
            this.choose = choose;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getGoods_title() {
            return goods_title;
        }

        public void setGoods_title(String goods_title) {
            this.goods_title = goods_title;
        }

        public String getGoods_pic() {
            return goods_pic;
        }

        public void setGoods_pic(String goods_pic) {
            this.goods_pic = goods_pic;
        }

        public String getGoods_quan() {
            return goods_quan;
        }

        public void setGoods_quan(String goods_quan) {
            this.goods_quan = goods_quan;
        }

        public String getGoods_sale() {
            return goods_sale;
        }

        public void setGoods_sale(String goods_sale) {
            this.goods_sale = goods_sale;
        }

        public String getGoods_endprice() {
            return goods_endprice;
        }

        public void setGoods_endprice(String goods_endprice) {
            this.goods_endprice = goods_endprice;
        }

        public String   getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }
    }
}
