package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by Administrator on 2018/3/10.
 */

public class ProductListResponse extends BaseResponse {

    /**
     * data : {"maxPrice":"150.00","brands":[],"pages":{"total":"7","pageSize":30,"start":0,"root":[{"goodsId":"2","goodsSn":"0001","goodsName":"wstmall墨西哥牛油果8个 7天内发货 8个装","goodsThums":"Upload/goods/2015-05/55641875b7501.jpg","goodsStock":"496","saleCount":"0","shopId":"34","marketPrice":"40.00","shopPrice":"36.00","goodsAttrId":null,"saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"3","goodsSn":"223655","goodsName":"进口 美国新奇士Sunkist 脐橙 6粒","goodsThums":"Upload/goods/2015-05/554c2c1a6a4b9.jpg","goodsStock":"299","saleCount":"0","shopId":"34","marketPrice":"40.00","shopPrice":"40.00","goodsAttrId":"10","saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"4","goodsSn":"00001","goodsName":"特色菜 半成品菜肴BCP-22 糖醋排条225g","goodsThums":"Upload/goods/2015-05/556b10094ea52.jpg","goodsStock":"133","saleCount":"0","shopId":"4","marketPrice":"20.00","shopPrice":"13.00","goodsAttrId":null,"saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"5","goodsSn":"0003","goodsName":"wstmal鲜果蔬 越南新鲜青芒果 5斤装 好吃的","goodsThums":"Upload/goods/2015-05/5563ef4681396.jpg","goodsStock":"300","saleCount":"0","shopId":"34","marketPrice":"55.00","shopPrice":"50.00","goodsAttrId":"15","saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"6","goodsSn":"0003","goodsName":"台湾金钻凤梨2粒/组（约2.5kg-3.2kg）","goodsThums":"Upload/goods/2015-05/5563f3941bd95.jpg","goodsStock":"5214","saleCount":"0","shopId":"4","marketPrice":"95.00","shopPrice":"56.00","goodsAttrId":null,"saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"7","goodsSn":"543512","goodsName":"wstmall汇绿鲜果蔬 限量发售泰国金枕榴莲带壳","goodsThums":"Upload/goods/2015-05/55641f039eb22.jpg","goodsStock":"300","saleCount":"0","shopId":"4","marketPrice":"200.00","shopPrice":"150.00","goodsAttrId":null,"saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"255","goodsSn":"sn0012","goodsName":"xec","goodsThums":"Upload/goods/2018-03/5a9f9d2cbb8dd_thumb.png","goodsStock":"100","saleCount":"0","shopId":"34","marketPrice":"100.00","shopPrice":"20.00","goodsAttrId":"29","saleTime":"2018-03-07 16:14:01","totalScore":null,"totalUsers":null}],"totalPage":1,"currPage":1}}
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
         * maxPrice : 150.00
         * brands : []
         * pages : {"total":"7","pageSize":30,"start":0,"root":[{"goodsId":"2","goodsSn":"0001","goodsName":"wstmall墨西哥牛油果8个 7天内发货 8个装","goodsThums":"Upload/goods/2015-05/55641875b7501.jpg","goodsStock":"496","saleCount":"0","shopId":"34","marketPrice":"40.00","shopPrice":"36.00","goodsAttrId":null,"saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"3","goodsSn":"223655","goodsName":"进口 美国新奇士Sunkist 脐橙 6粒","goodsThums":"Upload/goods/2015-05/554c2c1a6a4b9.jpg","goodsStock":"299","saleCount":"0","shopId":"34","marketPrice":"40.00","shopPrice":"40.00","goodsAttrId":"10","saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"4","goodsSn":"00001","goodsName":"特色菜 半成品菜肴BCP-22 糖醋排条225g","goodsThums":"Upload/goods/2015-05/556b10094ea52.jpg","goodsStock":"133","saleCount":"0","shopId":"4","marketPrice":"20.00","shopPrice":"13.00","goodsAttrId":null,"saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"5","goodsSn":"0003","goodsName":"wstmal鲜果蔬 越南新鲜青芒果 5斤装 好吃的","goodsThums":"Upload/goods/2015-05/5563ef4681396.jpg","goodsStock":"300","saleCount":"0","shopId":"34","marketPrice":"55.00","shopPrice":"50.00","goodsAttrId":"15","saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"6","goodsSn":"0003","goodsName":"台湾金钻凤梨2粒/组（约2.5kg-3.2kg）","goodsThums":"Upload/goods/2015-05/5563f3941bd95.jpg","goodsStock":"5214","saleCount":"0","shopId":"4","marketPrice":"95.00","shopPrice":"56.00","goodsAttrId":null,"saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"7","goodsSn":"543512","goodsName":"wstmall汇绿鲜果蔬 限量发售泰国金枕榴莲带壳","goodsThums":"Upload/goods/2015-05/55641f039eb22.jpg","goodsStock":"300","saleCount":"0","shopId":"4","marketPrice":"200.00","shopPrice":"150.00","goodsAttrId":null,"saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"255","goodsSn":"sn0012","goodsName":"xec","goodsThums":"Upload/goods/2018-03/5a9f9d2cbb8dd_thumb.png","goodsStock":"100","saleCount":"0","shopId":"34","marketPrice":"100.00","shopPrice":"20.00","goodsAttrId":"29","saleTime":"2018-03-07 16:14:01","totalScore":null,"totalUsers":null}],"totalPage":1,"currPage":1}
         */

        private String maxPrice;
        private PagesBean pages;
        private List<?> brands;

