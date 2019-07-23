package com.d2956987215.mow.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSON;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class WebViewUtils {
    WebViewInterface webViewInterface;
    private Activity activity;

    public WebViewUtils(WebView webView, WebViewInterface webViewInterface, Activity activity) {
        this.webViewInterface = webViewInterface;
        this.activity = activity;
        init(webView);
        initWebClient(webView);
        initInterface(webView);
    }

    private void initInterface(WebView webView) {
        webView.addJavascriptInterface(new JavaScriptObject(), "JiuJiuMianDan");
    }

    public class JavaScriptObject {
        @JavascriptInterface

        public void gotoSecKilling(String message) {

            webViewInterface.JavaScriptObjectMethod(message);
        }

    }


    private static final String INJECTION_TOKEN = "**injection**";

    private void initWebClient(WebView webView) {

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = "";
                //android 7.0系统以上 已经摒弃了shouldOverrideUrlLoading(WebView view, String url)此方法
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    url = request.getUrl().toString();
                } else {
                    url = request.toString();
                }
                Log.d("zzz", url);
                String goodsId = Uri.parse(url).getQueryParameter("id");
                String item_id = Uri.parse(url).getQueryParameter("item_id");
                String itemId = Uri.parse(url).getQueryParameter("itemId");
                if (!TextUtils.isEmpty(goodsId) || !TextUtils.isEmpty(item_id) || !TextUtils.isEmpty(itemId)) {
                    boolean isExit = false;
                    String rulesStr = SP.getString("rules", "");
                    if (!TextUtils.isEmpty(rulesStr)) {
                        List<String> rulesList = JSON.parseArray(rulesStr, String.class);
                        if (rulesList != null && rulesList.size() > 0) {
                            for (int i = 0; i < rulesList.size(); i++) {
                                if (url.contains(rulesList.get(i))) {
                                    isExit = true;
                                }
                            }
                        }

                    }

                    if (isExit) {
                        if (!TextUtils.isEmpty(goodsId)) {
                            Intent intent = new Intent(activity, TaoBaoDetailActivity.class);
                            intent.putExtra("id", goodsId);
                            activity.startActivity(intent);
                        } else if (!TextUtils.isEmpty(item_id)) {
                            Intent intent = new Intent(activity, TaoBaoDetailActivity.class);
                            intent.putExtra("id", item_id);
                            activity.startActivity(intent);
                        } else if (!TextUtils.isEmpty(itemId)) {
                            Intent intent = new Intent(activity, TaoBaoDetailActivity.class);
                            intent.putExtra("id", itemId);
                            activity.startActivity(intent);
                        } else {
                            if (url.startsWith("https") || url.startsWith("http")) {
                                view.loadUrl(url);
                            } else {
                                return false;
                            }
                        }

                    } else {
                        if (url.startsWith("https") || url.startsWith("http")) {
                            view.loadUrl(url);
                        } else {
                            return false;
                        }

                    }

                } else

                {
                    if (url.startsWith("https") || url.startsWith("http")) {
                        view.loadUrl(url);
                    } else {
                        return false;
                    }
                }


                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                WebResourceResponse response = super.shouldInterceptRequest(view, url);
                if (url != null && url.contains(INJECTION_TOKEN)) {
                    String assetPath = url.substring(url.indexOf(INJECTION_TOKEN) + INJECTION_TOKEN.length(), url.length());
                    try {
                        response = new WebResourceResponse(
                                "application/javascript",
                                "UTF8",
                                activity.getAssets().open(assetPath)
                        );
                    } catch (IOException e) {
                        e.printStackTrace(); // Failed to load asset file
                    }
                }
                return response;
            }
        });
    }

    private void init(WebView mWebView) {

        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setGeolocationEnabled(true);
        String dir = APP.application.getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);
        String user_agent = webSettings.getUserAgentString();// 获取UserAgent
        webSettings.setUserAgentString(user_agent + "/JINBAO");// 设置UserAgent
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(APP.application.getCacheDir().getAbsolutePath());
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);

    }

    public interface WebViewInterface {
        public boolean check(WebView webView, String url);

        public void JavaScriptObjectMethod(String url);

    }


}
