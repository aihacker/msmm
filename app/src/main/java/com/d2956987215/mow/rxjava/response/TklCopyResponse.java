package com.d2956987215.mow.rxjava.response;

public class TklCopyResponse extends BaseResponse {

    /**
     * data : {"Tkl":"($6OtZY2XXlpZ)"}
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
         * Tkl : ($6OtZY2XXlpZ)
         */

        private String Tkl;

        public String getTkl() {
            return Tkl;
        }

        public void setTkl(String Tkl) {
            this.Tkl = Tkl;
        }
    }
}
