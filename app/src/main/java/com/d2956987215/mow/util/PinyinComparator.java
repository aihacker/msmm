package com.d2956987215.mow.util;



import com.d2956987215.mow.bean.AreaBean;
import com.d2956987215.mow.util.sortlist.SortModel;

import java.util.Comparator;

/**
 * @Description:拼音的比较器
 * @author http://blog.csdn.net/finddreams
 */ 
public class PinyinComparator implements Comparator<AreaBean.DataBean> {

	public int compare(AreaBean.DataBean o1, AreaBean.DataBean o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
