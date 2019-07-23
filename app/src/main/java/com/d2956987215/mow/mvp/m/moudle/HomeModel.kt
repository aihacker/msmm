package com.d2956987215.mow.mvp.m.moudle


import com.d2956987215.mow.rxjava.response.BaseResponse
import com.d2956987215.mow.rxjava.xapi.XApi
import com.example.qimiao.kotlinframework.network.RetrofitClient
import io.reactivex.Observable
import java.util.*

import kotlin.collections.HashMap

/**
 * Created by lk on 2018/6/8.
 * model 层  用于与apiserver 交互
 */
class HomeModel {

    val retrofitClient = RetrofitClient.getInstance()
    val apiService = retrofitClient.create(XApi::class.java)
//    fun <T> loadData(url: String?, map: HashMap<String, Any>?, vararg value: Any?): Observable<T>? {
//        var isfalg = true
//        if (value.size == 2) {
//            isfalg = false
//        }
//        when (isfalg) {
//            true -> return apiService?.getHomeData<HomeBean>() as Observable<T>
//            false -> return apiService?.getHomeMoreData<HomeBean>(value[1] as String, "2") as Observable<T>
//        }
//    }


    fun <T :BaseResponse> getguide(url: String?, map: HashMap<String, Any>?, vararg value: Any?): Observable<T>? {
        return apiService?.getguide(map) as  Observable<T>
    }

    fun <T :BaseResponse> IsNeedRecord(url: String?, map: HashMap<String, String>?, vararg value: Any?): Observable<T>? {
        var data = value[0] as kotlin.Array<Any>
        return apiService?.IsNeedRecord(map,data[0].toString()) as  Observable<T>
    }

    fun <T :BaseResponse> homeData(url: String?, map: HashMap<String, String>?, vararg value: Any?): Observable<T>? {
        return apiService?.homeData(map) as  Observable<T>
    }

    fun <T :BaseResponse> getHomeGoodsList(url: String?, map: HashMap<String, String>?, vararg value: Any?): Observable<T>? {
        return apiService?.getHomeGoodsList(map) as  Observable<T>
    }

    fun <T :BaseResponse> timelist(url: String?, map: HashMap<String, String>?, vararg value: Any?): Observable<T>? {
        return apiService?.timelist(map) as  Observable<T>
    }
    fun <T :BaseResponse> authTaoBaoNotice(url: String?, map: HashMap<String, String>?, vararg value: Any?): Observable<T>? {
        var data = value[0] as kotlin.Array<Any>
        return apiService?.authTaoBaoNotice(map,data[0].toString()) as  Observable<T>
    }

}