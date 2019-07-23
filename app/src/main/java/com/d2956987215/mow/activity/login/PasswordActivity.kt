package com.d2956987215.mow.activity.login

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build

import android.os.Bundle

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.d2956987215.mow.R
import com.d2956987215.mow.activity.BaseActivity
import com.d2956987215.mow.constant.Const
import com.d2956987215.mow.presenter.LoginPresenter
import com.example.urilslibrary.Utils
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.include_header.*


class PasswordActivity : BaseActivity() {
    private var phone = ""
    private var area = ""
    private var isFlag = false
    private var presenter: LoginPresenter? = null
    override fun show(obj: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_password
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onClick()
        phone = intent.extras.get("phone").toString()
        area = Const.area
        tv_phone.text = "+$phone"

        et_password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        presenter = LoginPresenter(this)
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onClick() {
        rl_back.setOnClickListener {
            finish()
        }
        iv_visibility.setOnClickListener {
            isFlag = !isFlag
            if (isFlag) {
                iv_visibility.setBackgroundDrawable(context.getDrawable(R.mipmap.eye_open))
                et_password.inputType = EditorInfo.TYPE_CLASS_TEXT
            } else {
                iv_visibility.setBackgroundDrawable(context.getDrawable(R.mipmap.eye_close))
                val inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                et_password.inputType = inputType
            }

        }
        et_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                tv_error.visibility = View.GONE
                val s = editable.toString()
                if (s.isNotEmpty()) {
                    et_del.visibility = View.VISIBLE
                    bt_next.setBackgroundResource(R.drawable.bt_login_nextd)
                    bt_next.setTextColor(context.resources.getColor(R.color.white))
                } else {
                    et_del.visibility = View.GONE
                    bt_next.setBackgroundResource(R.drawable.bt_login_next)
                    bt_next.setTextColor(context.resources.getColor(R.color.black))
                }


            }
        })

        et_del.setOnClickListener {
            et_password.setText("")
        }
        bt_next.setOnClickListener {
            if (et_password.text.toString().isEmpty()) {
                Utils.ToastShort(this, "请输入密码")
                return@setOnClickListener
            }
            presenter?.login(phone, et_password.text.toString())
        }

        tv_forget.setOnClickListener {
            startActivity(Intent(applicationContext, FindPassActivity::class.java))
        }
        tv_code_login.setOnClickListener {
            // startActivity(Intent(applicationContext, LoginPhoneActivity::class.java))
            val intent = Intent(applicationContext, CodeLoginActivity::class.java)
            intent.putExtra("type", "1")
            intent.putExtra("yaoqingma", "")
            intent.putExtra("phone", phone)
            startActivity(intent)

        }
    }


}
