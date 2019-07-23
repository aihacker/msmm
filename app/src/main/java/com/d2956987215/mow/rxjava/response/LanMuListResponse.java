package com.d2956987215.mow.rxjava.response;


import java.util.List;

public class LanMuListResponse extends BaseResponse {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 44
         * goodstype : 1
         * article_tbgoods : []
         * article_content : <p>买手圈淘宝优惠券买手圈淘宝优惠券</p><p>买手圈淘宝优惠券</p><p>买手圈淘宝优惠券</p><p>买手圈淘宝优惠券</p>
         * article_date : 2018-09-17 16:25:33
         * article_readnum : 0
         * article_readnum_v : 8888
         * name : 买手妈妈
         * avatar :
         * article_goods : [{"id":521779851133,"itempic":"https://img.alicdn.com/bao/uploaded/i3/2452934656/TB2XrAwCv9TBuNjy0FcXXbeiFXa_!!2452934656-0-item_pic.jpg","itemtitle":"福钰润9-10mm白色强光淡水珍珠项链女款送妈妈婆婆礼物包邮送耳钉"},{"id":545549375936,"itempic":"https://img.alicdn.com/bao/uploaded/i4/2178765634/TB1ABhJxXkoBKNjSZFkXXb4tFXa_!!0-item_pic.jpg","itemtitle":"百年宝诚银手镯999纯银女款吉祥福字民族风老人千足镯子荷花银饰"},{"id":536337016284,"itempic":"https://img.alicdn.com/bao/uploaded/i4/2930697304/TB1VXXIjXGWBuNjy0FbXXb4sXXa_!!0-item_pic.jpg","itemtitle":"袋鼠妈妈 孕妇唇膏天然护手霜身体乳补水保湿孕妇秋冬护肤品套装"},{"id":572884724637,"itempic":"https://img.alicdn.com/bao/uploaded/i2/3941453412/TB2dJMsuRjTBKNjSZFuXXb0HFXa_!!3941453412-0-item_pic.jpg","itemtitle":"妈咪包 手提包 多功能大容量妈妈包母婴包大号女2018新款时尚旅行"},{"id":574512798656,"itempic":"https://img.alicdn.com/bao/uploaded/i2/843332261/O1CN011SZZxu2lxXUSTxe_!!843332261.jpg","itemtitle":"秋装新款女装妈妈显瘦打底小衫女式宽松荷叶边中长款收腰裙摆上衣"},{"id":575212980413,"itempic":"https://img.alicdn.com/bao/uploaded/i3/843332261/O1CN011SZZxEz22lRchDY_!!843332261.jpg","itemtitle":"秋装新款长袖t恤女式小衫大码宽松中长款打底衫中年人妈妈上衣服"},{"id":575483325124,"itempic":"https://img.alicdn.com/bao/uploaded/i4/2180670531/TB2PaR0dbrpK1RjSZTEXXcWAVXa_!!2180670531-0-item_pic.jpg","itemtitle":"妈妈毛衣中年女秋装新款长袖40-50岁上衣打底衫中老年女装针织衫"},{"id":574564562957,"itempic":"https://img.alicdn.com/bao/uploaded/i2/3327847817/TB2a1rwbwHqK1RjSZFPXXcwapXa_!!3327847817-0-item_pic.jpg","itemtitle":"新款春秋季厚款中老年女裤老年人高腰宽松妈妈加绒裤子奶奶休闲裤"},{"id":566610027262,"itempic":"https://img.alicdn.com/bao/uploaded/i4/3551151185/TB19FqIf49YBuNjy0FfXXXIsVXa_!!0-item_pic.jpg","itemtitle":"新款夏装妈妈装时尚立领T恤套装短袖40岁50阔腿裤中年女两件套"}]
         */

        private String id;
        private String goodstype;
        private String article_content;
        private long article_date;
        private String article_readnum;
        private String article_readnum_v;
        private String name;
        private String avatar;
        private List<String> images;
        private List<?> article_tbgoods;
        private List<ArticleGoodsBean> article_goods;
        private CodeBean ewm_info;
        private String good_comment;
        private String yongjin;
        private int isGetKl;//为0时候直接复制返回的评论;为1的时候请求CopyComment这个接口获取评论
        private int isGenerate;//是否已经生成海报
        private String ewm;

