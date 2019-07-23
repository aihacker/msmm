package com.d2956987215.mow.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.d2956987215.mow.R
import com.d2956987215.mow.activity.BaseActivity
import com.d2956987215.mow.bean.AreaBean
import com.d2956987215.mow.constant.Const
import com.example.urilslibrary.Utils
import kotlinx.android.synthetic.main.activity_login_phone_new.*
import kotlinx.android.synthetic.main.include_header.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LoginPhoneNewActivity : BaseActivity() {
    private var code = ""
    override fun show(obj: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login_phone_new
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login_phone_new)
        EventBus.getDefault().register(this)
        setOnClick()
        code = intent.extras.get("yaoqingma").toString()
        if (Const.phone !=null&&Const.phone.isNotEmpty()) {
            et_phone_num.setText(Const.phone)
        }
    }

    private fun setOnClick() {
        bt_next.setOnClickListener {
            var phone = et_phone_num.text.toString()
            val mobileNO = Utils.isMobileNO(phone)
            if (!mobileNO) {
                Utils.ToastShort(this, "请输入正确的手机号")
                return@setOnClickListener
            }
            var intent = Intent(applicationContext, CodeLoginActivity::class.java)
            intent.putExtra("yaoqingma", code)
            intent.putExtra("phone", phone)
            intent.putExtra("type", "0")
            startActivity(intent)
        }
        rl_back.setOnClickListener {
            finish()
        }

        bt_select_area.setOnClickListener {
            startActivity(Intent(applicationContext, AreaSelectActivity::class.java))
        }
        et_phone_num.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                val s = editable.toString()
                if (s.isNotEmpty()) {
                    iv_del_phone.visibility = View.VISIBLE
                } else {
                    iv_del_phone.visibility = View.GONE
                }
                val mobileNO = Utils.isMobileNO(s)
                if (mobileNO) {
                    bt_next.setBackgroundResource(R.drawable.bt_login_nextd)
                    bt_next.setTextColor(context.resources.getColor(R.color.white))
                } else {
                    bt_next.setBackgroundResource(R.drawable.bt_login_next)
                    bt_next.setTextColor(context.resources.getColor(R.color.black))
                }


            }
        })
        iv_del_phone.setOnClickListener {
            et_phone_num.setText("")
        }
        tv_xy.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java).putExtra("url", Const.XYYRL).putExtra("title", "买手妈妈协议"))
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAreaCode(event: AreaBean.DataBean) {
        Const.area=event.areaCode
        val s = "+" +  Const.area
        tv_area_code.text = s
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
