package com.d2956987215.mow.widgets;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.MainActivity;
import com.d2956987215.mow.activity.mine.FeedBackActivity;
import com.d2956987215.mow.activity.product.SearchShopActivity;
import com.d2956987215.mow.widgets.adapters.BaseRecyclerAdapter;
import com.d2956987215.mow.widgets.adapters.BaseRecyclerViewHolder;

import java.util.List;

import static com.d2956987215.mow.R.id.tv_shoucang;
import static com.d2956987215.mow.R.id.tv_shouye;


/**
 * Created by lenovo on 2017/8/20.
 */

public class SpinerPopWindow4 extends PopupWindow {
    private final List<String> mData;
    private LayoutInflater inflater;
    private TextView tv_quanbu, tv_feimai, tv_banzhang;
    private Context context;
    CallBack callback;
    RecyclerView mRv;

    public SpinerPopWindow4(List<String> data, Context context, CallBack callback) {
        super(context);
        mData = data;
        this.context = context;
        this.callback = callback;
        inflater = LayoutInflater.from(context);
        init();

    }

    private void init() {
        View view = inflater.inflate(R.layout.spiner_item_layout4, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);

        mRv = view.findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(context));
        mRv.setAdapter(new QuickAdapter(mData));
//        tv_quanbu = view.findViewById(R.id.tv_quanbu);
//        tv_feimai = view.findViewById(R.id.tv_feimai);
//        tv_banzhang = view.findViewById(R.id.tv_banzhang);
//        tv_quanbu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//                callback.xuanze(null, tv_quanbu.getText().toString());
//            }
//        });
//
//        tv_feimai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callback.xuanze("1", tv_feimai.getText().toString());
//                dismiss();
//            }
//        });
//        tv_banzhang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callback.xuanze("2", tv_banzhang.getText().toString());
//                dismiss();
//            }
//        });


    }

    public class QuickAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public QuickAdapter( @Nullable List<String> data) {
            super(R.layout.item_spinner_pop_window, data);
        }



        @Override
        protected void convert(BaseViewHolder helper, final String item) {
            TextView tv = helper.getView(R.id.tv_name);
            tv.setText(item);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.xuanze(null,item);
                    dismiss();
                }
            });
        }
    }

    public interface CallBack {
        public void xuanze(String leixing, String name);
    }

}