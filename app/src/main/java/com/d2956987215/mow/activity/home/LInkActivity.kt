package com.d2956987215.mow.activity.home

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.d2956987215.mow.activity.BaseActivity
import com.d2956987215.mow.bean.LinkBean
import com.d2956987215.mow.util.ToastUtil
import com.example.urilslibrary.Utils
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.activity_link.*
import kotlinx.android.synthetic.main.include_header.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode

import org.greenrobot.eventbus.Subscribe


class LInkActivity : BaseActivity() {

    private var response: LinkBean? = null
    override fun show(obj: Any?) {

    }

    override fun getLayoutId(): Int {
        return com.d2956987215.mow.R.layout.activity_link
    }

    override fun title(): String {
        return "转链"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_link)
        EventBus.getDefault().register(this)
        setOnclick()
    }

    private fun setOnclick() {
        rl_back.setOnClickListener { finish() }
        iv_del_tip.setOnClickListener {
            ll_tip.visibility = View.GONE
        }

        tv_link_copy.setOnClickListener {
            if (response != null) {
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // 将文本内容放到系统剪贴板里。
                cm.text = response?.data?.isLink.toString()
                Utils.ToastShort(applicationContext, "复制成功")
            } else {
                Utils.ToastShort(applicationContext, "复制内容为空")
            }
        }
        tv_share.setOnClickListener {
            ShareAction(this).withText(response?.data?.content)
                    .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA)
                    .setCallback(MyUMShareListener()).open()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEventMainThread(response: LinkBean) {
        if (response != null) {
            this.response = response
            tv_message.text = response.data.title
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private inner class MyUMShareListener : UMShareListener {

        override fun onStart(share_media: SHARE_MEDIA) {
            ToastUtil.show(this@LInkActivity, "分享开始，请稍后")
        }

        override fun onResult(share_media: SHARE_MEDIA) {
            ToastUtil.show(this@LInkActivity, "分享成功")
        }

        override fun onError(share_media: SHARE_MEDIA, throwable: Throwable) {
            ToastUtil.show(this@LInkActivity, "分享失败")
        }

        override fun onCancel(share_media: SHARE_MEDIA) {
            ToastUtil.show(this@LInkActivity, "分享取消")
        }
    }
}
