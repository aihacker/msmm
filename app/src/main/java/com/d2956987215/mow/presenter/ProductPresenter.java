package com.d2956987215.mow.presenter;

import com.d2956987215.mow.DESUtils.JiaMiUtils;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.product.ProductFragment;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.util.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.d2956987215.mow.DESUtils.JiaMiUtils;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.product.ProductFragment;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.HomeHotResponse;
import com.d2956987215.mow.util.User;

/**
 * Created by lq on 2018/3/15.
 */

public class ProductPresenter {
    private BaseActivity activity;
    private ProductFragment fragment;
    private Date date;

    public ProductPresenter(BaseActivity activity) {
        this.activity = activity;
    }

    public ProductPresenter(ProductFragment fragment) {
        this.fragment = fragment;
    }


    public void hotProduct(int page){
        Map<String, String> map = new HashMap<>();
        Date  date = new Date();
        long time = date.getTime() / 1000;
        String key = JiaMiUtils.getkey(User.uid() + User.token(), String.valueOf(time));
        map.put("uid", String.valueOf(User.uid()));
        map.put("timestamp", String.valueOf(time));
        map.put("model", "periphery");
        map.put("sign", key);
        map.put("page", page+"");
        map.put("token", User.token());
        new Request<HomeHotResponse>().request(RxJavaUtil.xApi().homeHotProduct(map), "热门商品", fragment.getActivity(), false, new Result<HomeHotResponse>() {
            @Override
            public void get(HomeHotResponse response) {
                fragment.show(response.getRetval());
            }
        });
    }



}