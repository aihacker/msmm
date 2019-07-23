package com.d2956987215.mow.util.sortlist;

import java.util.List;

public class SortModel {
    private List<UserBean> area;

    public List<UserBean> getUser() {
        return area;
    }

    public void setUser(List<UserBean> user) {
        this.area = user;
    }

    public static class UserBean {
        /**
         * en : Angola
         * zh : 安哥拉
         * locale : AO
         * code : 244
         */

        private String en;
        private String zh;
        private String locale;
        private int code;
        private String sortLetters;  //显示数据拼音的首字母

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getZh() {
            return zh;
        }

        public void setZh(String zh) {
            this.zh = zh;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getSortLetters() {
            return sortLetters;
        }

        public void setSortLetters(String sortLetters) {
            this.sortLetters = sortLetters;
        }
    }
//	/**
//	 * en : Angola
//	 * zh : 安哥拉
//	 * locale : AO
//	 * code : 244
//	 */
//
//	private String en;
//	private String zh;
//	private String locale;
//	private int code;
//
//	public String getEn() {
//		return en;
//	}
//
//	public void setEn(String en) {
//		this.en = en;
//	}
//
//	public String getZh() {
//		return zh;
//	}
//
//	public void setZh(String zh) {
//		this.zh = zh;
//	}
//
//	public String getLocale() {
//		return locale;
//	}
//
//	public void setLocale(String locale) {
//		this.locale = locale;
//	}
//
//	public int getCode() {
//		return code;
//	}
//
//	public void setCode(int code) {
//		this.code = code;
//	}
//
////	private String name;   //显示的数�?

////
////	public String getName() {
////		return name;
////	}
////	public void setName(String name) {
////		this.name = name;
////	}


}
