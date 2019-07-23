package com.d2956987215.mow.jpush;

public class MsgBean {

    /**
     * n_extras : {"aid":"","quan_id":"","typeid":"2","url":""}
     * msg_id : 54043205681569606
     * rom_type : 2
     * n_content : 欢迎注册[买手妈妈]会员，免费使用买手妈妈购买和分享都可以获得高额返佣，VIP可获得更多专属尊贵权益。快开启省&赚钱模式吧！
     * n_title : 注册成功
     */

    private NExtrasBean n_extras;
    private long msg_id;
    private int rom_type;
    private String n_content;
    private String n_title;

    public NExtrasBean getN_extras() {
        return n_extras;
    }

    public void setN_extras(NExtrasBean n_extras) {
        this.n_extras = n_extras;
    }

    public long getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(long msg_id) {
        this.msg_id = msg_id;
    }

    public int getRom_type() {
        return rom_type;
    }

    public void setRom_type(int rom_type) {
        this.rom_type = rom_type;
    }

    public String getN_content() {
        return n_content;
    }

    public void setN_content(String n_content) {
        this.n_content = n_content;
    }

    public String getN_title() {
        return n_title;
    }

    public void setN_title(String n_title) {
        this.n_title = n_title;
    }

    public static class NExtrasBean {
        /**
         * aid :
         * quan_id :
         * typeid : 2
         * url :
         */

        private String aid;
        private String quan_id;
        private String typeid;
        private String url;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getQuan_id() {
            return quan_id;
        }

        public void setQuan_id(String quan_id) {
            this.quan_id = quan_id;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
