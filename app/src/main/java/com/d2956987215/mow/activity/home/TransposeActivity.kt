package com.d2956987215.mow.activity.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.d2956987215.mow.R
import com.d2956987215.mow.activity.BaseActivity
import com.d2956987215.mow.bean.LinkBean
import com.d2956987215.mow.dialog.LinkErrorDialog
import com.d2956987215.mow.rxjava.Request
import com.d2956987215.mow.rxjava.Result
import com.d2956987215.mow.rxjava.RxJavaUtil
import com.d2956987215.mow.util.ActivityUtils
import com.d2956987215.mow.util.User
import com.example.urilslibrary.Utils
import kotlinx.android.synthetic.main.activity_transpose.*
import kotlinx.android.synthetic.main.include_header.*
import org.greenrobot.eventbus.EventBus
import java.util.HashMap

class TransposeActivity : BaseActivity() {
    private var content = ""
    private var type = ""
    private var mContext: Activity? = null
    override fun show(obj: Any?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_transpose
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_transpose)
        mContext = this
        type = intent.extras.get("type").toString()
        if ("0" == type) {
            content = intent.extras.get("content").toString()
            et_link.setText(content)
        }
        onClick()
    }

    override fun title(): String {
        return "转链"
    }

    private fun onClick() {
        rl_back.setOnClickListener { this.finish() }
        iv_del_tip.setOnClickListener {
            ll_tip.visibility = View.GONE
        }
        tv_link_copy.setOnClickListener {
            et_link.setText("")
        }

        ll_start_link.setOnClickListener {

            content = et_link.text.toString()
            if (content.isEmpty()) {
                Utils.ToastShort(application, "填写内容为空")
                return@setOnClickListener
            }
            checEixtkLink()

        }

        et_link.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(et: Editable?) {
                val s = et.toString()
                if (s.isEmpty()) {

                    ll_start_link.setBackgroundResource(R.drawable.bt_login_next)
                    tv_start_link.setTextColor(context.resources.getColor(R.color.black))
                    iv_start_link.setBackgroundResource(R.mipmap.link)
                } else {
                    ll_start_link.setBackgroundResource(R.drawable.bt_login_nextd)
                    tv_start_link.setTextColor(context.resources.getColor(R.color.white))
                    iv_start_link.setBackgroundResource(R.mipmap.link_withe)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })


    }

    private fun checEixtkLink() {
        if (User.uid() < 0) {
            ActivityUtils.startLoginAcitivy(mContext)
            Utils.ToastShort(application, "还未登录，请登录后使用")
            finish()
            return
        }
        val map = HashMap<String, String>()
        map["keyword"] = content
        Request<LinkBean>().request(RxJavaUtil.xApi().jiantie(map), "获取剪贴板数据", context, false, object : Result<LinkBean>() {
            override fun get(response: LinkBean) {
                if ( response.data.type == 1) {
                    EventBus.getDefault().post(response)
                    startActivity(Intent(mContext, LInkActivity::class.java))
                    finish()

                } else {
//                    var dialog = LinkErrorDialog(mContext, LinkErrorDialog.CallBack {
//                        if (User.uid() < 0) {
//                            ActivityUtils.startLoginAcitivy(mContext)
//                            Utils.ToastShort(application, "还未登录，请登录后使用")
//                            finish()
//                        }
//                        requstData()
//                    })
//
//                    dialog.show()

                    Utils.ToastShort(application, "未搜索到该链接")
                }

            }
        })
    }

    private fun requstData() {
        val map = HashMap<String, String>()
        map["keyword"] = et_link.text.toString()
        map["user_id"] = User.uid().toString()
        map["type"] = type
        Request<LinkBean>().request(RxJavaUtil.xApi().chainLink(map), "转链接口", this@TransposeActivity, true, object : Result<LinkBean>() {
            override fun get(response: LinkBean) {
                if (response != null) {
                    Utils.ToastShort(application, "提交成功")
                    finish()

                }

            }

            override fun onServerError(errDesc: String) {
                super.onServerError(errDesc)
            }

            override fun onBackErrorMessage(response: LinkBean) {
                super.onBackErrorMessage(response)
            }
        })


    }

}
