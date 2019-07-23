package com.d2956987215.mow.widgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2956987215.mow.R;


public class CheckItem extends RelativeLayout {

    private TextView tv_title;
    private TextView tv_content;
    private CheckBox cb_update;
    private String desc;


    private boolean IsCheck;
    private boolean showDesc;
    private Drawable icon;
    private ImageView iv_icon;

    private void init(Context context) {
        // TODO Auto-generated method stub
        View.inflate(context, R.layout.check_item, this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        cb_update = (CheckBox) findViewById(R.id.cb_update);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);

//		if(is_check())
//		{
//			set_ischeck();
//		}else{
//			set_uncheck();
//		}


    }

    public CheckItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // �����ؼ�
        init(context);
    }

    public CheckItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CheckItem);
        String title = typedArray.getString(R.styleable.CheckItem_title);
        desc = typedArray.getString(R.styleable.CheckItem_desc);
        //desc_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.mz.zhaiyong", "desc_off");
        IsCheck =typedArray.getBoolean(R.styleable.CheckItem_IsCheck,false);
        showDesc =typedArray.getBoolean(R.styleable.CheckItem_showDesc,false);
        icon=typedArray.getDrawable(R.styleable.CheckItem_Icon);
        iv_icon.setBackground(icon);
        tv_title.setText(title);
        tv_title.setTextColor(typedArray.getColor(R.styleable.CheckItem_titleColor, Color.BLACK));
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,typedArray.getInteger(R.styleable.CheckItem_titleSize, 14));
        tv_content.setTextColor(typedArray.getColor(R.styleable.CheckItem_descColor, Color.GRAY));
        tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP,typedArray.getInteger(R.styleable.CheckItem_descSize, 16));
        //setDesc(desc_off);
        setDesc(desc);
        if(showDesc){
            tv_content.setVisibility(GONE);
        }
        cb_update.setChecked(IsCheck);
    }

    private void setDesc(String text) {
        // TODO Auto-generated method stub
        tv_content.setText(text);
    }

    public CheckItem(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public void setCheck(boolean isCheck) {
        cb_update.setChecked(isCheck);
    }



    public boolean getIsCheck() {
        return cb_update.isChecked();
    }

}
