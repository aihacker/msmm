package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class BrandResponse extends BaseResponse{

    /**
     * data : {"brand_list":[{"id":1,"brandName":"三只松鼠","brandLogo":"http://img02.taobaocdn.com:80/tfscom/TB1jsD.DxjaK1RjSZFAXXbdLFXa"},{"id":2,"brandName":"百草味","brandLogo":"http://img02.taobaocdn.com:80/tfscom/TB1JCfSDwTqK1RjSZPhXXXfOFXa"},{"id":5,"brandName":"特步","brandLogo":"http://img01.taobaocdn.com:80/tfscom/TB1P3z2DwHqK1RjSZFPXXcwapXa"},{"id":9,"brandName":"蓝月亮","brandLogo":"http://img04.taobaocdn.com:80/tfscom/TB1ogrODzDpK1RjSZFrXXa78VXa"},{"id":10,"brandName":"清风","brandLogo":"http://img04.taobaocdn.com:80/tfscom/TB1TvrWDr2pK1RjSZFsXXaNlXXa"},{"id":11,"brandName":"维达","brandLogo":"http://img04.taobaocdn.com:80/tfscom/TB1P1f.DxjaK1RjSZFAXXbdLFXa"},{"id":12,"brandName":"良品铺子","brandLogo":"http://img03.taobaocdn.com:80/tfscom/TB1LBjWDCzqK1RjSZFLXXcn2XXa"},{"id":17,"brandName":"拉芳","brandLogo":"http://img04.taobaocdn.com:80/tfscom/TB1RXz3DAvoK1RjSZFDXXXY3pXa"}],"cate":[{"brandcat":1,"brandName":"母婴童品"},{"brandcat":2,"brandName":"百变女装"},{"brandcat":3,"brandName":"食品酒水"},{"brandcat":4,"brandName":"居家日用"},{"brandcat":5,"brandName":"美妆洗护"},{"brandcat":6,"brandName":"品质男装"},{"brandcat":7,"brandName":"舒适内衣"},{"brandcat":8,"brandName":"箱包配饰"},{"brandcat":9,"brandName":"男女鞋靴"},{"brandcat":10,"brandName":"宠物用品"},{"brandcat":11,"brandName":"数码家电"},{"brandcat":12,"brandName":"车品文体"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<BrandListBean> brand_list;
        private List<CateBean> cate;

        public List<BrandListBean> getBrand_list() {
            return brand_list;
        }

        public void setBrand_list(List<BrandListBean> brand_list) {
            this.brand_list = brand_list;
        }

        public List<CateBean> getCate() {
            return cate;
        }

        public void setCate(List<CateBean> cate) {
            this.cate = cate;
        }

        public static class BrandListBean {
            /**
             * id : 1
             * brandName : 三只松鼠
             * brandLogo : http://img02.taobaocdn.com:80/tfscom/TB1jsD.DxjaK1RjSZFAXXbdLFXa
             */

            private String id;
            private String brandName;
            private String brandLogo;
            private String brandCommission;
            private String shop_url;

            public String getShop_url() {
                return shop_url;
            }

            public void setShop_url(String shop_url) {
                this.shop_url = shop_url;
            }

            public String getBrandCommission() {
                return brandCommission;
            }

            public void setBrandCommission(String brandCommission) {
                this.brandCommission = brandCommission;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getBrandLogo() {
                return brandLogo;
            }

            public void setBrandLogo(String brandLogo) {
                this.brandLogo = brandLogo;
            }
        }

        public static class CateBean {
            /**
             * brandcat : 1
             * brandName : 母婴童品
             */

            private String brandcat;
            private String brandName;

            public String getBrandcat() {
                return brandcat;
            }

            public void setBrandcat(String brandcat) {
                this.brandcat = brandcat;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }
        }
    }
}
