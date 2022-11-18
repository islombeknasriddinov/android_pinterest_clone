package com.example.pinterest_clone.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.PhotoList
import com.example.pinterest_clone.repository.PhotoHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
            photoHomeRepository.apiPhotoHome(page, perPage).enqueue(object : Callback<PhotoList> {
                override fun onResponse(call: Call<PhotoList>, response: Response<PhotoList>) {
                    photoHomeFromApi.postValue(response.body()!!)
                    isLoading.value = false

                }
                override fun onFailure(call: Call<PhotoList>, t: Throwable) {
                    onError("Error : ${t.message}")
                }
            })
        }
    }


    fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

}