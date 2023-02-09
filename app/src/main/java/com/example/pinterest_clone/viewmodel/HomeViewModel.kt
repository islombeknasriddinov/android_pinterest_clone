package com.example.pinterest_clone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pinterest_clone.model.PhotoList
import com.example.pinterest_clone.repository.PhotoHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val photoHomeRepository: PhotoHomeRepository) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val photoHomeFromApi = MutableLiveData<PhotoList>()

    /**
     * Retrofit Related
     */

    fun apiPhotoHome(page: Int, perPage: Int) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = photoHomeRepository.apiPhotoHome(page, perPage)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val res = response.body()

                    photoHomeFromApi.postValue(res!!)
                    isLoading.value = false
                } else {
                    onError("onError : ${response.message()}")
                }
            }
        }
    }


    fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

}