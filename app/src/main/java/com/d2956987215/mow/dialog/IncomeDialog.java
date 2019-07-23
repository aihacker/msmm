package com.d2956987215.mow.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.d2956987215.mow.R;

public class IncomeDialog extends Dialog implements View.OnClickListener {
    String title;
    String name;

    public IncomeDialog(@NonNull Context context, String title,String  name) {
        super(context);
        this.title = title;
        this.name=name;
        init();
    }

    private void init() {
        setContentView(R.layout.ml_dialog_income);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tv_content = findViewById(R.id.tv_content);

        if (title.contains("预估计收入")) {
            title = title.replace("预估计收入", "");
        }
        tvTitle.setText(title);
        tv_content.setText(name);
        TextView tv_sure = findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
