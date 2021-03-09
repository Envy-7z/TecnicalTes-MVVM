package com.wisnu.tecnicaltes_mvvm.api

import com.wisnu.tecnicaltes_mvvm.model.MainResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface ApiService {

    @GET("everything?q=pendidikan&from=2021-02-09&sortBy=publishedAt&apiKey=3b3ec71ff13d4813bef52369983409cb")
    fun getData(): Flowable<MainResponse>

}