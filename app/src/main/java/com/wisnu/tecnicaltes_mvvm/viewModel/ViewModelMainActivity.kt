package com.wisnu.tecnicaltes_mvvm.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wisnu.tecnicaltes_mvvm.model.MainResponse
import com.wisnu.tecnicaltes_mvvm.repo.Repository

class ViewModelMainActivity : ViewModel() {
    val repository = Repository()

    var responData = MutableLiveData<MainResponse>()
    var isError = MutableLiveData<Throwable>()
    var isLoading = MutableLiveData<Boolean>()
    fun getListData(){
        isLoading.value = true

        repository.getData({
            responData.value = it
            isLoading.value = false
        },{
            isError.value = it
            isLoading.value = false
        })
    }
}