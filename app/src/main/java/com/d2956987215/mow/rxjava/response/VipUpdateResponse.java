package com.d2956987215.mow.rxjava.response;

/**
 * 作者：闫亚锋
 * 时间：2018/10/19 10:34
 * 说明：
 */
public class VipUpdateResponse extends BaseResponse{


    /**
     * data : {"is_full":1,"clicktype":1,"clicktitle":"联系客服！","clickurl":""}
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
         * is_full : 1
         * clicktype : 1
         * clicktitle : 联系客服！
         * clickurl :
         */

        private int is_full;
        private int clicktype;
        private String clicktitle;
        private String clickurl;

        public int getIs_full() {
            return is_full;
        }

        public void setIs_full(int is_full) {
            this.is_full = is_full;
        }

        public int getClicktype() {
            return clicktype;
        }

        public void setClicktype(int clicktype) {
            this.clicktype = clicktype;
        }

        public String getClicktitle() {
            return clicktitle;
        }

        public void setClicktitle(String clicktitle) {
            this.clicktitle = clicktitle;
        }

        public String getClickurl() {
            return clickurl;
        }

        public void setClickurl(String clickurl) {
            this.clickurl = clickurl;
        }
    }
}
