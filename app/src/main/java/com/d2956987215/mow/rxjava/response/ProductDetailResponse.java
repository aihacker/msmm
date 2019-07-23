package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by lq on 2018/3/15.
 */

public class ProductDetailResponse extends BaseResponse {

    /**
     * data : {"shopId":"34","attrCatId":"1","isSale":"1","goodsName":"上衣","goodsImg":"Upload/goods/2018-03/5aaf783763487.jpg","goodsThums":"Upload/goods/2018-03/5aaf783763487_thumb.jpg","shopPrice":"100.00","marketPrice":"55.00","goodsDesc":"测试商品","goodsStatus":"1","shopName":"sn001","goodsAttrId":"45","attrPrice":"100.00","attrStock":"20","shopAddress":"xxxxx","goodsStock":"20","shopImg":"Upload/shops/2018-03/5a9f86f2c5569.png","goodsSn":"0003","goodsAttrs":{"priceAttrId":"14","priceAttrName":"颜色","priceAttrs":[{"id":"45","attrVal":"红色","attrPrice":"100.00","attrStock":"20","attrId":"14","attrName":"颜色","isPriceAttr":"1"},{"id":"46","attrVal":"黄色","attrPrice":"50.00","attrStock":"100","attrId":"14","attrName":"颜色","isPriceAttr":"1"},{"id":"47","attrVal":"蓝色","attrPrice":"20.00","attrStock":"100","attrId":"14","attrName":"颜色","isPriceAttr":"1"}],"attrs":[{"id":"26","attrVal":"S","attrPrice":"0.00","attrStock":"100","attrId":"15","attrName":"尺寸","isPriceAttr":"0","attrContent":"S"},{"id":"27","attrVal":"L","attrPrice":"0.00","attrStock":"100","attrId":"15","attrName":"尺寸","isPriceAttr":"0","attrContent":"L"},{"id":"28","attrVal":"M","attrPrice":"0.00","attrStock":"100","attrId":"15","attrName":"尺寸","isPriceAttr":"0","attrContent":"M"},{"id":"44","attrVal":"","attrPrice":"0.00","attrStock":"0","attrId":"15","attrName":"尺寸","isPriceAttr":"0","attrContent":""}]},"commentTotal":"2","commentLast":{"LoginName":"ceshi22","userId":"20","userPhoto":null,"content":"xxxxx"}}
     */

    private DataBean retval;

    public DataBean getData() {
        return retval;
    }

    public void setData(DataBean data) {
        this.retval = data;
    }

    public static class DataBean {
        /**
         * shopId : 34
         * attrCatId : 1
         * isSale : 1
         * goodsName : 上衣
         * goodsImg : Upload/goods/2018-03/5aaf783763487.jpg
         * goodsThums : Upload/goods/2018-03/5aaf783763487_thumb.jpg
         * shopPrice : 100.00
         * marketPrice : 55.00
         * goodsDesc : 测试商品
         * goodsStatus : 1
         * shopName : sn001
         * goodsAttrId : 45
         * attrPrice : 100.00
         * attrStock : 20
         * shopAddress : xxxxx
         * goodsStock : 20
         * shopImg : Upload/shops/2018-03/5a9f86f2c5569.png
         * goodsSn : 0003
         * goodsAttrs : {"priceAttrId":"14","priceAttrName":"颜色","priceAttrs":[{"id":"45","attrVal":"红色","attrPrice":"100.00","attrStock":"20","attrId":"14","attrName":"颜色","isPriceAttr":"1"},{"id":"46","attrVal":"黄色","attrPrice":"50.00","attrStock":"100","attrId":"14","attrName":"颜色","isPriceAttr":"1"},{"id":"47","attrVal":"蓝色","attrPrice":"20.00","attrStock":"100","attrId":"14","attrName":"颜色","isPriceAttr":"1"}],"attrs":[{"id":"26","attrVal":"S","attrPrice":"0.00","attrStock":"100","attrId":"15","attrName":"尺寸","isPriceAttr":"0","attrContent":"S"},{"id":"27","attrVal":"L","attrPrice":"0.00","attrStock":"100","attrId":"15","attrName":"尺寸","isPriceAttr":"0","attrContent":"L"},{"id":"28","attrVal":"M","attrPrice":"0.00","attrStock":"100","attrId":"15","attrName":"尺寸","isPriceAttr":"0","attrContent":"M"},{"id":"44","attrVal":"","attrPrice":"0.00","attrStock":"0","attrId":"15","attrName":"尺寸","isPriceAttr":"0","attrContent":""}]}
         * commentTotal : 2
         * commentLast : {"LoginName":"ceshi22","userId":"20","userPhoto":null,"content":"xxxxx"}
         */

