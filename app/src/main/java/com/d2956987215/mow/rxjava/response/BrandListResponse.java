package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class BrandListResponse extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 31
         * fq_brand_name : 雀氏
         * brand_logo : http://img02.taobaocdn.com:80/tfscom/TB1FTjZDpzqK1RjSZSgXXcpAVXa
         * item : [{"itemid":"569301642889","itemtitle":"雀氏宝宝短袖T恤夏装新款男童女童童装儿童卡通圆领上衣","itempic":"https://img.alicdn.com/imgextra/i4/3247338739/TB2oQ4wGQ9WBuNjSspeXXaz5VXa_!!3247338739-0-item_pic.jpg","itemprice":"19.90","couponmoney":"5","couponurl":"https://uland.taobao.com/quan/detail?sellerId=3247338739&activityId=3e8ae14740a14bd8b183ca0836184d2f","pid":"mm_32490747_43626016_341908488","give_money":3.73}]
         */

        private String id;
        private String fq_brand_name;
        private String brand_logo;
        private String brandCommission;
        private String shopid;
        private List<ItemBean> item;

        public String getBrandCommission() {
            return brandCommission;
        }

        public void setBrandCommission(String brandCommission) {
            this.brandCommission = brandCommission;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFq_brand_name() {
            return fq_brand_name;
        }

        public void setFq_brand_name(String fq_brand_name) {
            this.fq_brand_name = fq_brand_name;
        }

        public String getBrand_logo() {
            return brand_logo;
        }

        public void setBrand_logo(String brand_logo) {
            this.brand_logo = brand_logo;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * itemid : 569301642889
             * itemtitle : 雀氏宝宝短袖T恤夏装新款男童女童童装儿童卡通圆领上衣
             * itempic : https://img.alicdn.com/imgextra/i4/3247338739/TB2oQ4wGQ9WBuNjSspeXXaz5VXa_!!3247338739-0-item_pic.jpg
             * itemprice : 19.90
             * couponmoney : 5
             * couponurl : https://uland.taobao.com/quan/detail?sellerId=3247338739&activityId=3e8ae14740a14bd8b183ca0836184d2f
             * pid : mm_32490747_43626016_341908488
             * give_money : 3.73
             */

            private String itemid;
            private String itemtitle;
            private String itempic;
            private String itemprice;
            private String couponmoney;
            private String couponurl;
            private String pid;
            private String give_money;
            private String quan_id;

            public String getQuan_id() {
                return quan_id;
            }

            public void setQuan_id(String quan_id) {
                this.quan_id = quan_id;
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

            public String getItempic() {
                return itempic;
            }

            public void setItempic(String itempic) {
                this.itempic = itempic;
            }

            public String getItemprice() {
                return itemprice;
            }

            public void setItemprice(String itemprice) {
                this.itemprice = itemprice;
            }

            public String getCouponmoney() {
                return couponmoney;
            }

            public void setCouponmoney(String couponmoney) {
                this.couponmoney = couponmoney;
            }

            public String getCouponurl() {
                return couponurl;
            }

            public void setCouponurl(String couponurl) {
                this.couponurl = couponurl;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getGive_money() {
                return give_money;
            }

            public void setGive_money(String give_money) {
                this.give_money = give_money;
            }
        }
    }
}
