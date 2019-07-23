package com.d2956987215.mow.widgets.datepicker;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.d2956987215.mow.R;

import java.util.Date;


/**
 * 弹出PopupWindow式日期选择框
 * 
 * @author gulin
 * @version 1.0
 * @created 2014-03-10
 */
public class PopWheelDatepicker {
	private PopupWindow popupWindow;
	private TextView ok;
	private View cancl_button;
	private  TextView tv_name;
	private WheelDatepicker wheelMain;
	private GetDateListener  getDateListener;
	public PopWheelDatepicker(Activity context, Date date, String pattern) {
		View view = LayoutInflater.from(context).inflate(R.layout.pop_wheel_date_picker, null);

		ok = (TextView) view.findViewById(R.id.affirm_button);

		cancl_button=view.findViewById(R.id.cancl_button);
		cancl_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		wheelMain = new WheelDatepicker(context, view.findViewById(R.id.wheel_layout), date, pattern);

		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	public void setGetDateListener(GetDateListener getDateListener) {
		this.getDateListener = getDateListener;
	}

	/**
	 * 弹出PopupWindow式日期选择框，startYear和endYear为0的话则使用默认值
	 *
	 * @param context
	 * @param date
	 * @param pattern
	 * @param startYear
	 *            开始年份
	 * @param endYear
	 *            结束年份
	 */
	public PopWheelDatepicker(Activity context, Date date, String pattern, int startYear, int endYear,String name) {
		try {
			View view = LayoutInflater.from(context).inflate(R.layout.pop_wheel_date_picker, null);
 			ok = (TextView) view.findViewById(R.id.affirm_button);
			tv_name= (TextView) view.findViewById(R.id.tv_name);
			tv_name.setText(name);
			ok.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
 					popupWindow.dismiss();
					getDateListener.getdate(wheelMain.getTime());
				}
			});
		    	view.findViewById(R.id.ll_beijing).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});

			cancl_button=view.findViewById(R.id.cancl_button);
			cancl_button.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
			wheelMain = new WheelDatepicker(context, view.findViewById(R.id.wheel_layout), date, pattern, startYear, endYear);

			popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setFocusable(true);
			// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	// 底部 弹出 pop菜单
	public void showAtLocation(View parent) {
		// 保证尺寸是根据屏幕像素密度来的
		// popupWindow.showAsDropDown(parent, 10, context.getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));
		// popupWindow.showAsDropDown(parent, 0, 0);
     	popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		//		popupWindow.showAsDropDown(parent);
		// 使其聚集
		popupWindow.setFocusable(true);
		// 动画效果
		//popupWindow.setAnimationStyle(R.style.popwin_anim_style);
		// 设置允许在外点击消失
	    popupWindow.setOutsideTouchable(true);
		// 刷新状态
		popupWindow.update();
	}

	public void setDismissListener(OnDismissListener onDismissListener) {
		popupWindow.setOnDismissListener(onDismissListener);
	}

	public void setOkListener(OnClickListener onClickListener) {
		ok.setOnClickListener(onClickListener);
	}

	// 隐藏菜单
	public void dismiss() {
		popupWindow.dismiss();
	}

	public WheelDatepicker getWheelMain() {
		return wheelMain;
	}

}
