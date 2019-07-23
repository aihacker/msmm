package com.d2956987215.mow.activity.login


import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.d2956987215.mow.R
import com.d2956987215.mow.activity.BaseActivity
import com.d2956987215.mow.constant.Const
import com.d2956987215.mow.rxjava.Request
import com.d2956987215.mow.rxjava.Result
import com.d2956987215.mow.rxjava.RxJavaUtil
import com.d2956987215.mow.rxjava.response.BaseResponse
import com.d2956987215.mow.util.User
import com.example.urilslibrary.Utils
import kotlinx.android.synthetic.main.activity_setting_paw.*
import kotlinx.android.synthetic.main.activity_setting_paw.et_del
import kotlinx.android.synthetic.main.activity_setting_paw.et_password
import kotlinx.android.synthetic.main.activity_setting_paw.iv_visibility
import kotlinx.android.synthetic.main.include_header.*
import java.util.HashMap

class SettingPawActivity : BaseActivity() {
    private var isFlage = false
    private var isSureFlage = false
    private var phone = ""
    private var lcode = ""
    private var area = ""

    override fun show(obj: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting_paw
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_setting_paw)

        initC()
        setOnClick()


    }

    private fun initC() {
        et_password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        et_sure_password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        phone = intent.extras.get("phone").toString()
        lcode = intent.extras.get("lcode").toString()
        area = Const.area
    }

    fun changePass(newPass: String) {
        val map = HashMap<String, String>()
        map["password"] = newPass
        map["user_id"] = User.uid().toString() + ""
        Request<BaseResponse>().request(RxJavaUtil.xApi().setPwd(map, "Bearer " + User.token()), "设置密码", this@SettingPawActivity, false, object : Result<BaseResponse>() {
            override fun get(response: BaseResponse) {
                startActivity(Intent(applicationContext, PasswordActivity::class.java).putExtra("phone", phone).putExtra("area", area))
            }
        })
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setOnClick() {
        rl_back.setOnClickListener { finish() }
        bt_next.setOnClickListener {
            val pwd = et_password.text.toString()
            val sure_pwd = et_sure_password.text.toString()
            if (sure_pwd != pwd || pwd.length < 7) {
                Utils.ToastShort(this, "密码不符合要求,请重新输入")
                return@setOnClickListener
            }
            changePass(sure_pwd)
        }


        iv_visibility.setOnClickListener {
            isFlage = !isFlage
            if (isFlage) {
                iv_visibility.setBackgroundDrawable(context.getDrawable(R.mipmap.eye_open))
                et_password.inputType = EditorInfo.TYPE_CLASS_TEXT
            } else {
                iv_visibility.setBackgroundDrawable(context.getDrawable(R.mipmap.eye_close))
                val inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                et_password.inputType = inputType
            }

        }


        iv_sure_visibility.setOnClickListener {
            isSureFlage = !isSureFlage
            if (isSureFlage) {
                iv_sure_visibility.setBackgroundDrawable(context.getDrawable(R.mipmap.eye_open))
                et_sure_password.inputType = EditorInfo.TYPE_CLASS_TEXT
            } else {
                iv_sure_visibility.setBackgroundDrawable(context.getDrawable(R.mipmap.eye_close))
                val inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                et_sure_password.inputType = inputType
            }

        }


        et_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val s = p0.toString()
                if (s.isNotEmpty()) {
                    et_del.visibility = View.VISIBLE
                } else {
                    et_del.visibility = View.GONE
                }

                if (s.length > 7 && s == et_sure_password.text.toString()) {
                    bt_next.setBackgroundResource(R.drawable.bt_login_nextd)
                    bt_next.setTextColor(context.resources.getColor(R.color.white))
                } else {
                    bt_next.setBackgroundResource(R.drawable.bt_login_next)
                    bt_next.setTextColor(context.resources.getColor(R.color.black))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })


        et_sure_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val s = p0.toString()
                if (s.isNotEmpty()) {
                    et_sure_del.visibility = View.VISIBLE
                } else {
                    et_sure_del.visibility = View.GONE
                }
                if (s.length > 7 && s == et_password.text.toString()) {
                    bt_next.setBackgroundResource(R.drawable.bt_login_nextd)
                    bt_next.setTextColor(context.resources.getColor(R.color.white))
                } else {
                    bt_next.setBackgroundResource(R.drawable.bt_login_next)
                    bt_next.setTextColor(context.resources.getColor(R.color.black))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })
    }
}
