package com.d2956987215.mow.presenter;

import com.d2956987215.mow.rxjava.Request;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.d2956987215.mow.DESUtils.JiaMiUtils;
import com.d2956987215.mow.activity.home.HomeFragment;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.BannerResponse;
import com.d2956987215.mow.rxjava.response.GetRedPacket;
import com.d2956987215.mow.rxjava.response.HomeHotResponse;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.User;

/**
 * Created by lq on 2018/3/15.
 */

public class HomeFragmentPresenter {
    private HomeFragment fragment;

    public HomeFragmentPresenter(HomeFragment fragment){
        this.fragment=fragment;
    }

    public void banner(){
        Map<String, String> map = new HashMap<>();
        Date  date = new Date();
        long time = date.getTime() / 1000;
        String key = JiaMiUtils.getkey(User.uid() + User.token(), String.valueOf(time));
        map.put("uid", String.valueOf(User.uid()));
        map.put("timestamp", String.valueOf(time));
        map.put("sign", key);
        map.put("token", User.token());
        new Request<BannerResponse>().request(RxJavaUtil.xApi().banner(map), "首页Banner和商品", fragment.getActivity(), false, new Result<BannerResponse>() {
            @Override
            public void get(BannerResponse response) {
                fragment.show(response.getRetval());
            }
        });
    }

    public void hotProduct(int page){
        Map<String, String> map = new HashMap<>();
        Date  date = new Date();
        long time = date.getTime() / 1000;
        String key = JiaMiUtils.getkey(User.uid() + User.token(), String.valueOf(time));
        map.put("uid", String.valueOf(User.uid()));
        map.put("timestamp", String.valueOf(time));
        map.put("model", "mall");
        map.put("sign", key);
        map.put("page", page+"");
        map.put("token", User.token());
        new Request<HomeHotResponse>().request(RxJavaUtil.xApi().homeHotProduct(map), "首页热门商品", fragment.getActivity(), false, new Result<HomeHotResponse>() {
            @Override
            public void get(HomeHotResponse response) {
                fragment.show(response.getRetval());
            }
        });
    }


    public void receiveCoupon(int useid,String couponId){
        new Request<GetRedPacket>().request(RxJavaUtil.xApi().receiveCoupon(useid,couponId), "领取红包", fragment.getActivity(), false, new Result<GetRedPacket>() {
            @Override
            public void get(GetRedPacket response) {
                ToastUtil.show(fragment.getActivity(),"领取成功,不能再次领取");
                fragment.show(response.getData());
            }
        });
    }
}