        public String getEwm() {
            return ewm;
        }

        public void setEwm(String ewm) {
            this.ewm = ewm;
        }

        public int getIsGenerate() {
            return isGenerate;
        }

        public void setIsGenerate(int isGenerate) {
            this.isGenerate = isGenerate;
        }

        public int getIsGetKl() {
            return isGetKl;
        }

        public void setIsGetKl(int isGetKl) {
            this.isGetKl = isGetKl;
        }

        public String getGood_comment() {
            return good_comment;
        }

        public void setGood_comment(String good_comment) {
            this.good_comment = good_comment;
        }

        public String getYongjin() {
            return yongjin;
        }

        public void setYongjin(String yongjin) {
            this.yongjin = yongjin;
        }

        public CodeBean getEwm_info() {
            return ewm_info;
        }

        public void setEwm_info(CodeBean ewm_info) {
            this.ewm_info = ewm_info;
        }

        public class CodeBean {

            private int x;
            private int y;
            private int w;
            private int h;
            private String url;


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

            public int getH() {
                return h;
            }

            public void setH(int h) {
                this.h = h;
            }

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoodstype() {
            return goodstype;
        }

        public void setGoodstype(String goodstype) {
            this.goodstype = goodstype;
        }

        public String getArticle_content() {
            return article_content;
        }

        public void setArticle_content(String article_content) {
            this.article_content = article_content;
        }

        public long getArticle_date() {
            return article_date;
        }

        public void setArticle_date(long article_date) {
            this.article_date = article_date;
        }

        public String getArticle_readnum() {
            return article_readnum;
        }

        public void setArticle_readnum(String article_readnum) {
            this.article_readnum = article_readnum;
        }

        public String getArticle_readnum_v() {
            return article_readnum_v;
        }

        public void setArticle_readnum_v(String article_readnum_v) {
            this.article_readnum_v = article_readnum_v;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public List<?> getArticle_tbgoods() {
            return article_tbgoods;
        }

        public void setArticle_tbgoods(List<?> article_tbgoods) {
            this.article_tbgoods = article_tbgoods;
        }

        public List<ArticleGoodsBean> getArticle_goods() {
            return article_goods;
        }

        public void setArticle_goods(List<ArticleGoodsBean> article_goods) {
            this.article_goods = article_goods;
        }

        public static class ArticleGoodsBean {
            /**
             * id : 521779851133
             * itempic : https://img.alicdn.com/bao/uploaded/i3/2452934656/TB2XrAwCv9TBuNjy0FcXXbeiFXa_!!2452934656-0-item_pic.jpg
             * itemtitle : 福钰润9-10mm白色强光淡水珍珠项链女款送妈妈婆婆礼物包邮送耳钉
             */

            private long id;
            private String itempic;
            private String itemtitle;
            private String zk_final_price;
            private String nick;
            private String shoptype;
            private String couponmoney;
            private String endprice;
            private String eurl;
            private String volume;
            private String quan_id;

            public String getQuan_id() {
                return quan_id;
            }

            public void setQuan_id(String quan_id) {
                this.quan_id = quan_id;
            }

            public String getVolume() {
                return volume;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public String getShoptype() {
                return shoptype;
            }

            public void setShoptype(String shoptype) {
                this.shoptype = shoptype;
            }

            public String getCouponmoney() {
                return couponmoney;
            }

            public void setCouponmoney(String couponmoney) {
                this.couponmoney = couponmoney;
            }

            public String getEndprice() {
                return endprice;
            }

            public void setEndprice(String endprice) {
                this.endprice = endprice;
            }

            public String getEurl() {
                return eurl;
            }

            public void setEurl(String eurl) {
                this.eurl = eurl;
            }

            public String getZk_final_price() {
                return zk_final_price;
            }

            public void setZk_final_price(String zk_final_price) {
                this.zk_final_price = zk_final_price;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getItempic() {
                return itempic;
            }

            public void setItempic(String itempic) {
                this.itempic = itempic;
            }

            public String getItemtitle() {
                return itemtitle;
            }

            public void setItemtitle(String itemtitle) {
                this.itemtitle = itemtitle;
            }
        }
    }
}
