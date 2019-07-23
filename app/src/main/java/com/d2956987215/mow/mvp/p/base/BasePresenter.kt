package com.d2956987215.mow.mvp.p.base

import android.content.Context
import com.d2956987215.mow.mvp.p.ParsingPresenter
import com.d2956987215.mow.rxjava.response.BaseResponse

/**
 * Created by lk on 2018/6/8.
 */
interface BasePresenter {
    fun<T : BaseResponse> start(vararg value:Any)
    fun<T : BaseResponse> start( value:HashMap<String,Any>)
    fun  setDialog(context: Context) :ParsingPresenter
}