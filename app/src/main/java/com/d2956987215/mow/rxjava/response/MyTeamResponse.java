package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class MyTeamResponse extends BaseResponse {


    /**
     * data : {"total":3,"list":[{"id":192531,"studentId":"8475983","mtotal":"0.00","ww":"t_1497662341167_0323","avatarUrl":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=MHP-M8cyPCxYOHRHvG8WPC7eXHReMCI0M8xGv8PeMmIT&width=160&height=160&type=sns","avatar1":null,"avatar":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=MHP-M8cyPCxYOHRHvG8WPC7eXHReMCI0M8xGv8PeMmIT&width=160&height=160&type=sns","createtime":"2018-09-06 13:59","realname":"t_1497662341167_0323","agenttime":"1536388950","prealname":"美妞艺涵2015","pww":"美妞艺涵2015","pagentid":"92561","pnickname":"美妞艺涵2015","nickname":"t_1497662341167_0323","ji":5,"avatars":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=MHP-M8cyPCxYOHRHvG8WPC7eXHReMCI0M8xGv8PeMmIT&width=160&height=160&type=sns","pprealname":"胡珍珍","deadline":{"id":177230,"deadline":"2019-09-08 14:42","is_jq":0,"is_kt":"1","roletype":"1","kttext":"已开通"}},{"id":192463,"studentId":"8334362","mtotal":"0.00","ww":"我就是我42379847","avatarUrl":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=MHkyP8xYvm8LOFc0MmPHMkcSOFQbO88WOHg0PkRhMGNT&width=160&height=160&type=sns","avatar1":null,"avatar":"http://www.maishoumm.com/attachment/ewei_shopv2/user/192463/192463.png","createtime":"2018-09-06 13:36","realname":"我就是我42379847","agenttime":"1536212389","prealname":"美妞艺涵2015","pww":"美妞艺涵2015","pagentid":"92561","pnickname":"美妞艺涵2015","nickname":"我就是我42379847","ji":5,"avatars":"http://www.maishoumm.com/attachment/ewei_shopv2/user/192463/192463.png","pprealname":"胡珍珍","deadline":{"id":172573,"deadline":"2019-09-07 13:39","is_jq":0,"is_kt":"1","roletype":"1","kttext":"已开通"}},{"id":191215,"studentId":"8064146","mtotal":"0.00","ww":"杨乐15537505787","avatarUrl":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=PGMHOHP-M8ReMFgLXmhHPkc0MF84vGgbXHcWvGc4MFxT&width=160&height=160&type=sns","avatar1":null,"avatar":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=PGMHOHP-M8ReMFgLXmhHPkc0MF84vGgbXHcWvGc4MFxT&width=160&height=160&type=sns","createtime":"2018-09-05 21:49","realname":"杨乐15537505787","agenttime":"1536210055","prealname":"美妞艺涵2015","pww":"美妞艺涵2015","pagentid":"92561","pnickname":"美妞艺涵2015","nickname":"杨乐15537505787","ji":5,"avatars":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=PGMHOHP-M8ReMFgLXmhHPkc0MF84vGgbXHcWvGc4MFxT&width=160&height=160&type=sns","pprealname":"胡珍珍","deadline":{"id":172455,"deadline":"2019-09-06 13:00","is_jq":0,"is_kt":"1","roletype":"1","kttext":"已开通"}}]}
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
         * total : 3
         * list : [{"id":192531,"studentId":"8475983","mtotal":"0.00","ww":"t_1497662341167_0323","avatarUrl":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=MHP-M8cyPCxYOHRHvG8WPC7eXHReMCI0M8xGv8PeMmIT&width=160&height=160&type=sns","avatar1":null,"avatar":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=MHP-M8cyPCxYOHRHvG8WPC7eXHReMCI0M8xGv8PeMmIT&width=160&height=160&type=sns","createtime":"2018-09-06 13:59","realname":"t_1497662341167_0323","agenttime":"1536388950","prealname":"美妞艺涵2015","pww":"美妞艺涵2015","pagentid":"92561","pnickname":"美妞艺涵2015","nickname":"t_1497662341167_0323","ji":5,"avatars":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=MHP-M8cyPCxYOHRHvG8WPC7eXHReMCI0M8xGv8PeMmIT&width=160&height=160&type=sns","pprealname":"胡珍珍","deadline":{"id":177230,"deadline":"2019-09-08 14:42","is_jq":0,"is_kt":"1","roletype":"1","kttext":"已开通"}},{"id":192463,"studentId":"8334362","mtotal":"0.00","ww":"我就是我42379847","avatarUrl":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=MHkyP8xYvm8LOFc0MmPHMkcSOFQbO88WOHg0PkRhMGNT&width=160&height=160&type=sns","avatar1":null,"avatar":"http://www.maishoumm.com/attachment/ewei_shopv2/user/192463/192463.png","createtime":"2018-09-06 13:36","realname":"我就是我42379847","agenttime":"1536212389","prealname":"美妞艺涵2015","pww":"美妞艺涵2015","pagentid":"92561","pnickname":"美妞艺涵2015","nickname":"我就是我42379847","ji":5,"avatars":"http://www.maishoumm.com/attachment/ewei_shopv2/user/192463/192463.png","pprealname":"胡珍珍","deadline":{"id":172573,"deadline":"2019-09-07 13:39","is_jq":0,"is_kt":"1","roletype":"1","kttext":"已开通"}},{"id":191215,"studentId":"8064146","mtotal":"0.00","ww":"杨乐15537505787","avatarUrl":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=PGMHOHP-M8ReMFgLXmhHPkc0MF84vGgbXHcWvGc4MFxT&width=160&height=160&type=sns","avatar1":null,"avatar":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=PGMHOHP-M8ReMFgLXmhHPkc0MF84vGgbXHcWvGc4MFxT&width=160&height=160&type=sns","createtime":"2018-09-05 21:49","realname":"杨乐15537505787","agenttime":"1536210055","prealname":"美妞艺涵2015","pww":"美妞艺涵2015","pagentid":"92561","pnickname":"美妞艺涵2015","nickname":"杨乐15537505787","ji":5,"avatars":"https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=PGMHOHP-M8ReMFgLXmhHPkc0MF84vGgbXHcWvGc4MFxT&width=160&height=160&type=sns","pprealname":"胡珍珍","deadline":{"id":172455,"deadline":"2019-09-06 13:00","is_jq":0,"is_kt":"1","roletype":"1","kttext":"已开通"}}]
         */

        private int total;
        private String title;
        private List<ListBean> list;
        private List<String> nextlist;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getNextlist() {
            return nextlist;
        }

        public void setNextlist(List<String> nextlist) {
            this.nextlist = nextlist;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 192531
             * studentId : 8475983
             * mtotal : 0.00
             * ww : t_1497662341167_0323
             * avatarUrl : https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=MHP-M8cyPCxYOHRHvG8WPC7eXHReMCI0M8xGv8PeMmIT&width=160&height=160&type=sns
             * avatar1 : null
             * avatar : https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=MHP-M8cyPCxYOHRHvG8WPC7eXHReMCI0M8xGv8PeMmIT&width=160&height=160&type=sns
             * createtime : 2018-09-06 13:59
             * realname : t_1497662341167_0323
             * agenttime : 1536388950
             * prealname : 美妞艺涵2015
             * pww : 美妞艺涵2015
             * pagentid : 92561
             * pnickname : 美妞艺涵2015
             * nickname : t_1497662341167_0323
             * ji : 5
             * avatars : https://wwc.alicdn.com/avatar/getAvatar.do?userIdStr=MHP-M8cyPCxYOHRHvG8WPC7eXHReMCI0M8xGv8PeMmIT&width=160&height=160&type=sns
             * pprealname : 胡珍珍
             * deadline : {"id":177230,"deadline":"2019-09-08 14:42","is_jq":0,"is_kt":"1","roletype":"1","kttext":"已开通"}
             */

            private int id;
            private String studentId;
            private String mtotal;
            private String ww;
            private String avatarUrl;
            private Object avatar1;
            private String avatar;
            private String createtime;
            private String realname;
            private String agenttime;
            private String prealname;
            private String pww;
            private String pagentid;
            private String pnickname;
            private String nickname;

            private String avatars;
            private String pprealname;
            private DeadlineBean deadline;
            private String dbz;
            private String teamnum;
            private String tjr;
            private String ji;
            private String allxgyg;
            private String mobile;
            private String mobiles;
            private String isagent;

            public String getAllxgyg() {
                return allxgyg;
            }

            public void setAllxgyg(String allxgyg) {
                this.allxgyg = allxgyg;
            }

            public String getIsagent() {
                return isagent;
            }

            public void setIsagent(String isagent) {
                this.isagent = isagent;
            }

            public String getMobiles() {
                return mobiles;
            }

            public void setMobiles(String mobiles) {
                this.mobiles = mobiles;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getJi() {
                return ji;
            }

            public void setJi(String ji) {
                this.ji = ji;
            }

            public String getTjr() {
                return tjr;
            }

            public void setTjr(String tjr) {
                this.tjr = tjr;
            }

            public String getTeamnum() {
                return teamnum;
            }

            public void setTeamnum(String teamnum) {
                this.teamnum = teamnum;
            }

            public String getDbz() {
                return dbz;
            }

            public void setDbz(String dbz) {
                this.dbz = dbz;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getStudentId() {
                return studentId;
            }

            public void setStudentId(String studentId) {
                this.studentId = studentId;
            }

            public String getMtotal() {
                return mtotal;
            }

            public void setMtotal(String mtotal) {
                this.mtotal = mtotal;
            }

            public String getWw() {
                return ww;
            }

            public void setWw(String ww) {
                this.ww = ww;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public Object getAvatar1() {
                return avatar1;
            }

            public void setAvatar1(Object avatar1) {
                this.avatar1 = avatar1;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getAgenttime() {
                return agenttime;
            }

            public void setAgenttime(String agenttime) {
                this.agenttime = agenttime;
            }

            public String getPrealname() {
                return prealname;
            }

            public void setPrealname(String prealname) {
                this.prealname = prealname;
            }

            public String getPww() {
                return pww;
            }

            public void setPww(String pww) {
                this.pww = pww;
            }

            public String getPagentid() {
                return pagentid;
            }

            public void setPagentid(String pagentid) {
                this.pagentid = pagentid;
            }

            public String getPnickname() {
                return pnickname;
            }

            public void setPnickname(String pnickname) {
                this.pnickname = pnickname;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }


            public String getAvatars() {
                return avatars;
            }

            public void setAvatars(String avatars) {
                this.avatars = avatars;
            }

            public String getPprealname() {
                return pprealname;
            }

            public void setPprealname(String pprealname) {
                this.pprealname = pprealname;
            }

            public DeadlineBean getDeadline() {
                return deadline;
            }

            public void setDeadline(DeadlineBean deadline) {
                this.deadline = deadline;
            }

            public static class DeadlineBean {
                /**
                 * id : 177230
                 * deadline : 2019-09-08 14:42
                 * is_jq : 0
                 * is_kt : 1
                 * roletype : 1
                 * kttext : 已开通
                 */

                private int id;
                private String deadline;
                private int is_jq;
                private String is_kt;
                private String roletype;
                private String kttext;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getDeadline() {
                    return deadline;
                }

                public void setDeadline(String deadline) {
                    this.deadline = deadline;
                }

                public int getIs_jq() {
                    return is_jq;
                }

                public void setIs_jq(int is_jq) {
                    this.is_jq = is_jq;
                }

                public String getIs_kt() {
                    return is_kt;
                }

                public void setIs_kt(String is_kt) {
                    this.is_kt = is_kt;
                }

                public String getRoletype() {
                    return roletype;
                }

                public void setRoletype(String roletype) {
                    this.roletype = roletype;
                }

                public String getKttext() {
                    return kttext;
                }

                public void setKttext(String kttext) {
                    this.kttext = kttext;
                }
            }
        }
    }
}
