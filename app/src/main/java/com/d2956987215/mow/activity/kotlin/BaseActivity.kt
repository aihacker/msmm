package com.d2956987215.mow.activity.kotlin

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.d2956987215.mow.activity.APP
import com.d2956987215.mow.dialog.LoadingDialog
import com.d2956987215.mow.eventbus.SearchDialogHideEvent
import com.d2956987215.mow.eventbus.SearchEvent
import com.d2956987215.mow.mvp.v.Contract
import com.d2956987215.mow.util.ActivityManager
import com.d2956987215.mow.util.StatusNavUtils
import com.example.urilslibrary.Utils
import com.umeng.analytics.MobclickAgent
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created by lk on 2018/6/19.
 */
abstract class BaseActivity : AppCompatActivity(), Contract.View {
    var mContext: Activity? = this
    internal var loadingDialog: LoadingDialog? = null
    private var mOutRestart = false
    override fun onStart() {
        super.onStart()

        mContext = initview()
//        if (mContext != null) {
//            PushAgent.getInstance(mContext).onAppStart()
//        }
//        Log.e("tag--->", activity.localClassName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        //     StatusNavUtils.setStatusBarColor(this,0x00000000);
        StatusNavUtils.setStatusBarColor(this, 0x00000000)
        ActivityManager.getAppManager().addActivity(this)
    }

    override fun onResume() {
        super.onResume()
        if (mContext != null) {
            MobclickAgent.onPageStart(mContext?.localClassName.toString())
            MobclickAgent.onResume(mContext)
        }

    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPageEnd(mContext?.localClassName.toString())
        MobclickAgent.onPause(mContext)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    protected fun changeStatusBarTextColor(isBlack: Boolean) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//设置状态栏黑色字体
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE//恢复状态栏白色字体
            }
        }
    }


    //加载动画

    private fun createProgressDialog(): LoadingDialog {
        val dialog = LoadingDialog(this)
        dialog.setCancelable(true)
        return dialog
    }

    fun showProgress() {
        if (loadingDialog == null) {
            loadingDialog = createProgressDialog()
        }
        loadingDialog?.show()

    }

    fun hideProgress() {
        if (loadingDialog != null) {
            loadingDialog?.stop()
        }
    }
    fun getContext(): Context {
        return this
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun searchEvents(event: SearchEvent) {
//        huoqufuzhi()
        mOutRestart = true

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun searchEvent(event: SearchDialogHideEvent) {
        mOutRestart = false



    }
    abstract fun initview(): Activity
}