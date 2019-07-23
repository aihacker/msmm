package com.d2956987215.mow.activity.login

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import com.d2956987215.mow.R
import com.d2956987215.mow.activity.BaseActivity
import com.d2956987215.mow.constant.Const
import com.d2956987215.mow.dialog.RegistDialog
import com.d2956987215.mow.dialog.SettingPasswordDialog
import com.d2956987215.mow.eventbus.GetCodeSuccess
import com.d2956987215.mow.presenter.LoginPresenter
import com.d2956987215.mow.rxjava.Request
import com.d2956987215.mow.rxjava.Result
import com.d2956987215.mow.rxjava.RxJavaUtil
import com.d2956987215.mow.rxjava.response.BaseResponse
import com.d2956987215.mow.rxjava.response.RegisterResponse
import com.d2956987215.mow.util.DisplayUtil
import com.d2956987215.mow.util.ToastUtil
import com.d2956987215.mow.view.VerifyEditText
import kotlinx.android.synthetic.main.activity_code_login.*
import kotlinx.android.synthetic.main.activity_login_phone_new.tv_phone
import kotlinx.android.synthetic.main.include_header.*
import org.greenrobot.eventbus.EventBus
import java.util.HashMap

class CodeLoginActivity : BaseActivity(), VerifyEditText.inputCompleteListener {


    private var yaoqingma = ""
    private var phone = ""
    private var area = ""
    private var type = ""
    private var presenter: LoginPresenter? = null
    private var content = ""
    override fun show(obj: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_code_login
    }

    override fun inputComplete(et: VerifyEditText?, content: String?) {
        if (content != null) {
            this.content = content
        }

        if (type == "1") {
            presenter?.loginphone(phone, content)
        } else {
            registerAuthCode(content!!)
        }



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_code_login)
        initm()
        requst()
        setOnclick()
    }

    private fun requst() {
        if (type == "1") {
            presenter?.getLoginCode(phone)
        } else {
            presenter?.registerAuthCode(phone, yaoqingma)
        }


    }

    private fun setOnclick() {
        rl_back.setOnClickListener { finish() }

        tv_time.setOnClickListener {

            if (content != null) {
//                registerAuthCode(content)
                requst()
                var count = TimeCount(60000, 1000)
                count.start()
            }
        }
    }

    private fun initm() {
        yaoqingma = intent.extras.get("yaoqingma").toString()
        phone = intent.extras.get("phone").toString()
        area = Const.area
        type = intent.extras.get("type").toString()
        tv_phone.text = "$area $phone"
        vet_code.isAllLineLight = true
        vet_code.setInputCompleteListener(this)
        presenter = LoginPresenter(this)
        var count = TimeCount(60000, 1000)
        count.start()
    }

    /*倒计时类 */
    inner class TimeCount
    (millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            //计时过程中
            tv_time.isClickable = false
            tv_time.text = (millisUntilFinished / 1000).toString() + "秒后重新发送"
        }

        override fun onFinish() {
            //计时完成
            tv_time.text = "重新获取"
            tv_time.isClickable = true
        }
    }


    fun registerAuthCode(verifycode: String) {
        val time = DisplayUtil.getCurrTime().toString()
        val key = "T#rr#6ZS6lexJHDD"
        val code = DisplayUtil.md5(key + phone + time)

//        val map = HashMap<String, String>()
//        map["mobile"] = phone
//        map["lcode"] = yaoqingma
//        map["verifycode"] = verifycode
//        map["code"] = code

        presenter?.register1(phone, yaoqingma, verifycode)

    }
}
