package com.d2956987215.mow.widgets.datepicker;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.d2956987215.mow.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class WheelDatepicker {

	private static final String TAG = WheelDatepicker.class.getSimpleName();

	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_mins;
	private int screenheight;
	private SimpleDateFormat sdf;
	private int START_YEAR = 1990, END_YEAR = 2100;

	private int year;
	private int month;
	private int day;
	private int h;
	private int m;

	private String formatPattern;

	private int showCount = 0;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	/**
	 * 日期时间滚轮选择控件
	 * 
	 * @param activity
	 * @param view
	 * @param date
	 * @param pattern
	 *            输出的时间日期格式如："yyyy-MM-dd HH:mm"
	 */
	public WheelDatepicker(Activity activity, View view, Date date, String pattern) {
		this(activity, view, date, pattern, 0, 0);
	}

	/**
	 * 日期时间滚轮选择控件
	 * 
	 * @param activity
	 * @param view
	 * @param date
	 * @param pattern
	 *            输出的时间日期格式如："yyyy-MM-dd HH:mm"
	 */
	public WheelDatepicker(Activity activity, View view, Date date, String pattern, int startYear, int endYear) {

		this.view = view;
		ScreenInfo screenInfo = new ScreenInfo(activity);
		this.screenheight = screenInfo.getHeight();

		sdf = new SimpleDateFormat(pattern, Locale.CHINA);

		Calendar calendar = Calendar.getInstance();
		if (date == null) {
			date = new Date();
		}
		calendar.setTime(date);
		this.year = calendar.get(Calendar.YEAR);
		this.month = calendar.get(Calendar.MONTH);
		this.day = calendar.get(Calendar.DAY_OF_MONTH);
		this.h = calendar.get(Calendar.HOUR_OF_DAY);
		this.m = calendar.get(Calendar.MINUTE);

		this.formatPattern = pattern;

		if (startYear != 0)
			this.START_YEAR = startYear;
		if (endYear != 0)
			this.END_YEAR = endYear;

		initDateTimePicker();

	}


	private void isShow() {
		if (!formatPattern.contains("yyyy")) {
			wv_year.setVisibility(View.GONE);
		} else {
			wv_year.setVisibility(View.VISIBLE);
			showCount++;
		}
		if (!formatPattern.contains("MM")) {
			wv_month.setVisibility(View.GONE);
		} else {
			wv_month.setVisibility(View.VISIBLE);
			showCount++;
		}
		if (!formatPattern.contains("dd")) {
			wv_day.setVisibility(View.GONE);
		} else {
			wv_day.setVisibility(View.VISIBLE);
			showCount++;
		}
		if (!formatPattern.contains("HH") && !formatPattern.contains("hh")) {
			wv_hours.setVisibility(View.GONE);
		} else {
			wv_hours.setVisibility(View.VISIBLE);
			showCount++;
		}
		if (!formatPattern.contains("mm")) {
			wv_mins.setVisibility(View.GONE);
		} else {
			wv_mins.setVisibility(View.VISIBLE);
			showCount++;
		}
	}

	/**
	 * 初始化日期时间选择器
	 * 

	 */
	private void initDateTimePicker() {
		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_mins = (WheelView) view.findViewById(R.id.min);

   		wv_month.setCyclic(true);
		wv_year.setCyclic(true);
		wv_day.setCyclic(true);

		isShow();

		// 年
		if (wv_year.getVisibility() != View.GONE) {
			wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
			// wv_year.setCyclic(true);// 可循环滚动
			wv_year.setLabel("年");// 添加文字
			wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据
		}

		// 月
		if (wv_month.getVisibility() != View.GONE) {
			wv_month.setAdapter(new NumericWheelAdapter(1, 12));
			// wv_month.setCyclic(true);
			wv_month.setLabel("月");
			wv_month.setCurrentItem(month);
		}

		// 日
		if (wv_day.getVisibility() != View.GONE) {
			// wv_day.setCyclic(true);
			// 判断大小月及是否闰年,用来确定"日"的数据
			if (list_big.contains(String.valueOf(month + 1))) {
				wv_day.setAdapter(new NumericWheelAdapter(1, 31));
			} else if (list_little.contains(String.valueOf(month + 1))) {
				wv_day.setAdapter(new NumericWheelAdapter(1, 30));
			} else {
				// 闰年
				if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
					wv_day.setAdapter(new NumericWheelAdapter(1, 29));
				else
					wv_day.setAdapter(new NumericWheelAdapter(1, 28));
			}
			wv_day.setLabel("日");
			wv_day.setCurrentItem(day - 1);
		}

		// 时
		if (wv_hours.getVisibility() != View.GONE) {
			wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
			// wv_hours.setCyclic(true);// 可循环滚动
			wv_hours.setLabel("时");// 添加文字
			wv_hours.setCurrentItem(h);
		}

		// 分
		if (wv_mins.getVisibility() != View.GONE) {
			wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
			// wv_mins.setCyclic(true);// 可循环滚动
			wv_mins.setLabel("分");// 添加文字
			wv_mins.setCurrentItem(m);
		}
		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					// 防止出现当由5月31日滚到4月时，出现4月31日的bug
					if (wv_day.getCurrentItem() + 1 == 31) {
						wv_day.setCurrentItem(29, true);
					}
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0) {
						// 防止出现当由3月31日滚到2月时，出现2月31日的bug
						if (wv_day.getCurrentItem() + 1 == 30 || wv_day.getCurrentItem() + 1 == 31) {
							wv_day.setCurrentItem(28, true);
						}
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					} else {
						// 防止出现当由3月31日滚到2月时，出现2月31日的bug
						if (wv_day.getCurrentItem() + 1 == 29 || wv_day.getCurrentItem() + 1 == 30 || wv_day.getCurrentItem() + 1 == 31) {
							wv_day.setCurrentItem(27, true);
						}
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
					}
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					// 防止出现当由5月31日滚到4月时，出现4月31日的bug
					if (wv_day.getCurrentItem() + 1 == 31) {
						wv_day.setCurrentItem(29, true);
					}
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year.getCurrentItem() + START_YEAR) % 100 != 0) || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {
						// 防止出现当由3月31日滚到2月时，出现2月31日的bug
						if (wv_day.getCurrentItem() + 1 == 30 || wv_day.getCurrentItem() + 1 == 31) {
							wv_day.setCurrentItem(28, true);
						}
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					} else {
						// 防止出现当由3月31日滚到2月时，出现2月31日的bug
						if (wv_day.getCurrentItem() + 1 == 29 || wv_day.getCurrentItem() + 1 == 30 || wv_day.getCurrentItem() + 1 == 31) {
							wv_day.setCurrentItem(27, true);
						}
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
					}
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);

		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 0;
		if (showCount > 3) {
			textSize = (screenheight / 100) * 2;
		} else {
			textSize = (screenheight / 100) *3;
		}
		wv_day.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;
		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;

	}

	/**
	 * 输出当前滚轮所表示的时间字符串
	 * 
	 * @return
	 */
	public String getTime() {
		StringBuffer sb = new StringBuffer();
		if (wv_year.getVisibility() != View.GONE) {
			sb.append((wv_year.getCurrentItem() + START_YEAR));
		} else {
			sb.append(year);
		}
		sb.append("-");
		if (wv_month.getVisibility() != View.GONE) {
			sb.append((wv_month.getCurrentItem() + 1));
		} else {
			sb.append(month);
		}
		sb.append("-");
		if (wv_day.getVisibility() != View.GONE) {
			sb.append((wv_day.getCurrentItem() + 1));
		} else {
			sb.append(day);
		}
		sb.append(" ");
		if (wv_hours.getVisibility() != View.GONE) {
			sb.append(wv_hours.getCurrentItem());
		} else {
			sb.append(h);
		}
		sb.append("-");
		if (wv_mins.getVisibility() != View.GONE) {
			sb.append(wv_mins.getCurrentItem());
		} else {
			sb.append(m);
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.CHINA);
		try {
			Date date = sf.parse(sb.toString());
			return sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			Log.e(TAG, "格式转换发生错误");
		}
		return null;
	}
}
