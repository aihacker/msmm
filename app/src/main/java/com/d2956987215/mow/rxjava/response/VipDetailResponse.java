package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by lq on 2018/3/15.
 */

public class VipDetailResponse extends BaseResponse {

    private DataBean data;
    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean {
        private String mrid;
        private Privilege privilege;
        private Explain explain;
        private List<SpecOptions> spec_options;

        public List<SpecOptions> getSpec_options() {
            return spec_options;
        }

        public void setSpec_options(List<SpecOptions> spec_options) {
            this.spec_options = spec_options;
        }

        public String getMrid() {
            return mrid;
        }

        public void setMrid(String mrid) {
            this.mrid = mrid;
        }

        public Privilege getPrivilege() {
            return privilege;
        }

        public void setPrivilege(Privilege privilege) {
            this.privilege = privilege;
        }

        public Explain getExplain() {
            return explain;
        }

        public void setExplain(Explain explain) {
            this.explain = explain;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public class Privilege {
            private String id;
            private String title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDeleted() {
                return deleted;
            }

            public void setDeleted(String deleted) {
                this.deleted = deleted;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getBuy_hasoption() {
                return buy_hasoption;
            }

            public void setBuy_hasoption(String buy_hasoption) {
                this.buy_hasoption = buy_hasoption;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getThumb_url() {
                return thumb_url;
            }

            public void setThumb_url(String thumb_url) {
                this.thumb_url = thumb_url;
            }

            public String getThumb_first() {
                return thumb_first;
            }

            public void setThumb_first(String thumb_first) {
                this.thumb_first = thumb_first;
            }

            public String getBuy_marketprice() {
                return buy_marketprice;
            }

            public void setBuy_marketprice(String buy_marketprice) {
                this.buy_marketprice = buy_marketprice;
            }

            public String getRenew_marketprice() {
                return renew_marketprice;
            }

            public void setRenew_marketprice(String renew_marketprice) {
                this.renew_marketprice = renew_marketprice;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getSalesreal() {
                return salesreal;
            }

            public void setSalesreal(String salesreal) {
                this.salesreal = salesreal;
            }

            public String getBzwx() {
                return bzwx;
            }

            public void setBzwx(String bzwx) {
                this.bzwx = bzwx;
            }

            public boolean isCanbuy() {
                return canbuy;
            }

            public void setCanbuy(boolean canbuy) {
                this.canbuy = canbuy;
            }

            private String status;
            private String deleted;
            private String total;
            private String buy_hasoption;
            private String thumb;
            private String thumb_url;
            private String thumb_first;
            private String buy_marketprice;
            private String renew_marketprice;
            private String sales;
            private String unit;
            private String salesreal;
            private String bzwx;
            private boolean canbuy;
        }

        public class Explain {
            private String renew;
            private String deadline;
            private String typename;

            public String getDeadline() {
                return deadline;
            }

            public void setDeadline(String deadline) {
                this.deadline = deadline;
            }

            private String name;
            private String name1;
            private List<Tq> tq;
            private String name2;
            private List<Tq> tq1;
            private String bzwx;

            public String getRenew() {
                return renew;
            }

            public void setRenew(String renew) {
                this.renew = renew;
            }

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName1() {
                return name1;
            }

            public void setName1(String name1) {
                this.name1 = name1;
            }

            public List<Tq> getTq() {
                return tq;
            }

            public void setTq(List<Tq> tq) {
                this.tq = tq;
            }

            public String getName2() {
                return name2;
            }

            public void setName2(String name2) {
                this.name2 = name2;
            }

            public List<Tq> getTq1() {
                return tq1;
            }

            public void setTq1(List<Tq> tq1) {
                this.tq1 = tq1;
            }

            public String getBzwx() {
                return bzwx;
            }

            public void setBzwx(String bzwx) {
                this.bzwx = bzwx;
            }

            public class Tq {
                private String tq_t;
                private String tq_d;
                private String tq_img;

                public String getTq_img() {
                    return tq_img;
                }

                public void setTq_img(String tq_img) {
                    this.tq_img = tq_img;
                }

                public String getTq_t() {
                    return tq_t;
                }

                public void setTq_t(String tq_t) {
                    this.tq_t = tq_t;
                }

                public String getTq_d() {
                    return tq_d;
                }

                public void setTq_d(String tq_d) {
                    this.tq_d = tq_d;
                }
            }
        }

        public class SpecOptions {
            private String id;
            private String tag;
            private String expiry_date;
            private String titles;
            private String title;
            private String price;
            private String marketprice;
            private String stock;
            private String ismr;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getExpiry_date() {
                return expiry_date;
            }

            public void setExpiry_date(String expiry_date) {
                this.expiry_date = expiry_date;
            }

            public String getTitles() {
                return titles;
            }

            public void setTitles(String titles) {
                this.titles = titles;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getMarketprice() {
                return marketprice;
            }

            public void setMarketprice(String marketprice) {
                this.marketprice = marketprice;
            }

            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
            }

            public String getIsmr() {
                return ismr;
            }

            public void setIsmr(String ismr) {
                this.ismr = ismr;
            }
        }

        private String studentId;
    }

}