        private int shopId;
        private int attrCatId;
        private int isSale;
        private String goodsName;
        private String goodsImg;
        private String goodsThums;
        private float shopPrice;
        private float marketPrice;
        private String goodsDesc;
        private int goodsStatus;
        private String shopName;
        private int goodsAttrId;
        private float attrPrice;
        private int attrStock;
        private String shopAddress;
        private String goodsStock;
        private String shopImg;
        private String goodsSn;
        private GoodsAttrsBean goodsAttrs;
        private int commentTotal;
        private CommentLastBean commentLast;

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getAttrCatId() {
            return attrCatId;
        }

        public void setAttrCatId(int attrCatId) {
            this.attrCatId = attrCatId;
        }

        public int getIsSale() {
            return isSale;
        }

        public void setIsSale(int isSale) {
            this.isSale = isSale;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsImg() {
            return goodsImg;
        }

        public void setGoodsImg(String goodsImg) {
            this.goodsImg = goodsImg;
        }

        public String getGoodsThums() {
            return goodsThums;
        }

        public void setGoodsThums(String goodsThums) {
            this.goodsThums = goodsThums;
        }

        public float getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(float shopPrice) {
            this.shopPrice = shopPrice;
        }

        public float getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(float marketPrice) {
            this.marketPrice = marketPrice;
        }

        public String getGoodsDesc() {
            return goodsDesc;
        }

        public void setGoodsDesc(String goodsDesc) {
            this.goodsDesc = goodsDesc;
        }

        public int getGoodsStatus() {
            return goodsStatus;
        }

        public void setGoodsStatus(int goodsStatus) {
            this.goodsStatus = goodsStatus;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getGoodsAttrId() {
            return goodsAttrId;
        }

        public void setGoodsAttrId(int goodsAttrId) {
            this.goodsAttrId = goodsAttrId;
        }

        public float getAttrPrice() {
            return attrPrice;
        }

        public void setAttrPrice(float attrPrice) {
            this.attrPrice = attrPrice;
        }

        public int getAttrStock() {
            return attrStock;
        }

        public void setAttrStock(int attrStock) {
            this.attrStock = attrStock;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public String getGoodsStock() {
            return goodsStock;
        }

        public void setGoodsStock(String goodsStock) {
            this.goodsStock = goodsStock;
        }

        public String getShopImg() {
            return shopImg;
        }

        public void setShopImg(String shopImg) {
            this.shopImg = shopImg;
        }

        public String getGoodsSn() {
            return goodsSn;
        }

        public void setGoodsSn(String goodsSn) {
            this.goodsSn = goodsSn;
        }

        public GoodsAttrsBean getGoodsAttrs() {
            return goodsAttrs;
        }

        public void setGoodsAttrs(GoodsAttrsBean goodsAttrs) {
            this.goodsAttrs = goodsAttrs;
        }

        public int getCommentTotal() {
            return commentTotal;
        }

        public void setCommentTotal(int commentTotal) {
            this.commentTotal = commentTotal;
        }

        public CommentLastBean getCommentLast() {
            return commentLast;
        }

        public void setCommentLast(CommentLastBean commentLast) {
            this.commentLast = commentLast;
        }

        public static class GoodsAttrsBean {
            /**
             * priceAttrId : 14
             * priceAttrName : 颜色
             * priceAttrs : [{"id":"45","attrVal":"红色","attrPrice":"100.00","attrStock":"20","attrId":"14","attrName":"颜色","isPriceAttr":"1"},{"id":"46","attrVal":"黄色","attrPrice":"50.00","attrStock":"100","attrId":"14","attrName":"颜色","isPriceAttr":"1"},{"id":"47","attrVal":"蓝色","attrPrice":"20.00","attrStock":"100","attrId":"14","attrName":"颜色","isPriceAttr":"1"}]
             * attrs : [{"id":"26","attrVal":"S","attrPrice":"0.00","attrStock":"100","attrId":"15","attrName":"尺寸","isPriceAttr":"0","attrContent":"S"},{"id":"27","attrVal":"L","attrPrice":"0.00","attrStock":"100","attrId":"15","attrName":"尺寸","isPriceAttr":"0","attrContent":"L"},{"id":"28","attrVal":"M","attrPrice":"0.00","attrStock":"100","attrId":"15","attrName":"尺寸","isPriceAttr":"0","attrContent":"M"},{"id":"44","attrVal":"","attrPrice":"0.00","attrStock":"0","attrId":"15","attrName":"尺寸","isPriceAttr":"0","attrContent":""}]
             */

            private int priceAttrId;
            private String priceAttrName;
            private List<PriceAttrsBean> priceAttrs;
            private List<AttrsBean> attrs;

            public int getPriceAttrId() {
                return priceAttrId;
            }

            public void setPriceAttrId(int priceAttrId) {
                this.priceAttrId = priceAttrId;
            }

            public String getPriceAttrName() {
                return priceAttrName;
            }

            public void setPriceAttrName(String priceAttrName) {
                this.priceAttrName = priceAttrName;
            }

            public List<PriceAttrsBean> getPriceAttrs() {
                return priceAttrs;
            }

            public void setPriceAttrs(List<PriceAttrsBean> priceAttrs) {
                this.priceAttrs = priceAttrs;
            }

            public List<AttrsBean> getAttrs() {
                return attrs;
            }

            public void setAttrs(List<AttrsBean> attrs) {
                this.attrs = attrs;
            }

            public static class PriceAttrsBean {
                /**
                 * id : 45
                 * attrVal : 红色
                 * attrPrice : 100.00
                 * attrStock : 20
                 * attrId : 14
                 * attrName : 颜色
                 * isPriceAttr : 1
                 */

                private int id;
                private String attrVal;
                private float attrPrice;
                private String attrStock;
                private String attrId;
                private String attrName;
                private String isPriceAttr;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getAttrVal() {
                    return attrVal;
                }

                public void setAttrVal(String attrVal) {
                    this.attrVal = attrVal;
                }

                public float getAttrPrice() {
                    return attrPrice;
                }

                public void setAttrPrice(float attrPrice) {
                    this.attrPrice = attrPrice;
                }

                public String getAttrStock() {
                    return attrStock;
                }

                public void setAttrStock(String attrStock) {
                    this.attrStock = attrStock;
                }

                public String getAttrId() {
                    return attrId;
                }

                public void setAttrId(String attrId) {
                    this.attrId = attrId;
                }

                public String getAttrName() {
                    return attrName;
                }

                public void setAttrName(String attrName) {
                    this.attrName = attrName;
                }

                public String getIsPriceAttr() {
                    return isPriceAttr;
                }

                public void setIsPriceAttr(String isPriceAttr) {
                    this.isPriceAttr = isPriceAttr;
                }
            }

            public static class AttrsBean {
                /**
                 * id : 26
                 * attrVal : S
                 * attrPrice : 0.00
                 * attrStock : 100
                 * attrId : 15
                 * attrName : 尺寸
                 * isPriceAttr : 0
                 * attrContent : S
                 */

                private int id;
                private String attrVal;
                private float attrPrice;
                private String attrStock;
                private int attrId;
                private String attrName;
                private String isPriceAttr;
                private String attrContent;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getAttrVal() {
                    return attrVal;
                }

                public void setAttrVal(String attrVal) {
                    this.attrVal = attrVal;
                }

                public float getAttrPrice() {
                    return attrPrice;
                }

                public void setAttrPrice(float attrPrice) {
                    this.attrPrice = attrPrice;
                }

                public String getAttrStock() {
                    return attrStock;
                }

                public void setAttrStock(String attrStock) {
                    this.attrStock = attrStock;
                }

                public int getAttrId() {
                    return attrId;
                }

                public void setAttrId(int attrId) {
                    this.attrId = attrId;
                }

                public String getAttrName() {
                    return attrName;
                }

                public void setAttrName(String attrName) {
                    this.attrName = attrName;
                }

                public String getIsPriceAttr() {
                    return isPriceAttr;
                }

                public void setIsPriceAttr(String isPriceAttr) {
                    this.isPriceAttr = isPriceAttr;
                }

                public String getAttrContent() {
                    return attrContent;
                }

                public void setAttrContent(String attrContent) {
                    this.attrContent = attrContent;
                }
            }
        }

        public static class CommentLastBean {
            /**
             * LoginName : ceshi22
             * userId : 20
             * userPhoto : null
             * content : xxxxx
             */

            private String LoginName;
            private int userId;
            private String userPhoto;
            private String content;

            public String getLoginName() {
                return LoginName;
            }

            public void setLoginName(String LoginName) {
                this.LoginName = LoginName;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserPhoto() {
                return userPhoto;
            }

            public void setUserPhoto(String userPhoto) {
                this.userPhoto = userPhoto;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
