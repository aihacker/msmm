package com.d2956987215.mow.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.d2956987215.mow.R;
import com.d2956987215.mow.activity.APP;
import com.d2956987215.mow.activity.BaseActivity;
import com.d2956987215.mow.activity.home.TaoBaoDetailActivity;
import com.d2956987215.mow.util.SP;
import com.d2956987215.mow.util.ToastUtil;
import com.d2956987215.mow.util.WebViewUtils;
import com.d2956987215.mow.widgets.SearchTicketDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class TaoBaoCollectionActivity extends BaseActivity implements WebViewUtils.WebViewInterface {

    @BindView(R.id.web)
    WebView web;
    private List<String> goodIdLists;
    private SearchTicketDialog searchTicketDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWeb();
    }


    private void initWeb() {
        web.setHorizontalScrollBarEnabled(false);
        web.setVerticalScrollBarEnabled(false);
        WebSettings webSettings = web.getSettings();
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
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(APP.application.getCacheDir().getAbsolutePath());
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);
        initWebClient(web);
        web.addJavascriptInterface(this, "java_obj");
        if (isPkgInstalled(this, "com.taobao.taobao")) {
            AlibcLogin alibcLogin = AlibcLogin.getInstance();
            alibcLogin.showLogin(new AlibcLoginCallback() {

                @Override
                public void onSuccess(int i) {
                    web.loadUrl(getIntent().getStringExtra("url").replace("\\", ""));
                    searchTicketDialog = new SearchTicketDialog(TaoBaoCollectionActivity.this);
                    searchTicketDialog.showDialog();
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        } else {
            web.loadUrl(getIntent().getStringExtra("url").replace("\\", ""));
        }
    }

    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        Intent intent = new Intent(TaoBaoCollectionActivity.this, Act_TaoBaoSearchTicket.class);
        intent.putExtra("ids", JSON.toJSONString(goodIdLists));
        startActivity(intent);
    }

    public static boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }


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
                if (!TextUtils.isEmpty(goodsId)) {
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
                        Intent intent = new Intent(TaoBaoCollectionActivity.this, TaoBaoDetailActivity.class);
                        intent.putExtra("id", goodsId);
                        startActivity(intent);
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


                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //去标题栏
                view.loadUrl("javascript:(function() { "
                        + "document.getElementsByClassName('module-topbar')[0].style.display='none'; " + "})()");

                web.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementsByTagName('html')[0].innerHTML);");

                if (searchTicketDialog != null) {
                    searchTicketDialog.dismissDislog();
                }

            }
        });
    }

    @JavascriptInterface
    public void showSource(String html) {
        if (!TextUtils.isEmpty(html)) {
            goodIdLists = match(html, "div", "data-numid");
        }
    }


    /**
     * 获取指定HTML标签的指定属性的值
     *
     * @param source  要匹配的源文本
     * @param element 标签名称
     * @param attr    标签的属性名称
     * @return 属性值列表
     */
    private List<String> match(String source, String element, String attr) {
        List<String> result = new ArrayList<>();
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            result.add(r);
        }
        return result;
    }


    @Override
    protected String title() {
        if (StringUtils.isEmpty(getIntent().getStringExtra("name")))
            return "详情";
        else
            return getIntent().getStringExtra("name");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_taobaocollection;
    }

    @Override
    public void show(Object obj) {

    }

    @Override
    public boolean check(WebView webView, String url) {
        return false;
    }

    @Override
    public void JavaScriptObjectMethod(String url) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web.canGoBack()) {
                web.goBack();
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
