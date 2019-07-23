package com.d2956987215.mow.bean;

import com.d2956987215.mow.rxjava.response.BaseResponse;

import java.util.List;

public class AreaBean extends BaseResponse {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * country : 澳大利亚
         * areaCode : 61
         * phoneLength : 10
         * prefix : A
         */

        private String country;
        private String areaCode;
        private int phoneLength;
        private String prefix;
        private String sortLetters;  //显示数据拼音的首字母

        public String getSortLetters() {
            return sortLetters;
        }

        public void setSortLetters(String sortLetters) {
            this.sortLetters = sortLetters;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public int getPhoneLength() {
            return phoneLength;
        }

        public void setPhoneLength(int phoneLength) {
            this.phoneLength = phoneLength;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }
}
