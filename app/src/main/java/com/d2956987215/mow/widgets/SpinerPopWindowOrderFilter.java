package com.d2956987215.mow.widgets;

import android.content.Context;
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
import com.d2956987215.mow.rxjava.response.MyOrderList;

import java.util.List;


/**
 * Created by lenovo on 2017/8/20.
 */

public class SpinerPopWindowOrderFilter extends PopupWindow {
    private final List<MyOrderList.DataBeanX.TypeBean> mData;
    private LayoutInflater inflater;
    private TextView tv_quanbu, tv_feimai, tv_banzhang;
    private Context context;
    CallBack callback;
    RecyclerView mRv;

    public SpinerPopWindowOrderFilter(List<MyOrderList.DataBeanX.TypeBean> data, Context context, CallBack callback) {
        super(context);
        mData = data;
        this.context = context;
        this.callback = callback;
        inflater = LayoutInflater.from(context);
        init();

    }

    private void init() {
        View view = inflater.inflate(R.layout.spiner_item_order_filter, null);
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

    public class QuickAdapter extends BaseQuickAdapter<MyOrderList.DataBeanX.TypeBean, BaseViewHolder> {
        public QuickAdapter( @Nullable List<MyOrderList.DataBeanX.TypeBean> data) {
            super(R.layout.item_spinner_pop_window, data);
        }



        @Override
        protected void convert(BaseViewHolder helper, final MyOrderList.DataBeanX.TypeBean item) {
            TextView tv = helper.getView(R.id.tv_name);
            tv.setText(item.getTitle());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.xuanze(null,item.getTitle(), item.getId());
                    dismiss();
                }
            });
        }
    }

    public interface CallBack {
        public void xuanze(String leixing, String name, String id);
    }

}