package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by xf on 2018/3/31.
 */

public class XiTonfListResponse extends BaseResponse {


    /**
     * data : {"current_page":1,"data":[{"id":"6","title":"若提现失败，请把提现账号修改正确！！！","tdesc":"","tstime":"1538016669","detail":"<p>&nbsp;&nbsp;<\/p><p>若反馈\u201c支付宝填写错误\u201d，<\/p><p>请务必第一时间在：<\/p><p>个人中心--设置--提现账号，<\/p><p><strong><span style=\"color: rgb(255, 0, 0);\">准确<\/span><\/strong>填写支付宝账号和<strong><span style=\"color: rgb(255, 0, 0);\">对应实名<\/span><\/strong>。<\/p><p><br/><\/p><p>是支付宝<strong><span style=\"color: rgb(255, 0, 0);\">实名认证的姓名<\/span><\/strong>，<\/p><p>不是昵称或淘宝会员名！<\/p><p><br/><\/p><p>修改正确后，<br/><\/p><p>打款软件定期自动巡视，<\/p><p>若填写正确将自动打款过去，<\/p><p>若仍为错误，请再次检查修改！<\/p><p><br/><\/p>","created_at":"2018-09-27 10:51:09"},{"id":"18","title":"为啥说使用买手APP不会丢单？","tdesc":"","tstime":"1536903728","detail":"<p>&nbsp;<\/p><p>数据库已升级，<br/>目前正在同步所有历史订单！<\/p><p>&nbsp;<\/p><p>近几天数据库升级，<br/>订单显示延迟比较大，大家无需担心。<\/p><p>&nbsp;<\/p><p>使用买手APP并不会造成丢单，因为是阿里系统根据每个买手的PID进行统计的，并不是咱们APP负责统计。咱们APP只是调取阿里数据库，然后展示给大家。所以，会延迟展示，但不会丢单。<\/p><p>&nbsp;<\/p><p>建议大家耐心等待。若拿不准是否操作失误，把订单编号发给2号班长（maishou22）。<\/p>","created_at":"2018-09-14 13:42:08"},{"id":"14","title":"★★★大班长必须知道\u203c️","tdesc":"","tstime":"1534297994","detail":"<p>&nbsp;<br/><\/p><p>当名下买手升级大班长，和自己平级后，【她和你是同一个总代】。即使随后你也升了总代，并不能替代她原有总代。<\/p><p><br/><\/p><p>能理解的，跳过此段看下面建议；<\/p><p>如果理解不了，换位思考下：假设你是总代A，名下大班长B推荐了大班长C，C推荐了大班长D、E.......都属于总代A的大班长。突然有一天，你悉心培养的大班长B升级总代，直接替代你变成了CDE的总代，你成了光杆总代。换位思考：这样的制度，对你公平吗？&nbsp;&nbsp;<\/p><p>&nbsp;<br/><\/p><p>★★★最重要的建议：<br/>当你升级大班长后，<span style=\"color: rgb(255, 0, 0);\">不要停下脚步，必须快速发展<\/span>。赶在名下买手，升级大班长之前，自己提前升级总代！<\/p><p><br/><\/p><p><br/><\/p><p>&nbsp;<\/p>","created_at":"2018-08-15 09:53:14"},{"id":"12","title":"升大班长后，请提供微信二维码给5号班长。","tdesc":"","tstime":"1533276959","detail":"<p>&nbsp;<\/p><p>为了方便新注册用户，联系大班长。<\/p><p><br/><\/p><p>升级大班长后，请尽快联系5号班长（maishou55），提供<span style=\"color: rgb(255, 0, 0);\">学号和微信二维码<\/span>，帮您把微信添加到系统里面。<\/p><p><br/><\/p><p>要不然自己团队用户，将无法联系到您。<\/p>","created_at":"2018-08-03 14:15:59"}],"from":1,"last_page":1,"next_page_url":null,"path":"https://msapi.maishoumm.com/api/v1/SysMessage","per_page":10,"prev_page_url":null,"to":4,"total":4}
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
         * data : [{"id":"6","title":"若提现失败，请把提现账号修改正确！！！","tdesc":"","tstime":"1538016669","detail":"<p>&nbsp;&nbsp;<\/p><p>若反馈\u201c支付宝填写错误\u201d，<\/p><p>请务必第一时间在：<\/p><p>个人中心--设置--提现账号，<\/p><p><strong><span style=\"color: rgb(255, 0, 0);\">准确<\/span><\/strong>填写支付宝账号和<strong><span style=\"color: rgb(255, 0, 0);\">对应实名<\/span><\/strong>。<\/p><p><br/><\/p><p>是支付宝<strong><span style=\"color: rgb(255, 0, 0);\">实名认证的姓名<\/span><\/strong>，<\/p><p>不是昵称或淘宝会员名！<\/p><p><br/><\/p><p>修改正确后，<br/><\/p><p>打款软件定期自动巡视，<\/p><p>若填写正确将自动打款过去，<\/p><p>若仍为错误，请再次检查修改！<\/p><p><br/><\/p>","created_at":"2018-09-27 10:51:09"},{"id":"18","title":"为啥说使用买手APP不会丢单？","tdesc":"","tstime":"1536903728","detail":"<p>&nbsp;<\/p><p>数据库已升级，<br/>目前正在同步所有历史订单！<\/p><p>&nbsp;<\/p><p>近几天数据库升级，<br/>订单显示延迟比较大，大家无需担心。<\/p><p>&nbsp;<\/p><p>使用买手APP并不会造成丢单，因为是阿里系统根据每个买手的PID进行统计的，并不是咱们APP负责统计。咱们APP只是调取阿里数据库，然后展示给大家。所以，会延迟展示，但不会丢单。<\/p><p>&nbsp;<\/p><p>建议大家耐心等待。若拿不准是否操作失误，把订单编号发给2号班长（maishou22）。<\/p>","created_at":"2018-09-14 13:42:08"},{"id":"14","title":"★★★大班长必须知道\u203c️","tdesc":"","tstime":"1534297994","detail":"<p>&nbsp;<br/><\/p><p>当名下买手升级大班长，和自己平级后，【她和你是同一个总代】。即使随后你也升了总代，并不能替代她原有总代。<\/p><p><br/><\/p><p>能理解的，跳过此段看下面建议；<\/p><p>如果理解不了，换位思考下：假设你是总代A，名下大班长B推荐了大班长C，C推荐了大班长D、E.......都属于总代A的大班长。突然有一天，你悉心培养的大班长B升级总代，直接替代你变成了CDE的总代，你成了光杆总代。换位思考：这样的制度，对你公平吗？&nbsp;&nbsp;<\/p><p>&nbsp;<br/><\/p><p>★★★最重要的建议：<br/>当你升级大班长后，<span style=\"color: rgb(255, 0, 0);\">不要停下脚步，必须快速发展<\/span>。赶在名下买手，升级大班长之前，自己提前升级总代！<\/p><p><br/><\/p><p><br/><\/p><p>&nbsp;<\/p>","created_at":"2018-08-15 09:53:14"},{"id":"12","title":"升大班长后，请提供微信二维码给5号班长。","tdesc":"","tstime":"1533276959","detail":"<p>&nbsp;<\/p><p>为了方便新注册用户，联系大班长。<\/p><p><br/><\/p><p>升级大班长后，请尽快联系5号班长（maishou55），提供<span style=\"color: rgb(255, 0, 0);\">学号和微信二维码<\/span>，帮您把微信添加到系统里面。<\/p><p><br/><\/p><p>要不然自己团队用户，将无法联系到您。<\/p>","created_at":"2018-08-03 14:15:59"}]
         * from : 1
         * last_page : 1
         * next_page_url : null
         * path : https://msapi.maishoumm.com/api/v1/SysMessage
         * per_page : 10
         * prev_page_url : null
         * to : 4
         * total : 4
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
             * id : 6
             * title : 若提现失败，请把提现账号修改正确！！！
             * tdesc :
             * tstime : 1538016669
             * detail : <p>&nbsp;&nbsp;</p><p>若反馈“支付宝填写错误”，</p><p>请务必第一时间在：</p><p>个人中心--设置--提现账号，</p><p><strong><span style="color: rgb(255, 0, 0);">准确</span></strong>填写支付宝账号和<strong><span style="color: rgb(255, 0, 0);">对应实名</span></strong>。</p><p><br/></p><p>是支付宝<strong><span style="color: rgb(255, 0, 0);">实名认证的姓名</span></strong>，</p><p>不是昵称或淘宝会员名！</p><p><br/></p><p>修改正确后，<br/></p><p>打款软件定期自动巡视，</p><p>若填写正确将自动打款过去，</p><p>若仍为错误，请再次检查修改！</p><p><br/></p>
             * created_at : 2018-09-27 10:51:09
             */

            private String id;
            private String title;
            private String tdesc;
            private String tstime;
            private String detail;
            private String created_at;

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

            public String getTdesc() {
                return tdesc;
            }

            public void setTdesc(String tdesc) {
                this.tdesc = tdesc;
            }

            public String getTstime() {
                return tstime;
            }

            public void setTstime(String tstime) {
                this.tstime = tstime;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }
        }
    }
}
