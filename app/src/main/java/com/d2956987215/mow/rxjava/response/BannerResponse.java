package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by lq on 2018/3/15.
 */

public class BannerResponse extends BaseResponse {

    private DataBean retval;

    public DataBean getRetval() {
        return retval;
    }

    public void setRetval(DataBean retval) {
        this.retval = retval;
    }

    public static class DataBean {
        /**
         * adName : 1
         * adFile : Upload/adspic/2015-05/554c173b5c57e.jpg
         */
        private List<Ad> ad;
        private List<Goods> goods;
        private String adFile;

        public List<Ad> getAd() {
            return ad;
        }

        public void setAd(List<Ad> ad) {
            this.ad = ad;
        }

        public List<Goods> getGoods() {
            return goods;
        }

        public void setGoods(List<Goods> goods) {
            this.goods = goods;
        }

        public String getAdFile() {
            return adFile;
        }

        public void setAdFile(String adFile) {
            this.adFile = adFile;
        }

        public static class Goods {
            private String  goods_id;
            private String  goods_name;
            private String price;
            private String sales;
            private String picture;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }

        public static class Ad {
            private String adv_image;
            private String adv_url;

            public String getAdv_image() {
                return adv_image;
            }

            public void setAdv_image(String adv_image) {
                this.adv_image = adv_image;
            }

            public String getAdv_url() {
                return adv_url;
            }

            public void setAdv_url(String adv_url) {
                this.adv_url = adv_url;
            }
        }
    }
}
