package com.d2956987215.mow.activity.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.d2956987215.mow.R
import com.d2956987215.mow.activity.BaseActivity
import com.d2956987215.mow.util.WebViewUtils
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.include_header.*

class WebViewActivity : BaseActivity(), WebViewUtils.WebViewInterface {
    override fun check(webView: WebView?, url: String?): Boolean {
        return true
    }


    override fun JavaScriptObjectMethod(url: String?) {

    }

    private var title = ""
    private var url = ""
    override fun show(obj: Any?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_web_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_web_view)
        title = intent.extras.get("title").toString()
        url = intent.extras.get("url").toString()
        tv_title.text = title
        WebViewUtils(wv, this, this)
        wv.loadUrl(url)
        rl_back.setOnClickListener { finish() }
    }
}
