package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by lq on 2018/3/15.
 */

public class SiTuListResponse extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 82
         "name": "天猫超市",
         "type": "9",
         "typeid": "http://s.click.taobao.com/t?spm=0.0.0.0.0_0.0.0.0&e=m%3D2%26s%3Dc9cEysGBoxocQipKwQzePCperVdZeJviK7Vc7tFgwiFRAdhuF14FMdo8wFfi5VIBFXyq8ODlIZ3iCpeCUkeJc8tA8Qks8BYBXdZXtfxfyx3IBFoSlOWI5E9%2FYFgWEAd0bnZHSCI%2BQaSx379%2FgWHD1oltDRe4JGBiKG2pNiQonqUkSblmzKJCg2DhH7G08hvtEvB9mAWGqHXKbk7%2BGct%2FC26sR2XtgLOEFe9gF6vkCfy9OHgbZvpf3smF%2F2r%2BMYD%2BomfkDJRs%2BhU%3D&pid=mm_32490747_43626016_341908488",
         "pic":
         * pic : http://msapi.maishoumm.com/uploads/gcatimg/1531134259091436.png
         */

        private String name;
        private String pic;
        private String id;
        private String type;
        private String activity_type;

        public String getActivity_type() {
            return activity_type;
        }

        public void setActivity_type(String activity_type) {
            this.activity_type = activity_type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }


}
