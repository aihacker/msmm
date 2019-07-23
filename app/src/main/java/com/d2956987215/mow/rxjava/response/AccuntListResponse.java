package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by Administrator on 2018/3/8.
 */

public class AccuntListResponse extends BaseResponse {


    /**
     * data : {"current_page":1,"data":[{"id":21104,"addtime":"2018-09-23 15:23:45","credit2":"8.57","sh":"1","createtime":"2018-09-22 09:22:37","pay_date":"2018-09-23 15:23:44","rsub_msg":"单日最多可转100万元","zfbuid":"15093829823","alipayname":"纪鹏乐","status":"已打款","avatar":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=Mmlev8QWOFHbX87hPmxbOFHGXm8yMG7hMHxWPF9ePFIT&width=160&height=160&type=sns"}],"from":1,"last_page":1,"next_page_url":null,"path":"https://msapi.maishoumm.com/api/v1/MyBill","per_page":10,"prev_page_url":null,"to":1,"total":1}
     */

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * current_page : 1
         * data : [{"id":21104,"addtime":"2018-09-23 15:23:45","credit2":"8.57","sh":"1","createtime":"2018-09-22 09:22:37","pay_date":"2018-09-23 15:23:44","rsub_msg":"单日最多可转100万元","zfbuid":"15093829823","alipayname":"纪鹏乐","status":"已打款","avatar":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=Mmlev8QWOFHbX87hPmxbOFHGXm8yMG7hMHxWPF9ePFIT&width=160&height=160&type=sns"}]
         * from : 1
         * last_page : 1
         * next_page_url : null
         * path : https://msapi.maishoumm.com/api/v1/MyBill
         * per_page : 10
         * prev_page_url : null
         * to : 1
         * total : 1
         */

        private int current_page;
        private int from;
        private int last_page;
        private Object next_page_url;
        private String path;
        private int per_page;
        private Object prev_page_url;
        private int to;
        private int total;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public Object getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(Object next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public Object getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(Object prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 21104
             * addtime : 2018-09-23 15:23:45
             * credit2 : 8.57
             * sh : 1
             * createtime : 2018-09-22 09:22:37
             * pay_date : 2018-09-23 15:23:44
             * rsub_msg : 单日最多可转100万元
             * zfbuid : 15093829823
             * alipayname : 纪鹏乐
             * status : 已打款
             * avatar : https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=Mmlev8QWOFHbX87hPmxbOFHGXm8yMG7hMHxWPF9ePFIT&width=160&height=160&type=sns
             */

            private int id;
            private String addtime;
            private String credit2;
            private String sh;
            private String createtime;
            private String pay_date;
            private String rsub_msg;
            private String zfbuid;
            private String alipayname;
            private String status;
            private String avatar;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getCredit2() {
                return credit2;
            }

            public void setCredit2(String credit2) {
                this.credit2 = credit2;
            }

            public String getSh() {
                return sh;
            }

            public void setSh(String sh) {
                this.sh = sh;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getPay_date() {
                return pay_date;
            }

            public void setPay_date(String pay_date) {
                this.pay_date = pay_date;
            }

            public String getRsub_msg() {
                return rsub_msg;
            }

            public void setRsub_msg(String rsub_msg) {
                this.rsub_msg = rsub_msg;
            }

            public String getZfbuid() {
                return zfbuid;
            }

            public void setZfbuid(String zfbuid) {
                this.zfbuid = zfbuid;
            }

            public String getAlipayname() {
                return alipayname;
            }

            public void setAlipayname(String alipayname) {
                this.alipayname = alipayname;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}
