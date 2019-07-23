package com.d2956987215.mow.util;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.android.trade.page.AlibcShopPage;
import com.alibaba.baichuan.trade.biz.AlibcConstants;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TBKUtils {

    private final static String H5 = "H5";
    private final static String TAOBAO = "taobao";
    private final static String TMALL = "tmall";
    private final static String AUTO = "auto";

    public static void showDetailPageForItemId(Activity activity, String id, String type) {
        AlibcShowParams alibcShowParams;
        switch (type) {
            case H5:
                alibcShowParams = new AlibcShowParams(OpenType.H5, false);
                break;
            case TAOBAO:
                alibcShowParams = new AlibcShowParams(OpenType.Native, false);
                alibcShowParams.setClientType("taobao_scheme");
                break;
            case TMALL:
                alibcShowParams = new AlibcShowParams(OpenType.Native, false);
                alibcShowParams.setClientType("tmall_scheme");
                break;
            case AUTO:
                alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                break;
            default:
                alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                break;
        }
        //通过ID访问
        AlibcBasePage alibcBasePage = new AlibcDetailPage(id);
        //通过URL访问
//        AlibcBasePage alibcBasePage = new AlibcPage(id);
        HashMap<String, String> exParams = new HashMap<>();
        // 固定写法
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");

        // 若非淘客taokeParams设置为null即可   mm_memberId_siteId_adzoneId
        // pid:广告位id
//        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams(Constant.TBK_PID, Constant.ADZONEID,Constant.TBK_PID);
//        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams("", "","");
        // adzoneid 为mm_memberId_siteId_adzoneId最后一位
//        alibcTaokeParams.adzoneid = Constant.ADZONEID;
//        alibcTaokeParams.pid = Constant.TBK_PID;
//        alibcTaokeParams.subPid = Constant.TBK_PID;
//        alibcTaokeParams.extraParams = new HashMap<>();
//        alibcTaokeParams.extraParams.put("taokeAppkey", "24487210");


//        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
//            @Override
//            public void onTradeSuccess(TradeResult tradeResult) {
//
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });

//        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
//            @Override
//            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
//                ToastUtils.showShort(alibcTradeResult.toString());
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Toast.makeText(APPAplication.getContext(), "初始化失败,错误码=" + i + " / 错误消息=" + s, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public static void showDetailPageForUrl(Activity activity, String url, String type, String pid, String adzoneid) {
        AlibcShowParams alibcShowParams;
        switch (type) {
            case H5:
                alibcShowParams = new AlibcShowParams(OpenType.H5, false);
                break;
            case TAOBAO:
                alibcShowParams = new AlibcShowParams(OpenType.Native, false);
                alibcShowParams.setClientType("taobao_scheme");
                break;
            case TMALL:
                alibcShowParams = new AlibcShowParams(OpenType.Native, false);
                alibcShowParams.setClientType("tmall_scheme");
                break;
            case AUTO:
                alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                break;
            default:
                alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                break;
        }
        //通过ID访问
//        AlibcBasePage alibcBasePage = new AlibcDetailPage(id);
        //通过URL访问
        AlibcBasePage alibcBasePage = new AlibcPage(url);
        HashMap<String, String> exParams = new HashMap<>();
        // 固定写法
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");

        // 若非淘客taokeParams设置为null即可   mm_memberId_siteId_adzoneId
        // pid:广告位id
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams();
        // adzoneid 为mm_memberId_siteId_adzoneId最后一位
        alibcTaokeParams.adzoneid = adzoneid;
        alibcTaokeParams.pid = pid;
        alibcTaokeParams.subPid = pid;
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey", "24754832");


        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                ToastUtils.showShort(alibcTradeResult.toString());
            }

            @Override
            public void onFailure(int i, String s) {
//                Toast.makeText(APPAplication.getContext(), "初始化失败,错误码=" + i + " / 错误消息=" + s, Toast.LENGTH_SHORT).show();
                ToastUtils.showShort("初始化失败,错误码=" + i + " / 错误消息=" + s, Toast.LENGTH_SHORT);

            }
        });

