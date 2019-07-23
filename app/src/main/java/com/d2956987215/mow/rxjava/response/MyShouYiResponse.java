package com.d2956987215.mow.rxjava.response;


public class MyShouYiResponse extends BaseResponse{


    /**
     * data : {"credit2":"0.00","last_month_price":0,"last_month_jsprice":0,"this_month_price":0,"this_month_jsprice":0,"yesterday_info":{"price":0,"jsprice":0,"count":0},"today_info":{"price":0,"jsprice":0,"count":0}}
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
         * credit2 : 0.00
         * last_month_price : 0
         * last_month_jsprice : 0
         * this_month_price : 0
         * this_month_jsprice : 0
         * yesterday_info : {"price":0,"jsprice":0,"count":0}
         * today_info : {"price":0,"jsprice":0,"count":0}
         */

        private String credit2;
        private String last_month_price;
        private String last_month_jsprice;
        private String this_month_price;
        private String this_month_jsprice;
        private YesterdayInfoBean yesterday_info;
        private TodayInfoBean today_info;
        private String alljs;

        public String getAlljs() {
            return alljs;
        }

        public void setAlljs(String alljs) {
            this.alljs = alljs;
        }

        public String getCredit2() {
            return credit2;
        }

        public void setCredit2(String credit2) {
            this.credit2 = credit2;
        }

        public String getLast_month_price() {
            return last_month_price;
        }

        public void setLast_month_price(String last_month_price) {
            this.last_month_price = last_month_price;
        }

        public String getLast_month_jsprice() {
            return last_month_jsprice;
        }

        public void setLast_month_jsprice(String last_month_jsprice) {
            this.last_month_jsprice = last_month_jsprice;
        }

        public String getThis_month_price() {
            return this_month_price;
        }

        public void setThis_month_price(String this_month_price) {
            this.this_month_price = this_month_price;
        }

        public String getThis_month_jsprice() {
            return this_month_jsprice;
        }

        public void setThis_month_jsprice(String this_month_jsprice) {
            this.this_month_jsprice = this_month_jsprice;
        }

        public YesterdayInfoBean getYesterday_info() {
            return yesterday_info;
        }

        public void setYesterday_info(YesterdayInfoBean yesterday_info) {
            this.yesterday_info = yesterday_info;
        }

        public TodayInfoBean getToday_info() {
            return today_info;
        }

        public void setToday_info(TodayInfoBean today_info) {
            this.today_info = today_info;
        }

        public static class YesterdayInfoBean {
            /**
             * price : 0
             * jsprice : 0
             * count : 0
             */

            private String price;
            private String jsprice;
            private String count;


            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getJsprice() {
                return jsprice;
            }

            public void setJsprice(String jsprice) {
                this.jsprice = jsprice;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }

        public static class TodayInfoBean {
            /**
             * price : 0
             * jsprice : 0
             * count : 0
             */

            private String price;
            private String jsprice;
            private String count;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getJsprice() {
                return jsprice;
            }

            public void setJsprice(String jsprice) {
                this.jsprice = jsprice;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }
    }
}
