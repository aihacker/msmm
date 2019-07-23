package com.d2956987215.mow.rxjava.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lq on 2018/3/15.
 */

public class PersonInfoResponse extends BaseResponse implements Serializable {

    /**
     * data : {"userName":"sn001","userPhoto":null}
     */

    private DataBean data;

    public DataBean getRetval() {
        return data;
    }

    public void setRetval(DataBean retval) {
        this.data = retval;
    }

    public static class DataBean implements Serializable {
        /**
         * userName : sn001
         * userPhoto : null
         * "pre_money": "0元",
         * "this_money": "0元",
         * "t_money": "0元",
         * "teamnum": 0
         */

        private String ww;
        private String avatar;
        private String name;
        private String mobile;
        private String t_money;
        private String pre_money;
        private String this_money;
        private String roletypes;
        private String uproletypes;
        private String contactid;
        private String avatarUrl;
        private String tj_wx;
        private String studentId;
        private String ewm;
        private String tj_qrcode;
        private String text;
        private String title;
        private String credit2;
        private String realname;
        private String alipayname;
        private String deadline;
        private String alipayusername;
        private String xszn;
        private String guize;

        private String wxqrcode;
        private String istaobao;
        private String contact;

        private String weixin;
        private List<BannerBean> banner;
        private int needrecord;
        private String groundPush;
        private int message_push;
        private int money_push;
        private int addmember_push;

        private NewsBean news;

        public NewsBean getNews() {
            return news;
        }

        public void setNews(NewsBean news) {
            this.news = news;
        }

        public int getMessage_push() {
            return message_push;
        }

        public void setMessage_push(int message_push) {
            this.message_push = message_push;
        }

        public int getMoney_push() {
            return money_push;
        }

        public void setMoney_push(int money_push) {
            this.money_push = money_push;
        }

        public int getAddmember_push() {
            return addmember_push;
        }

        public void setAddmember_push(int addmember_push) {
            this.addmember_push = addmember_push;
        }

        public String getGroundPush() {
            return groundPush;
        }

        public void setGroundPush(String groundPush) {
            this.groundPush = groundPush;
        }

        public int getNeedrecord() {
            return needrecord;
        }

        public void setNeedrecord(int needrecord) {
            this.needrecord = needrecord;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public String getUproletypes() {
            return uproletypes;
        }

        public void setUproletypes(String uproletypes) {
            this.uproletypes = uproletypes;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getContactid() {
            return contactid;
        }

        public void setContactid(String contactid) {
            this.contactid = contactid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getGuize() {
            return guize;
        }

        public void setGuize(String guize) {
            this.guize = guize;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getIstaobao() {
            return istaobao;
        }

        public void setIstaobao(String istaobao) {
            this.istaobao = istaobao;
        }

        public String getWxqrcode() {
            return wxqrcode;
        }

        public void setWxqrcode(String wxqrcode) {
            this.wxqrcode = wxqrcode;
        }

        public String getXszn() {
            return xszn;
        }

        public void setXszn(String xszn) {
            this.xszn = xszn;
        }

        public String getAlipayusername() {
            return alipayusername;
        }

        public void setAlipayusername(String alipayusername) {
            this.alipayusername = alipayusername;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getAlipayname() {
            return alipayname;
        }

        public void setAlipayname(String alipayname) {
            this.alipayname = alipayname;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getCredit2() {
            return credit2;
        }

        public void setCredit2(String credit2) {
            this.credit2 = credit2;
        }

        public String getTj_qrcode() {
            return tj_qrcode;
        }

        public void setTj_qrcode(String tj_qrcode) {
            this.tj_qrcode = tj_qrcode;
        }

        public String getEwm() {
            return ewm;
        }

        public void setEwm(String ewm) {
            this.ewm = ewm;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getTj_wx() {
            return tj_wx;
        }

        public void setTj_wx(String tj_wx) {
            this.tj_wx = tj_wx;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getRoletypes() {
            return roletypes;
        }

        public void setRoletypes(String roletypes) {
            this.roletypes = roletypes;
        }

        public String getPre_money() {
            return pre_money;
        }

        public void setPre_money(String pre_money) {
            this.pre_money = pre_money;
        }

        public String getThis_money() {
            return this_money;
        }

        public void setThis_money(String this_money) {
            this.this_money = this_money;
        }

        public String getT_money() {
            return t_money;
        }

        public void setT_money(String t_money) {
            this.t_money = t_money;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getWw() {
            return ww;
        }

        public void setWw(String ww) {
            this.ww = ww;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static class BannerBean {
            /**
             * id : 6
             * title : 鸡蛋
             * picUrl : https://images.maishoumm.com/banner/1553651887875.jpg
             * quan_id :
             * needLogin : 1
             * directType : 1
             * url :
             */

            private String id;
            private String title;
            private String picUrl;
            private String quan_id;
            private int needLogin;
            private int needrecord;
            private int directType;
            private String url;
            private int status;
            private int RoleType;
            private String aid;

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getRoleType() {
                return RoleType;
            }

            public void setRoleType(int roleType) {
                RoleType = roleType;
            }

            public int getNeedrecord() {
                return needrecord;
            }

            public void setNeedrecord(int needrecord) {
                this.needrecord = needrecord;
            }

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

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getQuan_id() {
                return quan_id;
            }

            public void setQuan_id(String quan_id) {
                this.quan_id = quan_id;
            }

            public int getNeedLogin() {
                return needLogin;
            }

            public void setNeedLogin(int needLogin) {
                this.needLogin = needLogin;
            }

            public int getDirectType() {
                return directType;
            }

            public void setDirectType(int directType) {
                this.directType = directType;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class NewsBean {
            private String roletype;
            private String text;

            public String getRoletype() {
                return roletype;
            }

            public void setRoletype(String roletype) {
                this.roletype = roletype;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
