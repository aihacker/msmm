package com.d2956987215.mow.rxjava.response;


import java.util.List;

public class MyZuJiResponse extends BaseResponse{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 312
         * user_id : 142
         * goods_id : 556337617633
         * type : 0
         * created_at : 2018-10-14 14:36:55
         * goods_title : 半高领毛衣女秋冬宽松韩版套头中长款打底衫2018秋装新款针织衫潮
         * goods_pic : https://img.alicdn.com/bao/uploaded/i4/2074868152/TB2nYpMuCMmBKNjSZTEXXasKpXa_!!2074868152-0-item_pic.jpg
         * goods_vit : 1
         * goods_price : 59.00
         */

        private int id;
        private String user_id;
        private String goods_id;
        private String quan_id;
        private String type;
        private String created_at;
        private String goods_title;
        private String goods_pic;
        private String goods_vit;
        private String goods_price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public String getQuan_id() {
            return quan_id;
        }

        public void setQuan_id(String quan_id) {
            this.quan_id = quan_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getGoods_title() {
            return goods_title;
        }

        public void setGoods_title(String goods_title) {
            this.goods_title = goods_title;
        }

        public String getGoods_pic() {
            return goods_pic;
        }

        public void setGoods_pic(String goods_pic) {
            this.goods_pic = goods_pic;
        }

        public String getGoods_vit() {
            return goods_vit;
        }

        public void setGoods_vit(String goods_vit) {
            this.goods_vit = goods_vit;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }
    }
}
