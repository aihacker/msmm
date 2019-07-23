package com.d2956987215.mow.presenter;

import com.d2956987215.mow.DESUtils.JiaMiUtils;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.eventbus.StopRefreshLoadMoreAnim;
import com.d2956987215.mow.rxjava.Request;
import com.d2956987215.mow.rxjava.Result;
import com.d2956987215.mow.rxjava.RxJavaUtil;
import com.d2956987215.mow.rxjava.response.ErJiListResponse;
import com.d2956987215.mow.rxjava.response.HotKetResponse;
import com.d2956987215.mow.util.User;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

//import rx.Observable;


/**
 * Created by lq on 2018/3/15.
 */

public class FenLeiPresenter {
    private BaseActivity fragment;
    private Date date;

    public FenLeiPresenter(BaseActivity fragment) {
        this.fragment = fragment;
    }


    public void search(int p, String content, String type, String order, String sort, String hascoupon) {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            date = new Date();
            long time = date.getTime() / 1000;
            String key = JiaMiUtils.getkey(User.uid() + User.token(), String.valueOf(time));
            map.put("user_id", String.valueOf(User.uid()));
            map.put("timestamp", String.valueOf(time));
            map.put("type", type);
        }

        map.put("keyword", content);
        map.put("hascoupon", hascoupon);
        map.put("page", p + "");
        map.put("order", order);
        map.put("sort", sort);
        if (User.uid() > 0) {
            map.put("user_id", User.uid() + "");
        }
        Observable<ErJiListResponse> erjidetail =null;

        if("2".endsWith(type)){
            erjidetail=  RxJavaUtil.xApi().JdSearch(map);
        }else if("3".endsWith(type)){
            erjidetail=  RxJavaUtil.xApi().PddSearch(map);
        }else {
            erjidetail= RxJavaUtil.xApi().erjidetail(map);
        }

        new Request<ErJiListResponse>().request(erjidetail, "搜索宝贝列表", fragment, false, new Result<ErJiListResponse>() {
            @Override
            public void get(ErJiListResponse response) {
                fragment.show(response.getData());
                EventBus.getDefault().post(new StopRefreshLoadMoreAnim());
        }

            @Override
            public void onBackErrorMessage(ErJiListResponse response) {
                super.onBackErrorMessage(response);
                EventBus.getDefault().post(new StopRefreshLoadMoreAnim());
            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
                EventBus.getDefault().post(new StopRefreshLoadMoreAnim());
            }
        });

    }

    /**
     * 获取热词
     */
    public void searchHotKey() {
        Map<String, String> map = new HashMap<>();
        if (User.uid() > 0) {
            date = new Date();
            long time = date.getTime() / 1000;
            String key = JiaMiUtils.getkey(User.uid() + User.token(), String.valueOf(time));
            map.put("user_id", String.valueOf(User.uid()));
            map.put("timestamp", String.valueOf(time));
        }
        new Request<HotKetResponse>().request(RxJavaUtil.xApi().hotKeySearch(map), "搜索宝贝列表", fragment, true, new Result<HotKetResponse>() {
            @Override
            public void get(HotKetResponse response) {
                fragment.show(response);
            }

            @Override
            public void onServerError(String errDesc) {
                super.onServerError(errDesc);
            }

            @Override
            public void onBackErrorMessage(HotKetResponse response) {
                super.onBackErrorMessage(response);
            }
        });
    }
//    https://suggest.taobao.com/sug?code=utf-8&q="+keyword+"&callback=jsonp

}
