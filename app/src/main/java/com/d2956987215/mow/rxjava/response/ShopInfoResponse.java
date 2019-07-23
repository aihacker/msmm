package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class ShopInfoResponse extends BaseResponse{

    /**
     * data : {"consumerProtection":[{"serviceId":"3838","title":"正品保证","desc":"商品支持正品保障服务","type":"0","priority":"12900"},{"serviceId":"2979","title":"极速退款","desc":"极速退款是为诚信会员提供的退款退货流程的专享特权，额度是根据每个用户当前的信誉评级情况而定","type":"0","priority":"6505"},{"serviceId":"3521","title":"不支持7天退换","desc":"不支持7天退换","type":"0","priority":"5060"}],"seller":{"shopName":"hto水果旗舰店","shopIcon":"https://img.alicdn.com/imgextra//77/81/TB1lYYLMlLoK1RjSZFuSutn0XXa.jpg","shopUrl":"https://shop451891797.m.taobao.com","evaluates":[{"title":"宝贝描述","score":"4.8 ","type":"desc","level":"1","levelText":"高","levelTextColor":"#999999","levelBackgroundColor":"#EEEEEE","tmallLevelTextColor":"#999999","tmallLevelBackgroundColor":"#EEEEEE"},{"title":"卖家服务","score":"4.8 ","type":"serv","level":"1","levelText":"高","levelTextColor":"#999999","levelBackgroundColor":"#EEEEEE","tmallLevelTextColor":"#999999","tmallLevelBackgroundColor":"#EEEEEE"},{"title":"物流服务","score":"4.8 ","type":"post","level":"1","levelText":"高","levelTextColor":"#999999","levelBackgroundColor":"#EEEEEE","tmallLevelTextColor":"#999999","tmallLevelBackgroundColor":"#EEEEEE"}]}}
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
         * consumerProtection : [{"serviceId":"3838","title":"正品保证","desc":"商品支持正品保障服务","type":"0","priority":"12900"},{"serviceId":"2979","title":"极速退款","desc":"极速退款是为诚信会员提供的退款退货流程的专享特权，额度是根据每个用户当前的信誉评级情况而定","type":"0","priority":"6505"},{"serviceId":"3521","title":"不支持7天退换","desc":"不支持7天退换","type":"0","priority":"5060"}]
         * seller : {"shopName":"hto水果旗舰店","shopIcon":"https://img.alicdn.com/imgextra//77/81/TB1lYYLMlLoK1RjSZFuSutn0XXa.jpg","shopUrl":"https://shop451891797.m.taobao.com","evaluates":[{"title":"宝贝描述","score":"4.8 ","type":"desc","level":"1","levelText":"高","levelTextColor":"#999999","levelBackgroundColor":"#EEEEEE","tmallLevelTextColor":"#999999","tmallLevelBackgroundColor":"#EEEEEE"},{"title":"卖家服务","score":"4.8 ","type":"serv","level":"1","levelText":"高","levelTextColor":"#999999","levelBackgroundColor":"#EEEEEE","tmallLevelTextColor":"#999999","tmallLevelBackgroundColor":"#EEEEEE"},{"title":"物流服务","score":"4.8 ","type":"post","level":"1","levelText":"高","levelTextColor":"#999999","levelBackgroundColor":"#EEEEEE","tmallLevelTextColor":"#999999","tmallLevelBackgroundColor":"#EEEEEE"}]}
         */

        private SellerBean seller;
        private List<ConsumerProtectionBean> consumerProtection;

        public SellerBean getSeller() {
            return seller;
        }

        public void setSeller(SellerBean seller) {
            this.seller = seller;
        }

        public List<ConsumerProtectionBean> getConsumerProtection() {
            return consumerProtection;
        }

        public void setConsumerProtection(List<ConsumerProtectionBean> consumerProtection) {
            this.consumerProtection = consumerProtection;
        }

        public static class SellerBean {
            /**
             * shopName : hto水果旗舰店
             * shopIcon : https://img.alicdn.com/imgextra//77/81/TB1lYYLMlLoK1RjSZFuSutn0XXa.jpg
             * shopUrl : https://shop451891797.m.taobao.com
             * evaluates : [{"title":"宝贝描述","score":"4.8 ","type":"desc","level":"1","levelText":"高","levelTextColor":"#999999","levelBackgroundColor":"#EEEEEE","tmallLevelTextColor":"#999999","tmallLevelBackgroundColor":"#EEEEEE"},{"title":"卖家服务","score":"4.8 ","type":"serv","level":"1","levelText":"高","levelTextColor":"#999999","levelBackgroundColor":"#EEEEEE","tmallLevelTextColor":"#999999","tmallLevelBackgroundColor":"#EEEEEE"},{"title":"物流服务","score":"4.8 ","type":"post","level":"1","levelText":"高","levelTextColor":"#999999","levelBackgroundColor":"#EEEEEE","tmallLevelTextColor":"#999999","tmallLevelBackgroundColor":"#EEEEEE"}]
             */

            private String shopName;
            private String shopIcon;
            private String shopUrl;
            private List<EvaluatesBean> evaluates;

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getShopIcon() {
                return shopIcon;
            }

            public void setShopIcon(String shopIcon) {
                this.shopIcon = shopIcon;
            }

            public String getShopUrl() {
                return shopUrl;
            }

            public void setShopUrl(String shopUrl) {
                this.shopUrl = shopUrl;
            }

            public List<EvaluatesBean> getEvaluates() {
                return evaluates;
            }

            public void setEvaluates(List<EvaluatesBean> evaluates) {
                this.evaluates = evaluates;
            }

            public static class EvaluatesBean {
                /**
                 * title : 宝贝描述
                 * score : 4.8
                 * type : desc
                 * level : 1
                 * levelText : 高
                 * levelTextColor : #999999
                 * levelBackgroundColor : #EEEEEE
                 * tmallLevelTextColor : #999999
                 * tmallLevelBackgroundColor : #EEEEEE
                 */

                private String title;
                private String score;
                private String type;
                private String level;
                private String levelText;
                private String levelTextColor;
                private String levelBackgroundColor;
                private String tmallLevelTextColor;
                private String tmallLevelBackgroundColor;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
                }

                public String getLevelText() {
                    return levelText;
                }

                public void setLevelText(String levelText) {
                    this.levelText = levelText;
                }

                public String getLevelTextColor() {
                    return levelTextColor;
                }

                public void setLevelTextColor(String levelTextColor) {
                    this.levelTextColor = levelTextColor;
                }

                public String getLevelBackgroundColor() {
                    return levelBackgroundColor;
                }

                public void setLevelBackgroundColor(String levelBackgroundColor) {
                    this.levelBackgroundColor = levelBackgroundColor;
                }

                public String getTmallLevelTextColor() {
                    return tmallLevelTextColor;
                }

                public void setTmallLevelTextColor(String tmallLevelTextColor) {
                    this.tmallLevelTextColor = tmallLevelTextColor;
                }

                public String getTmallLevelBackgroundColor() {
                    return tmallLevelBackgroundColor;
                }

                public void setTmallLevelBackgroundColor(String tmallLevelBackgroundColor) {
                    this.tmallLevelBackgroundColor = tmallLevelBackgroundColor;
                }
            }
        }

        public static class ConsumerProtectionBean {
            /**
             * serviceId : 3838
             * title : 正品保证
             * desc : 商品支持正品保障服务
             * type : 0
             * priority : 12900
             */

            private String serviceId;
            private String title;
            private String desc;
            private String type;
            private String priority;

            public String getServiceId() {
                return serviceId;
            }

            public void setServiceId(String serviceId) {
                this.serviceId = serviceId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPriority() {
                return priority;
            }

            public void setPriority(String priority) {
                this.priority = priority;
            }
        }
    }
}
