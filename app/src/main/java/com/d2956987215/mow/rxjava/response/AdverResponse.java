package com.d2956987215.mow.rxjava.response;

/**
 * @author lq
 *         Created by lq on 2017/9/4.
 */

public class AdverResponse  extends BaseResponse{


    /**
     * data : {"title":"颐娉加绒雪地靴女休闲运动款2018冬季保暖百搭女靴子棉鞋加厚短靴"}
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
         * title : 颐娉加绒雪地靴女休闲运动款2018冬季保暖百搭女靴子棉鞋加厚短靴
         */

        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