        public String getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(String maxPrice) {
            this.maxPrice = maxPrice;
        }

        public PagesBean getPages() {
            return pages;
        }

        public void setPages(PagesBean pages) {
            this.pages = pages;
        }

        public List<?> getBrands() {
            return brands;
        }

        public void setBrands(List<?> brands) {
            this.brands = brands;
        }

        public static class PagesBean {
            /**
             * total : 7
             * pageSize : 30
             * start : 0
             * root : [{"goodsId":"2","goodsSn":"0001","goodsName":"wstmall墨西哥牛油果8个 7天内发货 8个装","goodsThums":"Upload/goods/2015-05/55641875b7501.jpg","goodsStock":"496","saleCount":"0","shopId":"34","marketPrice":"40.00","shopPrice":"36.00","goodsAttrId":null,"saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"3","goodsSn":"223655","goodsName":"进口 美国新奇士Sunkist 脐橙 6粒","goodsThums":"Upload/goods/2015-05/554c2c1a6a4b9.jpg","goodsStock":"299","saleCount":"0","shopId":"34","marketPrice":"40.00","shopPrice":"40.00","goodsAttrId":"10","saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"4","goodsSn":"00001","goodsName":"特色菜 半成品菜肴BCP-22 糖醋排条225g","goodsThums":"Upload/goods/2015-05/556b10094ea52.jpg","goodsStock":"133","saleCount":"0","shopId":"4","marketPrice":"20.00","shopPrice":"13.00","goodsAttrId":null,"saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"5","goodsSn":"0003","goodsName":"wstmal鲜果蔬 越南新鲜青芒果 5斤装 好吃的","goodsThums":"Upload/goods/2015-05/5563ef4681396.jpg","goodsStock":"300","saleCount":"0","shopId":"34","marketPrice":"55.00","shopPrice":"50.00","goodsAttrId":"15","saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"6","goodsSn":"0003","goodsName":"台湾金钻凤梨2粒/组（约2.5kg-3.2kg）","goodsThums":"Upload/goods/2015-05/5563f3941bd95.jpg","goodsStock":"5214","saleCount":"0","shopId":"4","marketPrice":"95.00","shopPrice":"56.00","goodsAttrId":null,"saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"7","goodsSn":"543512","goodsName":"wstmall汇绿鲜果蔬 限量发售泰国金枕榴莲带壳","goodsThums":"Upload/goods/2015-05/55641f039eb22.jpg","goodsStock":"300","saleCount":"0","shopId":"4","marketPrice":"200.00","shopPrice":"150.00","goodsAttrId":null,"saleTime":null,"totalScore":null,"totalUsers":null},{"goodsId":"255","goodsSn":"sn0012","goodsName":"xec","goodsThums":"Upload/goods/2018-03/5a9f9d2cbb8dd_thumb.png","goodsStock":"100","saleCount":"0","shopId":"34","marketPrice":"100.00","shopPrice":"20.00","goodsAttrId":"29","saleTime":"2018-03-07 16:14:01","totalScore":null,"totalUsers":null}]
             * totalPage : 1
             * currPage : 1
             */

            private String total;
            private int pageSize;
            private int start;
            private int totalPage;
            private int currPage;
            private List<RootBean> root;

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getStart() {
                return start;
            }

            public void setStart(int start) {
                this.start = start;
            }

            public int getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            public int getCurrPage() {
                return currPage;
            }

            public void setCurrPage(int currPage) {
                this.currPage = currPage;
            }

            public List<RootBean> getRoot() {
                return root;
            }

            public void setRoot(List<RootBean> root) {
                this.root = root;
            }

            public static class RootBean {
                /**
                 * goodsId : 2
                 * goodsSn : 0001
                 * goodsName : wstmall墨西哥牛油果8个 7天内发货 8个装
                 * goodsThums : Upload/goods/2015-05/55641875b7501.jpg
                 * goodsStock : 496
                 * saleCount : 0
                 * shopId : 34
                 * marketPrice : 40.00
                 * shopPrice : 36.00
                 * goodsAttrId : null
                 * saleTime : null
                 * totalScore : null
                 * totalUsers : null
                 */

                private int goodsId;
                private String goodsSn;
                private String goodsName;
                private String goodsThums;
                private String goodsStock;
                private int saleCount;
                private int shopId;
                private float marketPrice;
                private float shopPrice;

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public String getGoodsSn() {
                    return goodsSn;
                }

                public void setGoodsSn(String goodsSn) {
                    this.goodsSn = goodsSn;
                }

                public String getGoodsName() {
                    return goodsName;
                }

                public void setGoodsName(String goodsName) {
                    this.goodsName = goodsName;
                }

                public String getGoodsThums() {
                    return goodsThums;
                }

                public void setGoodsThums(String goodsThums) {
                    this.goodsThums = goodsThums;
                }

                public String getGoodsStock() {
                    return goodsStock;
                }

                public void setGoodsStock(String goodsStock) {
                    this.goodsStock = goodsStock;
                }

                public int getSaleCount() {
                    return saleCount;
                }

                public void setSaleCount(int saleCount) {
                    this.saleCount = saleCount;
                }

                public int getShopId() {
                    return shopId;
                }

                public void setShopId(int shopId) {
                    this.shopId = shopId;
                }

                public float getMarketPrice() {
                    return marketPrice;
                }

                public void setMarketPrice(float marketPrice) {
                    this.marketPrice = marketPrice;
                }

                public float getShopPrice() {
                    return shopPrice;
                }

                public void setShopPrice(float shopPrice) {
                    this.shopPrice = shopPrice;
                }
            }
        }
    }
}
