package com.wisnu.tecnicaltes_mvvm.api

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {


    fun getRetrofit(): Retrofit {
            return Retrofit.Builder().baseUrl("http://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }

    fun service() : ApiService = getRetrofit().create(ApiService::class.java)
    }
