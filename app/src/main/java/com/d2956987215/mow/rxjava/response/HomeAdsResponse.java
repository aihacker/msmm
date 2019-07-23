package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * 作者：闫亚锋
 * 时间：2018/10/22 20:18
 * 说明：
 */
public class HomeAdsResponse extends BaseResponse{
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean{
        private String type;
        private String typeid;
        private String pic;
        private String quan_id;
        private String advname;

        public String getAdvname() {
            return advname;
        }

        public void setAdvname(String advname) {
            this.advname = advname;
        }

        public String getQuan_id() {
            return quan_id;
        }

        public void setQuan_id(String quan_id) {
            this.quan_id = quan_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
