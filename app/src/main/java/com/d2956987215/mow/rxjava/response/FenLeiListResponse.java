package com.d2956987215.mow.rxjava.response;

import java.util.List;

public class FenLeiListResponse extends BaseResponse {


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
         * pid : 0
         * name : 食品
         * pic : http://msapi.maishoumm.com/uploads/gcatimg/1531141710234584.png
         * son : [{"id":20,"pid":"1","name":"中秋礼盒","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1536834091345173.jpg"},{"id":21,"pid":"1","name":"休闲零食","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1536832111592213.png"},{"id":22,"pid":"1","name":"精选肉食","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1536832161256319.png"},{"id":23,"pid":"1","name":"坚果蜜饯","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531135328979477.png"},{"id":24,"pid":"1","name":"糕点饼干","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1536831358398372.png"},{"id":25,"pid":"1","name":"水果蔬菜","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1536841627060110.png"},{"id":26,"pid":"1","name":"保健滋补","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1537239183903243.jpg"},{"id":27,"pid":"1","name":"河海生鲜","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1536841731440519.png"},{"id":28,"pid":"1","name":"茶酒冲饮","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1536831383880829.png"},{"id":29,"pid":"1","name":"粮油调味","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1536832087268321.png"},{"id":30,"pid":"1","name":"保健品","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211999978557.png"},{"id":31,"pid":"1","name":"营养健康","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211988683454.png"},{"id":32,"pid":"1","name":"营养成分","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211968445116.png"},{"id":33,"pid":"1","name":"中西药品","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211958386746.png"},{"id":34,"pid":"1","name":"石斛/枫斗","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211940924351.png"},{"id":35,"pid":"1","name":"冬虫夏草","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211923605418.png"},{"id":36,"pid":"1","name":"养生茶饮","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211910447579.png"},{"id":37,"pid":"1","name":"燕窝","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211897303996.png"},{"id":38,"pid":"1","name":"三七","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211861014682.png"},{"id":39,"pid":"1","name":"药食同源食品","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211847229025.png"},{"id":40,"pid":"1","name":"枸杞","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211820534935.png"},{"id":41,"pid":"1","name":"参类产品","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211811464570.png"},{"id":42,"pid":"1","name":"阿胶膏方","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211799618013.png"},{"id":43,"pid":"1","name":"保健茶饮","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211789290818.png"},{"id":44,"pid":"1","name":"茶饮料","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211780015345.png"},{"id":45,"pid":"1","name":"碳酸饮料","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211750762173.png"},{"id":46,"pid":"1","name":"奶茶","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211728119070.png"},{"id":47,"pid":"1","name":"果味饮料","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211714876958.png"},{"id":48,"pid":"1","name":"饮用水","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531211582136761.png"},{"id":49,"pid":"1","name":"牛奶酸奶","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209980737588.png"},{"id":50,"pid":"1","name":"成人奶粉","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209965017705.png"},{"id":51,"pid":"1","name":"麦片谷物冲饮","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209948666007.png"},{"id":52,"pid":"1","name":"调味品","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209896448692.png"},{"id":53,"pid":"1","name":"咖啡","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209939594003.png"},{"id":54,"pid":"1","name":"杂粮","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209875574291.png"},{"id":55,"pid":"1","name":"方便食品","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209860659448.png"},{"id":56,"pid":"1","name":"面","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209848243822.png"},{"id":57,"pid":"1","name":"米","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209831374593.png"},{"id":58,"pid":"1","name":"冷饮冻食","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209816262620.png"},{"id":59,"pid":"1","name":"南北干货","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531135366763869.png"},{"id":60,"pid":"1","name":"烘培原料","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531135296508408.png"},{"id":61,"pid":"1","name":"功能饮料","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531135265557974.png"},{"id":62,"pid":"1","name":"新鲜水果","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209781893350.png"},{"id":63,"pid":"1","name":"其他","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531212186670839.png"},{"id":64,"pid":"1","name":"水果","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209701656485.png"},{"id":65,"pid":"1","name":"糖果/巧克力","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209681179623.png"},{"id":66,"pid":"1","name":"蜜饯果干","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209565867940.png"},{"id":67,"pid":"1","name":"肉干/即食","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531209546923675.png"},{"id":68,"pid":"1","name":"茶叶","pic":"http://msapi.maishoumm.com/uploads/gcatimg/1531135213875254.png"}]
         */

        private int id;
        private String pid;
        private String name;
        private boolean selete;
        private String pic;
        private List<SonBean> son;

        public boolean isSelete() {
            return selete;
        }

        public void setSelete(boolean selete) {
            this.selete = selete;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
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

        public List<SonBean> getSon() {
            return son;
        }

        public void setSon(List<SonBean> son) {
            this.son = son;
        }

        public static class SonBean {
            /**
             * id : 20
             * pid : 1
             * name : 中秋礼盒
             * pic : http://msapi.maishoumm.com/uploads/gcatimg/1536834091345173.jpg
             */

            private int id;
            private String pid;
            private String name;
            private String pic;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
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
}
