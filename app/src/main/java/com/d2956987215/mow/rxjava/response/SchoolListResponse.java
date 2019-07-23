package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class SchoolListResponse extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * cate_id : 3
         * title : 中级
         * pic : https://images.maishoumm.com/images/2/2019/05/A2LVZQUO13M9E297AEMF3PFMEUPP9V.jpg
         * created_at : 2019-05-05 15:14
         * detailUrl : https://images.maishoumm.com/
         */

        private String id;
        private int cate_id;
        private String title;
        private String pic;
        private int shareNum;
        private String created_at;
        private String detailUrl;

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
        }
    }
}
