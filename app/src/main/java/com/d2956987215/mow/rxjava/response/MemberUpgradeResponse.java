package com.d2956987215.mow.rxjava.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberUpgradeResponse extends BaseResponse {

    /**
     * data : {"userinfo":{"realname":"","avatar":"","rolename":"一年买手","roletype":1,"deadline":"2020-03-28 12:01:44"},"isopen":[{"icon":"","title":"买手权限","text":"一年"},{"icon":"","title":"全网优惠券","text":"淘宝,京东..."},{"icon":"","title":"购物能返佣","text":"最高90%"},{"icon":"","title":"爆款群好货","text":"每日更新"}],"noopen":[{"icon":"","title":"买手权限","text":"永久"},{"icon":"","title":"拼多多购物返佣","text":"最高90%"},{"icon":"","title":"京东购物返佣","text":"最高90%"},{"icon":"","title":"买手商学院","text":"一年"},{"icon":"","title":"专属班长辅导","text":"一年"}],"conditionOne":[{"finishOneLevelCount":0,"message":"邀请2个买手自动升级永久.","oneLevelCount":2,"schedule":"=0/2(人)"}],"wx":[{"title":"专属大班长","text":"联系班长进入买手专属培训群","avatar":"","realname":"朱潇洒","wx":"zxs645999223","wxqrcode":""},{"title":"专属客服","text":"解决专业问题","avatar":"","realname":"朱潇洒","wx":"zxs645999223","wxqrcode":""}],"rolecolor":1}
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
         * userinfo : {"realname":"","avatar":"","rolename":"一年买手","roletype":1,"deadline":"2020-03-28 12:01:44"}
         * isopen : [{"icon":"","title":"买手权限","text":"一年"},{"icon":"","title":"全网优惠券","text":"淘宝,京东..."},{"icon":"","title":"购物能返佣","text":"最高90%"},{"icon":"","title":"爆款群好货","text":"每日更新"}]
         * noopen : [{"icon":"","title":"买手权限","text":"永久"},{"icon":"","title":"拼多多购物返佣","text":"最高90%"},{"icon":"","title":"京东购物返佣","text":"最高90%"},{"icon":"","title":"买手商学院","text":"一年"},{"icon":"","title":"专属班长辅导","text":"一年"}]
         * conditionOne : [{"finishOneLevelCount":0,"message":"邀请2个买手自动升级永久.","oneLevelCount":2,"schedule":"=0/2(人)"}]
         * wx : [{"title":"专属大班长","text":"联系班长进入买手专属培训群","avatar":"","realname":"朱潇洒","wx":"zxs645999223","wxqrcode":""},{"title":"专属客服","text":"解决专业问题","avatar":"","realname":"朱潇洒","wx":"zxs645999223","wxqrcode":""}]
         * rolecolor : 1
         */

        private UserinfoBean userinfo;
        private int rolecolor;
        private List<IsopenBean> isopen;
        private List<NoopenBean> noopen;
        private List<ConditionOneBean> conditionOne;
        private List<ConditionTwoBean> conditionTwo;
        private List<WxBean> wx;
        private List<ArticleBean> article;
        private String ruleUrl;

        public String getRuleUrl() {
            return ruleUrl;
        }

        public void setRuleUrl(String ruleUrl) {
            this.ruleUrl = ruleUrl;
        }

        public List<ArticleBean> getArticle() {
            return article;
        }

        public void setArticle(List<ArticleBean> article) {
            this.article = article;
        }

        public List<ConditionTwoBean> getConditionTwo() {
            return conditionTwo;
        }

        public void setConditionTwo(List<ConditionTwoBean> conditionTwo) {
            this.conditionTwo = conditionTwo;
        }

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public int getRolecolor() {
            return rolecolor;
        }

        public void setRolecolor(int rolecolor) {
            this.rolecolor = rolecolor;
        }

        public List<IsopenBean> getIsopen() {
            return isopen;
        }

        public void setIsopen(List<IsopenBean> isopen) {
            this.isopen = isopen;
        }

        public List<NoopenBean> getNoopen() {
            return noopen;
        }

        public void setNoopen(List<NoopenBean> noopen) {
            this.noopen = noopen;
        }

        public List<ConditionOneBean> getConditionOne() {
            return conditionOne;
        }

        public void setConditionOne(List<ConditionOneBean> conditionOne) {
            this.conditionOne = conditionOne;
        }

        public List<WxBean> getWx() {
            return wx;
        }

        public void setWx(List<WxBean> wx) {
            this.wx = wx;
        }

        public static class UserinfoBean {
            /**
             * realname :
             * avatar :
             * rolename : 一年买手
             * roletype : 1
             * deadline : 2020-03-28 12:01:44
             */

            private String realname;
            private String avatar;
            private String rolename;
            private int roletype;
            private String deadline;

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getRolename() {
                return rolename;
            }

            public void setRolename(String rolename) {
                this.rolename = rolename;
            }

            public int getRoletype() {
                return roletype;
            }

            public void setRoletype(int roletype) {
                this.roletype = roletype;
            }

            public String getDeadline() {
                return deadline;
            }

            public void setDeadline(String deadline) {
                this.deadline = deadline;
            }
        }

        public static class IsopenBean {
            /**
             * icon :
             * title : 买手权限
             * text : 一年
             */

            private String icon;
            private String title;
            private String text;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
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
        }

        public static class NoopenBean {
            /**
             * icon :
             * title : 买手权限
             * text : 永久
             */

            private String icon;
            private String title;
            private String text;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
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
        }

        public static class ConditionOneBean {
            /**
             * finishOneLevelCount : 0
             * message : 邀请2个买手自动升级永久.
             * oneLevelCount : 2
             * schedule : =0/2(人)
             */

            private int finishOneLevelCount;
            @SerializedName("message")
            private String messageX;
            private int oneLevelCount;
            private String schedule;
            private int type;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getFinishOneLevelCount() {
                return finishOneLevelCount;
            }

            public void setFinishOneLevelCount(int finishOneLevelCount) {
                this.finishOneLevelCount = finishOneLevelCount;
            }

            public String getMessageX() {
                return messageX;
            }

            public void setMessageX(String messageX) {
                this.messageX = messageX;
            }

            public int getOneLevelCount() {
                return oneLevelCount;
            }

            public void setOneLevelCount(int oneLevelCount) {
                this.oneLevelCount = oneLevelCount;
            }

            public String getSchedule() {
                return schedule;
            }

            public void setSchedule(String schedule) {
                this.schedule = schedule;
            }
        }

        public static class ConditionTwoBean {
            /**
             * finishOneLevelCount : 0
             * message : 邀请2个买手自动升级永久.
             * oneLevelCount : 2
             * schedule : =0/2(人)
             */

            private int finishOneLevelCount;
            @SerializedName("message")
            private String messageX;
            private int oneLevelCount;
            private String schedule;
            private int type;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getFinishOneLevelCount() {
                return finishOneLevelCount;
            }

            public void setFinishOneLevelCount(int finishOneLevelCount) {
                this.finishOneLevelCount = finishOneLevelCount;
            }

            public String getMessageX() {
                return messageX;
            }

            public void setMessageX(String messageX) {
                this.messageX = messageX;
            }

            public int getOneLevelCount() {
                return oneLevelCount;
            }

            public void setOneLevelCount(int oneLevelCount) {
                this.oneLevelCount = oneLevelCount;
            }

            public String getSchedule() {
                return schedule;
            }

            public void setSchedule(String schedule) {
                this.schedule = schedule;
            }
        }

        public static class WxBean {
            /**
             * title : 专属大班长
             * text : 联系班长进入买手专属培训群
             * avatar :
             * realname : 朱潇洒
             * wx : zxs645999223
             * wxqrcode :
             */

            private String title;
            private String text;
            private String avatar;
            private String realname;
            private String wx;
            private String wxqrcode;
            private String studentid;
            private String rolename;

            public String getRolename() {
                return rolename;
            }

            public void setRolename(String rolename) {
                this.rolename = rolename;
            }

            public String getStudentid() {
                return studentid;
            }

            public void setStudentid(String studentid) {
                this.studentid = studentid;
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

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getWx() {
                return wx;
            }

            public void setWx(String wx) {
                this.wx = wx;
            }

            public String getWxqrcode() {
                return wxqrcode;
            }

            public void setWxqrcode(String wxqrcode) {
                this.wxqrcode = wxqrcode;
            }
        }

        public static class ArticleBean {
            private String title;
            private String url;
            private String images;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }
        }
    }
}
