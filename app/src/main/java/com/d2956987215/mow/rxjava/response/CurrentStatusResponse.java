package com.d2956987215.mow.rxjava.response;

/**
 * 作者：闫亚锋
 * 时间：2018/10/19 10:34
 * 说明：
 */
public class CurrentStatusResponse extends BaseResponse{


    /**
     * data : {"h5url":"http://msapi.maishoumm.com/vip/monitor.html","left":{"clicktype":0,"title":"结算佣金380+50人免费升","executetype":0,"executeAPi":""},"right":{"clicktype":7,"title":"立即升级","executetype":1,"executeAPi":"/api/v1/VipUpdate"}}
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
         * h5url : http://msapi.maishoumm.com/vip/monitor.html
         * left : {"clicktype":0,"title":"结算佣金380+50人免费升","executetype":0,"executeAPi":""}
         * right : {"clicktype":7,"title":"立即升级","executetype":1,"executeAPi":"/api/v1/VipUpdate"}
         */

        private String title;
        private String h5url;
        private LeftBean left;
        private RightBean right;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getH5url() {
            return h5url;
        }

        public void setH5url(String h5url) {
            this.h5url = h5url;
        }

        public LeftBean getLeft() {
            return left;
        }

        public void setLeft(LeftBean left) {
            this.left = left;
        }

        public RightBean getRight() {
            return right;
        }

        public void setRight(RightBean right) {
            this.right = right;
        }

        public static class LeftBean {
            /**
             * clicktype : 0
             * title : 结算佣金380+50人免费升
             * executetype : 0
             * executeAPi :
             */

            private int clicktype;
            private String title;
            private int executetype;
            private String executeAPi;

            public int getClicktype() {
                return clicktype;
            }

            public void setClicktype(int clicktype) {
                this.clicktype = clicktype;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getExecutetype() {
                return executetype;
            }

            public void setExecutetype(int executetype) {
                this.executetype = executetype;
            }

            public String getExecuteAPi() {
                return executeAPi;
            }

            public void setExecuteAPi(String executeAPi) {
                this.executeAPi = executeAPi;
            }
        }

        public static class RightBean {
            /**
             * clicktype : 7
             * title : 立即升级
             * executetype : 1
             * executeAPi : /api/v1/VipUpdate
             */

            private int clicktype;
            private String title;
            private int executetype;
            private String executeAPi;

            public int getClicktype() {
                return clicktype;
            }

            public void setClicktype(int clicktype) {
                this.clicktype = clicktype;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getExecutetype() {
                return executetype;
            }

            public void setExecutetype(int executetype) {
                this.executetype = executetype;
            }

            public String getExecuteAPi() {
                return executeAPi;
            }

            public void setExecuteAPi(String executeAPi) {
                this.executeAPi = executeAPi;
            }
        }
    }
}
