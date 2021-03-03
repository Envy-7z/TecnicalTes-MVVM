package com.wisnu.tecnicaltes_mvvm.repo

import com.wisnu.tecnicaltes_mvvm.api.NetworkModule
import com.wisnu.tecnicaltes_mvvm.model.MainResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class Repository {
    fun getData(responHandler : (MainResponse)-> Unit , errorHandler : (Throwable)-> Unit){
        NetworkModule.service().getData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    responHandler(it)
            },{
                    errorHandler(it)
            })
    }
}