package com.example.pinterest_clone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.Profile
import com.example.pinterest_clone.model.ResultProfiles
import com.example.pinterest_clone.model.SearchPhotos
import com.example.pinterest_clone.repository.PhotoHomeRepository
import com.example.pinterest_clone.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfilesViewModel  @Inject constructor(private val photoHomeRepository: PhotoHomeRepository) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val photoHomeFromApi = MutableLiveData<ArrayList<Profile>>()

    /**
     * Retrofit Related
     */

    fun profilePhotos(page: Int, query: String, perPage: Int){
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            photoHomeRepository.apiSearchProfilePhotos(page, query, perPage).enqueue(object : Callback<ResultProfiles>{
                override fun onResponse(
                    call: Call<ResultProfiles>,
                    response: Response<ResultProfiles>
                ) {
                    photoHomeFromApi.postValue(response.body()!!.results)
                    isLoading.value = false
                }

                override fun onFailure(call: Call<ResultProfiles>, t: Throwable) {
                    onError(t.message.toString())
                }

            })
        }
    }

    fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

}