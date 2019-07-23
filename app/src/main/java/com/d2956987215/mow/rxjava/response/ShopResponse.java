package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class ShopResponse extends BaseResponse{

    /**
     * data : {"id":"1","fq_brand_name":"三只松鼠","brand_logo":"http://img02.taobaocdn.com:80/tfscom/TB1jsD.DxjaK1RjSZFAXXbdLFXa","introduce":"三只松鼠，互联网坚果销量冠军品牌，致力于生产天然新鲜、非过度加工的健康食品，誓将卖萌服务与极致用户体验进行到底，数百万消费者的不二选择，值得信赖！","shopid":"67597230","item":[{"itemid":"576881595560","itemtitle":"三只松鼠 巨型零食大礼包礼盒一整箱零食1049g抖音同款送女友","itempic":"http://img.alicdn.com/imgextra/i4/725677994/O1CN01q7H2q928vIcrhbY5U_!!725677994.jpg","itemprice":"69.90","itemendprice":"59.90","itemsale":"8120","couponmoney":"10","couponurl":"https://uland.taobao.com/quan/detail?sellerId=725677994&activityId=d9158f0c963541eebaf587a99f879e45","quan_id":"","pid":"mm_32490747_43626016_341908488","give_money":7.5,"share_money":0},{"itemid":"571157008820","itemtitle":"三只松鼠 氧气吐司面包800g/整箱夹心吐司口袋面包早餐礼盒","itempic":"http://img.alicdn.com/imgextra/i3/725677994/O1CN0128vIcnoVi4xJgHt_!!725677994.jpg","itemprice":"36.90","itemendprice":"33.90","itemsale":"61825","couponmoney":"3","couponurl":"https://uland.taobao.com/quan/detail?sellerId=725677994&activityId=0cc7029b1cb045dcbed7aa1f82be1aa2","quan_id":"","pid":"mm_32490747_43626016_341908488","give_money":4.24,"share_money":0},{"itemid":"579833295941","itemtitle":"【三只松鼠_小确幸100g_牛奶/黑巧克力】网红零食送女友小礼盒","itempic":"http://img.alicdn.com/imgextra/i3/880734502/O1CN011j7xYRNCZtsWKs8_!!880734502.jpg","itemprice":"28.90","itemendprice":"23.90","itemsale":"18004","couponmoney":"5","couponurl":"https://uland.taobao.com/quan/detail?sellerId=880734502&activityId=631b19b5a31e4f2f85a16db5833acb5f","quan_id":"","pid":"mm_32490747_43626016_341908488","give_money":2.99,"share_money":0},{"itemid":"570982149821","itemtitle":"【三只松鼠_乳酸菌小伴侣800g/箱】营养口袋小面包早餐整箱蛋糕","itempic":"http://img.alicdn.com/imgextra/i4/880734502/TB2aq1UGf9TBuNjy1zbXXXpepXa_!!880734502.jpg","itemprice":"34.90","itemendprice":"29.90","itemsale":"118976","couponmoney":"5","couponurl":"https://uland.taobao.com/quan/detail?sellerId=880734502&activityId=710146adbae644c6861b79eed4e9a903","quan_id":"","pid":"mm_32490747_43626016_341908488","give_money":3.73,"share_money":0},{"itemid":"567448023753","itemtitle":"【三只松鼠_雪麸蛋糕1000g/整箱】雪芙营养早餐网红口袋夹心面包","itempic":"http://img.alicdn.com/imgextra/i3/880734502/O1CN01z9WhH01j7xahXKsvg_!!880734502.jpg","itemprice":"26.90","itemendprice":"21.90","itemsale":"13539","couponmoney":"5","couponurl":"https://uland.taobao.com/quan/detail?sellerId=880734502&activityId=3f1de4d0a2e74ad0a29bd094d412fd9b","quan_id":"","pid":"mm_32490747_43626016_341908488","give_money":2.72,"share_money":0}]}
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
         * id : 1
         * fq_brand_name : 三只松鼠
         * brand_logo : http://img02.taobaocdn.com:80/tfscom/TB1jsD.DxjaK1RjSZFAXXbdLFXa
         * introduce : 三只松鼠，互联网坚果销量冠军品牌，致力于生产天然新鲜、非过度加工的健康食品，誓将卖萌服务与极致用户体验进行到底，数百万消费者的不二选择，值得信赖！
         * shopid : 67597230
         * item : [{"itemid":"576881595560","itemtitle":"三只松鼠 巨型零食大礼包礼盒一整箱零食1049g抖音同款送女友","itempic":"http://img.alicdn.com/imgextra/i4/725677994/O1CN01q7H2q928vIcrhbY5U_!!725677994.jpg","itemprice":"69.90","itemendprice":"59.90","itemsale":"8120","couponmoney":"10","couponurl":"https://uland.taobao.com/quan/detail?sellerId=725677994&activityId=d9158f0c963541eebaf587a99f879e45","quan_id":"","pid":"mm_32490747_43626016_341908488","give_money":7.5,"share_money":0},{"itemid":"571157008820","itemtitle":"三只松鼠 氧气吐司面包800g/整箱夹心吐司口袋面包早餐礼盒","itempic":"http://img.alicdn.com/imgextra/i3/725677994/O1CN0128vIcnoVi4xJgHt_!!725677994.jpg","itemprice":"36.90","itemendprice":"33.90","itemsale":"61825","couponmoney":"3","couponurl":"https://uland.taobao.com/quan/detail?sellerId=725677994&activityId=0cc7029b1cb045dcbed7aa1f82be1aa2","quan_id":"","pid":"mm_32490747_43626016_341908488","give_money":4.24,"share_money":0},{"itemid":"579833295941","itemtitle":"【三只松鼠_小确幸100g_牛奶/黑巧克力】网红零食送女友小礼盒","itempic":"http://img.alicdn.com/imgextra/i3/880734502/O1CN011j7xYRNCZtsWKs8_!!880734502.jpg","itemprice":"28.90","itemendprice":"23.90","itemsale":"18004","couponmoney":"5","couponurl":"https://uland.taobao.com/quan/detail?sellerId=880734502&activityId=631b19b5a31e4f2f85a16db5833acb5f","quan_id":"","pid":"mm_32490747_43626016_341908488","give_money":2.99,"share_money":0},{"itemid":"570982149821","itemtitle":"【三只松鼠_乳酸菌小伴侣800g/箱】营养口袋小面包早餐整箱蛋糕","itempic":"http://img.alicdn.com/imgextra/i4/880734502/TB2aq1UGf9TBuNjy1zbXXXpepXa_!!880734502.jpg","itemprice":"34.90","itemendprice":"29.90","itemsale":"118976","couponmoney":"5","couponurl":"https://uland.taobao.com/quan/detail?sellerId=880734502&activityId=710146adbae644c6861b79eed4e9a903","quan_id":"","pid":"mm_32490747_43626016_341908488","give_money":3.73,"share_money":0},{"itemid":"567448023753","itemtitle":"【三只松鼠_雪麸蛋糕1000g/整箱】雪芙营养早餐网红口袋夹心面包","itempic":"http://img.alicdn.com/imgextra/i3/880734502/O1CN01z9WhH01j7xahXKsvg_!!880734502.jpg","itemprice":"26.90","itemendprice":"21.90","itemsale":"13539","couponmoney":"5","couponurl":"https://uland.taobao.com/quan/detail?sellerId=880734502&activityId=3f1de4d0a2e74ad0a29bd094d412fd9b","quan_id":"","pid":"mm_32490747_43626016_341908488","give_money":2.72,"share_money":0}]
         */

        private String id;
        private String fq_brand_name;
        private String brand_logo;
        private String introduce;
        private String shopid;
        private String shop_url;
        private List<ItemBean> item;

        public String getShop_url() {
            return shop_url;
        }

        public void setShop_url(String shop_url) {
            this.shop_url = shop_url;
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

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * itemid : 576881595560
             * itemtitle : 三只松鼠 巨型零食大礼包礼盒一整箱零食1049g抖音同款送女友
             * itempic : http://img.alicdn.com/imgextra/i4/725677994/O1CN01q7H2q928vIcrhbY5U_!!725677994.jpg
             * itemprice : 69.90
             * itemendprice : 59.90
             * itemsale : 8120
             * couponmoney : 10
             * couponurl : https://uland.taobao.com/quan/detail?sellerId=725677994&activityId=d9158f0c963541eebaf587a99f879e45
             * quan_id :
             * pid : mm_32490747_43626016_341908488
             * give_money : 7.5
             * share_money : 0
             */

            private String itemid;
            private String itemtitle;
            private String itempic;
            private String itemprice;
            private String itemendprice;
            private String itemsale;
            private String couponmoney;
            private String couponurl;
            private String quan_id;
            private String pid;
            private String give_money;
            private String share_money;
            private String uproletype;
            private String shoptype;
            private String sellernick;

            public String getShoptype() {
                return shoptype;
            }

            public void setShoptype(String shoptype) {
                this.shoptype = shoptype;
            }

            public String getSellernick() {
                return sellernick;
            }

            public void setSellernick(String sellernick) {
                this.sellernick = sellernick;
            }

            public String getUproletype() {
                return uproletype;
            }

            public void setUproletype(String uproletype) {
                this.uproletype = uproletype;
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

            public String getItemendprice() {
                return itemendprice;
            }

            public void setItemendprice(String itemendprice) {
                this.itemendprice = itemendprice;
            }

            public String getItemsale() {
                return itemsale;
            }

            public void setItemsale(String itemsale) {
                this.itemsale = itemsale;
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

            public String getQuan_id() {
                return quan_id;
            }

            public void setQuan_id(String quan_id) {
                this.quan_id = quan_id;
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

            public String getShare_money() {
                return share_money;
            }

            public void setShare_money(String share_money) {
                this.share_money = share_money;
            }
        }
    }
}
