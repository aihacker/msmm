package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by lq on 2018/3/15.
 */

public class HaiBaoResponse extends BaseResponse {


    /**
     * data : {"userid":142,"title":"买手妈妈","logo":"https://msapi.maishoumm.com/images/logo.png","user_name":"朱潇潇洒","studentId":"888888","avatar":"http://www.maishoumm.com/attachment/ewei_shopv2/user/142/142.png","desc":"你不能不知道的天猫淘宝省钱内幕，薅羊毛首席攻略，就在这里！","url":"https://msmmapp.kuaizhan.com/?inviteCode=888888","ewm":"http://bshare.optimix.asia/barCode?site=weixin&url=https://msmmapp.kuaizhan.com/?inviteCode=888888","hbimg":[{"x":248,"y":870,"w":300,"h":300,"src":"https://msapi.maishoumm.com/share/hbposter/1.jpg"},{"x":248,"y":863,"w":200,"h":200,"src":"https://msapi.maishoumm.com/share/hbposter/2.jpg"},{"x":10,"y":1003,"w":200,"h":200,"src":"https://msapi.maishoumm.com/share/hbposter/3.jpg"},{"x":248,"y":943,"w":200,"h":200,"src":"https://msapi.maishoumm.com/share/hbposter/4.jpg"},{"x":248,"y":943,"w":200,"h":200,"src":"https://msapi.maishoumm.com/share/hbposter/5.jpg"}]}
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
         * userid : 142
         * title : 买手妈妈
         * logo : https://msapi.maishoumm.com/images/logo.png
         * user_name : 朱潇潇洒
         * studentId : 888888
         * avatar : http://www.maishoumm.com/attachment/ewei_shopv2/user/142/142.png
         * desc : 你不能不知道的天猫淘宝省钱内幕，薅羊毛首席攻略，就在这里！
         * url : https://msmmapp.kuaizhan.com/?inviteCode=888888
         * ewm : http://bshare.optimix.asia/barCode?site=weixin&url=https://msmmapp.kuaizhan.com/?inviteCode=888888
         * hbimg : [{"x":248,"y":870,"w":300,"h":300,"src":"https://msapi.maishoumm.com/share/hbposter/1.jpg"},{"x":248,"y":863,"w":200,"h":200,"src":"https://msapi.maishoumm.com/share/hbposter/2.jpg"},{"x":10,"y":1003,"w":200,"h":200,"src":"https://msapi.maishoumm.com/share/hbposter/3.jpg"},{"x":248,"y":943,"w":200,"h":200,"src":"https://msapi.maishoumm.com/share/hbposter/4.jpg"},{"x":248,"y":943,"w":200,"h":200,"src":"https://msapi.maishoumm.com/share/hbposter/5.jpg"}]
         */

        private int userid;
        private String title;
        private String logo;
        private String user_name;
        private String studentId;
        private String avatar;
        private String desc;
        private String url;
        private String ewm;
        private String guize;
        private List<HbimgBean> hbimg;

        public String getGuize() {
            return guize;
        }

        public void setGuize(String guize) {
            this.guize = guize;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEwm() {
            return ewm;
        }

        public void setEwm(String ewm) {
            this.ewm = ewm;
        }

        public List<HbimgBean> getHbimg() {
            return hbimg;
        }

        public void setHbimg(List<HbimgBean> hbimg) {
            this.hbimg = hbimg;
        }

        public static class HbimgBean {
            /**
             * x : 248
             * y : 870
             * w : 300
             * h : 300
             * src : https://msapi.maishoumm.com/share/hbposter/1.jpg
             */

            private int x;
            private int y;
            private int w;
            private int h;
            private String src;

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }

            public int getH() {
                return h;
            }

            public void setH(int h) {
                this.h = h;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }
        }
    }
}
