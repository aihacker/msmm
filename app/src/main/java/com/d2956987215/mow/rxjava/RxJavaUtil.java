package com.d2956987215.mow.rxjava;

/*
 * Created by fizz on 2017/8/25.
 */


import android.util.Log;


import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.d2956987215.mow.rxjava.xapi.XApi;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;


import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJavaUtil {
    //TODO 需要更改成你自己的服务器请求地址并且需要引入gradle中rxJava的相应依赖（有标注）
    public static final String HOST = "http://106.15.53.235/api/";
    public static final String URL = "http://106.15.53.235/api/";
    public static final String ImageHOST = "http://msapi.maishoumm.com/";
//    http://msapi.maishoumm.com/api/


    private static XApi xApi;
    private static XApi xApi1;
    private static XApi xApi2;
    private static final long DEFAULT_TIMEOUT = 10;
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(getHttpLoggingInterceptor())
            .addInterceptor(new AddQueryParameterInterceptor())
            //其他配置
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build();
    private static OkHttpClient okHttpClient2 = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(getHttpLoggingInterceptor())
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            //其他配置
            .build();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static Retrofit retrofit = new Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(HOST)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .build();

    private static Retrofit retrofit1 = new Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(URL)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(gsonConverterFactory)

            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .build();



    private static Retrofit retrofit2 = new Retrofit
            .Builder()
            .client(okHttpClient2)
            .baseUrl(HOST)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(gsonConverterFactory)

            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .build();

    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.e("XuFeng", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return loggingInterceptor;
    }


    //获取XApi
    public static XApi xApi() {
        if (xApi == null) {
            xApi = retrofit.create(XApi.class);
        }
        return xApi;
    }

    //获取XApi
    public static XApi xApi1() {
        if (xApi1 == null) {
            xApi1 = retrofit1.create(XApi.class);
        }
        return xApi1;
    }
    public static XApi xApi2() {
        if (xApi2 == null) {
            xApi2 = retrofit2.create(XApi.class);
        }
        return xApi2;
    }
    private static class NullOnEmptyConverterFactory extends Converter.Factory {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);
                }
            };
        }
    }

    public static class AddQueryParameterInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Request originalRequest = chain.request();

            HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here
                    .addQueryParameter("device_id", DeviceUtils.getAndroidID())
                    .addQueryParameter("version", AppUtils.getAppVersionCode() + "")
                    .build();
            okhttp3.Request build = originalRequest.newBuilder().url(modifiedUrl).addHeader("Connection", "close").header("Accept-Encoding", "identity").build();
//            request = originalRequest.newBuilder().url(modifiedUrl)
//                    .addHeader("Connection", "close").build();
            return chain.proceed(build);
        }
    }
}
