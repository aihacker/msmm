package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class HomeHotResponse extends BaseResponse{

    /**
     * status : 1
     * msg : 推荐商品
     * data : [{"goodsId":"255","goodsSn":"sn0012","goodsName":"xec","goodsThums":"Upload/goods/2018-03/5a9f9d2cbb8dd_thumb.png","goodsStock":"100","saleCount":"0","marketPrice":"100.00","shopPrice":"20.00"},{"goodsId":"254","goodsSn":"23523","goodsName":"可口 /KOKA 新加坡进口方便面 多口味12袋装","goodsThums":"Upload/goods/2015-06/556c75d780e62.jpg","goodsStock":"5523423","saleCount":"0","marketPrice":"45.00","shopPrice":"41.90"},{"goodsId":"253","goodsSn":"3453636","goodsName":"可口 KOKA 星洲叻沙 快熟面 油炸辣味 90g","goodsThums":"Upload/goods/2015-06/556c758961ee6.jpg","goodsStock":"341241","saleCount":"0","marketPrice":"34.00","shopPrice":"23.00"},{"goodsId":"252","goodsSn":"234928342","goodsName":"wstmall测试商品 东远 金枪鱼罐头 蔬菜味","goodsThums":"Upload/goods/2015-06/556c74f22044b.jpg","goodsStock":"2342342","saleCount":"0","marketPrice":"45.00","shopPrice":"40.00"}]
     */

    private List<DataBean> retval;




    public List<DataBean> getRetval() {
        return retval;
    }

    public void setRetval(List<DataBean> retval) {
        this.retval = retval;
    }

    public static class DataBean {
        /**
         * goodsId : 255
         * goodsSn : sn0012
         * goodsName : xec
         * goodsThums : Upload/goods/2018-03/5a9f9d2cbb8dd_thumb.png
         * goodsStock : 100
         * saleCount : 0
         * marketPrice : 100.00
         * shopPrice : 20.00
         */
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
}
