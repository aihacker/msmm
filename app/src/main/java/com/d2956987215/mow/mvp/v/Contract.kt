package com.d2956987215.mow.mvp.v

import android.content.Context
import com.d2956987215.mow.mvp.p.base.BasePresenter
import com.d2956987215.mow.mvp.v.base.BaseView
import com.d2956987215.mow.rxjava.response.BaseResponse
import java.util.*

/**
 * Created by lk on 2018/6/8.
 */
interface Contract {
    interface View : BaseView<Presenter> {
        fun <T > setData(type: String, bean: T)
        fun  onError( type: String,error: Throwable)
    }

    interface Presenter : BasePresenter {
        fun <T : BaseResponse> requestData(type: String, method:String, url: String?, map: HashMap<String, Any>?, vararg param: Any?)

//
    }

}