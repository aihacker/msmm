package com.d2956987215.mow.rxjava.response;

import java.util.List;

/**
 * Created by lq on 2018/3/20.
 */

public class UpdateIdResponse extends BaseResponse {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * rolename : 买手
         * roletype : 1
         * rolepic : http://msapi.maishoumm.com/images/maishou.png
         */

        private String rolename;
        private String roletype;
        private String rolepic;

        public String getRolename() {
            return rolename;
        }

        public void setRolename(String rolename) {
            this.rolename = rolename;
        }

        public String getRoletype() {
            return roletype;
        }

        public void setRoletype(String roletype) {
            this.roletype = roletype;
        }

        public String getRolepic() {
            return rolepic;
        }

        public void setRolepic(String rolepic) {
            this.rolepic = rolepic;
        }
    }
}