//
//        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
//            @Override
//            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
//                ToastUtils.showShort(alibcTradeResult.toString());
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Toast.makeText(APPAplication.getContext(), "初始化失败,错误码=" + i + " / 错误消息=" + s, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public static void showDetailPageForUrl(Activity activity, String url, String type) {
        AlibcShowParams alibcShowParams;
        switch (type) {
            case H5:
                alibcShowParams = new AlibcShowParams(OpenType.H5, false);
                break;
            case TAOBAO:
                alibcShowParams = new AlibcShowParams(OpenType.Native, false);
                alibcShowParams.setClientType("taobao_scheme");
                break;
            case TMALL:
                alibcShowParams = new AlibcShowParams(OpenType.Native, false);
                alibcShowParams.setClientType("tmall_scheme");
                break;
            case AUTO:
                alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                break;
            default:
                alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                break;
        }
        //通过ID访问
//        AlibcBasePage alibcBasePage = new AlibcDetailPage(id);
        //通过URL访问
        AlibcBasePage alibcBasePage = new AlibcPage(url);
        HashMap<String, String> exParams = new HashMap<>();
        // 固定写法
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");

        // 若非淘客taokeParams设置为null即可   mm_memberId_siteId_adzoneId
        // pid:广告位id
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams();
        // adzoneid 为mm_memberId_siteId_adzoneId最后一位
//        alibcTaokeParams.adzoneid = adzoneid;
//        alibcTaokeParams.pid = pid;
//        alibcTaokeParams.subPid = pid;
//        alibcTaokeParams.extraParams = new HashMap<>();
//        alibcTaokeParams.extraParams.put("taokeAppkey", "24754832");


        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                ToastUtils.showShort(alibcTradeResult.toString());
            }

            @Override
            public void onFailure(int i, String s) {
//                Toast.makeText(APPAplication.getContext(), "初始化失败,错误码=" + i + " / 错误消息=" + s, Toast.LENGTH_SHORT).show();
                ToastUtils.showShort("初始化失败,错误码=" + i + " / 错误消息=" + s, Toast.LENGTH_SHORT);

            }
        });

//
//        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
//            @Override
//            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
//                ToastUtils.showShort(alibcTradeResult.toString());
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Toast.makeText(APPAplication.getContext(), "初始化失败,错误码=" + i + " / 错误消息=" + s, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public static void showShopDetailPageForUrl(Activity activity, String shopId, String type) {
        AlibcShowParams alibcShowParams;
        switch (type) {
            case H5:
                alibcShowParams = new AlibcShowParams(OpenType.H5, false);
                break;
            case TAOBAO:
                alibcShowParams = new AlibcShowParams(OpenType.Native, false);
                alibcShowParams.setClientType("taobao_scheme");
                break;
            case TMALL:
                alibcShowParams = new AlibcShowParams(OpenType.Native, false);
                alibcShowParams.setClientType("tmall_scheme");
                break;
            case AUTO:
                alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                break;
            default:
                alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
                break;
        }
        //通过ID访问
//        AlibcBasePage alibcBasePage = new AlibcDetailPage(id);
        //实例化店铺打开page
        AlibcBasePage shopPage = new AlibcShopPage(shopId);
        HashMap<String, String> exParams = new HashMap<>();
        // 固定写法
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");

        // 若非淘客taokeParams设置为null即可   mm_memberId_siteId_adzoneId
        // pid:广告位id
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams();
        // adzoneid 为mm_memberId_siteId_adzoneId最后一位
//        alibcTaokeParams.adzoneid = adzoneid;
//        alibcTaokeParams.pid = pid;
//        alibcTaokeParams.subPid = pid;
//        alibcTaokeParams.extraParams = new HashMap<>();
//        alibcTaokeParams.extraParams.put("taokeAppkey", "24754832");


        AlibcTrade.show(activity, shopPage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                ToastUtils.showShort(alibcTradeResult.toString());
            }

            @Override
            public void onFailure(int i, String s) {
//                Toast.makeText(APPAplication.getContext(), "初始化失败,错误码=" + i + " / 错误消息=" + s, Toast.LENGTH_SHORT).show();
                ToastUtils.showShort("初始化失败,错误码=" + i + " / 错误消息=" + s, Toast.LENGTH_SHORT);

            }
        });

//
//        AlibcTrade.show(activity, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new AlibcTradeCallback() {
//            @Override
//            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
//                ToastUtils.showShort(alibcTradeResult.toString());
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Toast.makeText(APPAplication.getContext(), "初始化失败,错误码=" + i + " / 错误消息=" + s, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     * 跳淘宝详情
     */
    public static void showTaobaoDetailForItemId(Activity activity, String id) {
        if (isAvilible(activity, "com.taobao.taobao")) {
            showDetailPageForItemId(activity, id, TAOBAO);
        } else {
            showDetailPageForItemId(activity, id, AUTO);
        }
    }

    /**
     * 跳天猫详情
     */
    public static void showTmallDetail(Activity activity, String id) {
        if (isAvilible(activity, "com.tmall.wireless")) {
            showDetailPageForItemId(activity, id, TMALL);
        } else {
            showDetailPageForItemId(activity, id, AUTO);
        }
    }
 /**
     * 跳淘宝详情 url
     */
    public static void showTaobaoDetailForUrl(Activity activity, String url, String pid, String ad) {
        if (isAvilible(activity, "com.taobao.taobao")) {
            showDetailPageForItemId(activity, url, TAOBAO);
        } else {
            showDetailPageForItemId(activity, url, AUTO);
        }
    }

    /**
     * 跳天猫详情url
     */
    public static void showTmallDetailForUrl(Activity activity, String url) {
        if (isAvilible(activity, "com.tmall.wireless")) {
            showDetailPageForItemId(activity, url, TMALL);
        } else {
            showDetailPageForItemId(activity, url, AUTO);
        }
    }

    private static boolean isAvilible(Activity context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();//获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名
//从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }


}
