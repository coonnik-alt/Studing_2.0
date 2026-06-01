package com.example.test_application.Data

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.jvm.java

object RetrofitInstance {

    private val clientTimeout = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()
    private const val BASE_URL = "https://drive.usercontent.google.com/"

    val api: CoursesApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientTimeout)
            .addConverterFactory(
                GsonConverterFactory.create())
            .build()
            .create(CoursesApi::class.java)
}