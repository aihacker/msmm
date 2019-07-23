package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by lq on 2018/3/19.
 */

public class FactoryProListResponse extends BaseResponse {

    /**
     * data : {"shopName":"sn001","shopImg":"Upload/shops/2018-03/5a9f86f2c5569.png","hotgoods":{"root":[{"saleCount":"0","shopId":"34","goodsId":"2","goodsName":"wstmall墨西哥牛油果8个 7天内发货 8个装","goodsImg":"Upload/goods/2015-05/55641875b7501.jpg","shopPrice":"36.00","marketPrice":"40.00","goodsSn":"0001"}]}}
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
         * shopName : sn001
         * shopImg : Upload/shops/2018-03/5a9f86f2c5569.png
         * hotgoods : {"root":[{"saleCount":"0","shopId":"34","goodsId":"2","goodsName":"wstmall墨西哥牛油果8个 7天内发货 8个装","goodsImg":"Upload/goods/2015-05/55641875b7501.jpg","shopPrice":"36.00","marketPrice":"40.00","goodsSn":"0001"}]}
         */

        private String shopName;
        private String shopImg;
        private int followers;

        public int getFollowers() {
            return followers;
        }

        public void setFollowers(int followers) {
            this.followers = followers;
        }

        private HotgoodsBean hotgoods;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopImg() {
            return shopImg;
        }

        public void setShopImg(String shopImg) {
            this.shopImg = shopImg;
        }

        public HotgoodsBean getHotgoods() {
            return hotgoods;
        }

        public void setHotgoods(HotgoodsBean hotgoods) {
            this.hotgoods = hotgoods;
        }

        public static class HotgoodsBean {
            private List<RootBean> root;

            private float start;

            public float getStart() {
                return start;
            }

            public void setStart(float start) {
                this.start = start;
            }

            public List<RootBean> getRoot() {
                return root;
            }

            public void setRoot(List<RootBean> root) {
                this.root = root;
            }

            public static class RootBean {
                /**
                 * saleCount : 0
                 * shopId : 34
                 * goodsId : 2
                 * goodsName : wstmall墨西哥牛油果8个 7天内发货 8个装
                 * goodsImg : Upload/goods/2015-05/55641875b7501.jpg
                 * shopPrice : 36.00
                 * marketPrice : 40.00
                 * goodsSn : 0001
                 */

                private int saleCount;
                private int shopId;
                private int goodsId;
                private String goodsName;
                private String goodsImg;
                private float shopPrice;
                private float marketPrice;
                private String goodsSn;

                public int getSaleCount() {
                    return saleCount;
                }

                public int getShopId() {
                    return shopId;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public String getGoodsName() {
                    return goodsName;
                }

                public String getGoodsImg() {
                    return goodsImg;
                }

                public float getShopPrice() {
                    return shopPrice;
                }

                public float getMarketPrice() {
                    return marketPrice;
                }

                public String getGoodsSn() {
                    return goodsSn;
                }
            }
        }
    }
}
