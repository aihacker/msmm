package com.example.qimiao.kotlinframework.network


import com.d2956987215.mow.util.User
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by lk on 2018/6/8.
 */
class HeardInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        val response = chain?.proceed(request)
        if(response?.code()==401){
            val build = chain?.request()?.newBuilder()

                    ?.addHeader("Accept", "Application/json")
                    ?.addHeader("Authorization", "Bearer " + User.token())
                    ?.build()
            return chain.proceed(build)
        }

        return response
    }


}
