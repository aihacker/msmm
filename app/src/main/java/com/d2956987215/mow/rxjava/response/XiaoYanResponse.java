package com.d2956987215.mow.rxjava.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class XiaoYanResponse extends BaseResponse implements Serializable {


    private DataBean retval;

    public DataBean getRetval() {
        return retval;
    }

    public void setRetval(DataBean retval) {
        this.retval = retval;
    }

    public static class DataBean implements Serializable {


        private Address address;
        private List<Goods> goods;
        private Statistics statistics;

        public Statistics getStatistics() {
            return statistics;
        }

        public void setStatistics(Statistics statistics) {
            this.statistics = statistics;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public List<Goods> getGoods() {
            return goods;
        }

        public void setGoods(List<Goods> goods) {
            this.goods = goods;
        }


    public static class Address implements Serializable{
        private int addr_id;
        private String mobile;
        private String province_name;
        private String city_name;
        private String district_name;
        private String consigner;
        private String address;

        public String getConsigner() {
            return consigner;
        }

        public void setConsigner(String consigner) {
            this.consigner = consigner;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getAddr_id() {
            return addr_id;
        }

        public void setAddr_id(int addr_id) {
            this.addr_id = addr_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }
    }

    public static class Goods implements  Serializable{
        private int number;
        private int stock;
        private int sku_id;
        private String sku_name;
        private String price;
        private String goods_name;
        private int max_buy;
        private String picture;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getSku_id() {
            return sku_id;
        }

        public void setSku_id(int sku_id) {
            this.sku_id = sku_id;
        }

        public String getSku_name() {
            return sku_name;
        }

        public void setSku_name(String sku_name) {
            this.sku_name = sku_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getMax_buy() {
            return max_buy;
        }

        public void setMax_buy(int max_buy) {
            this.max_buy = max_buy;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }

    public static class Statistics implements  Serializable{
        private int amount;
        private int express;
        private int   goods;
        private int number;


        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getGoods() {
            return goods;
        }

        public void setGoods(int goods) {
            this.goods = goods;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getExpress() {
            return express;
        }

        public void setExpress(int express) {
            this.express = express;
        }
    }
    }
}
