package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class QiagGouListResponse extends BaseResponse {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ID : 16628146
         * GoodsID : 574014813046
         * Title : 【小红书力荐】澳洲山羊奶身体乳250ml
         * D_title : 【小红书力荐】澳洲山羊奶身体乳250ml
         * Pic : https://img.alicdn.com/imgextra/i4/858335983/TB2jdG7IQSWBuNjSszdXXbeSpXa_!!858335983.jpg
         * Org_Price : 20.1
         * Price : 5.1
         * IsTmall : 1
         * Sales_num : 234697
         * Quan_id : 3d4e3a3784434bcb8eb4e7cd35aa7e12
         * Quan_price : 15
         * Quan_surplus : 44000
         * Quan_receive : 56000
         * Quan_link : https://uland.taobao.com/quan/detail?sellerId=2457133736&activityId=3d4e3a3784434bcb8eb4e7cd35aa7e12
         * give_money : 1.49
         * shop_name :
         */

        private String ID;
        private String GoodsID;
        private String Title;
        private String D_title;
        private String Pic;
        private String Org_Price;
        private String Price;
        private String IsTmall;
        private String Sales_num;
        private String Quan_id;
        private int Quan_price;
        private int Quan_surplus;
        private int Quan_receive;
        private String Quan_link;
        private String give_money;
        private String shop_name;
        private String IsTmal;
        private String share_money;


        public String getShare_money() {
            return share_money;
        }

        public void setShare_money(String share_money) {
            this.share_money = share_money;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getGoodsID() {
            return GoodsID;
        }

        public void setGoodsID(String goodsID) {
            GoodsID = goodsID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getD_title() {
            return D_title;
        }

        public void setD_title(String d_title) {
            D_title = d_title;
        }

        public String getPic() {
            return Pic;
        }

        public void setPic(String pic) {
            Pic = pic;
        }

        public String getOrg_Price() {
            return Org_Price;
        }

        public void setOrg_Price(String org_Price) {
            Org_Price = org_Price;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getIsTmall() {
            return IsTmall;
        }

        public void setIsTmall(String isTmall) {
            IsTmall = isTmall;
        }

        public String getSales_num() {
            return Sales_num;
        }

        public void setSales_num(String sales_num) {
            Sales_num = sales_num;
        }

        public String getQuan_id() {
            return Quan_id;
        }

        public void setQuan_id(String quan_id) {
            Quan_id = quan_id;
        }

        public int getQuan_price() {
            return Quan_price;
        }

        public void setQuan_price(int quan_price) {
            Quan_price = quan_price;
        }

        public int getQuan_surplus() {
            return Quan_surplus;
        }

        public void setQuan_surplus(int quan_surplus) {
            Quan_surplus = quan_surplus;
        }

        public int getQuan_receive() {
            return Quan_receive;
        }

        public void setQuan_receive(int quan_receive) {
            Quan_receive = quan_receive;
        }

        public String getQuan_link() {
            return Quan_link;
        }

        public void setQuan_link(String quan_link) {
            Quan_link = quan_link;
        }

        public String getGive_money() {
            return give_money;
        }

        public void setGive_money(String give_money) {
            this.give_money = give_money;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getIsTmal() {
            return IsTmal;
        }

        public void setIsTmal(String isTmal) {
            IsTmal = isTmal;
        }
    }
}
