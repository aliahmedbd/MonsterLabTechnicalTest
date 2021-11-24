package com.bracbank.digitallanding.retrofit

import com.google.firebase.BuildConfig
import com.monsterlab.technicaltest.network.APIInterface
import com.monsterlab.technicaltest.network.URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by Ali Ahmed, mail: aliahmedaiub@gmail.com
 */
object RetrofitClient {
    private const val BASE_URL = URL.BASE_URL

    private val retrofitClient: Retrofit.Builder by lazy {

        val levelType: HttpLoggingInterceptor.Level =
            if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
                HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(logging)
        okhttpClient.connectTimeout(60, TimeUnit.SECONDS)
        okhttpClient.readTimeout(30, TimeUnit.SECONDS)

        getInterceptor()?.let { okhttpClient.addInterceptor(it) }

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: APIInterface by lazy {
        retrofitClient
            .build()
            .create(APIInterface::class.java)
    }

    private fun getInterceptor(): Interceptor? {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
//                val token = SharedPreference(ApplicationController.instance).sharedPref.getString(
//                    Constants.AUTH_TOKEN, "")
                val original = chain.request()
                val builder = original.newBuilder()
                val request = builder
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Accept", "application/json")
//                    .addHeader(
//                        "Authorization",
//                        token!!
//                    )
                    .method(original.method, original.body)
                    .build()
                return chain.proceed(request)
            }
        }
    }
}