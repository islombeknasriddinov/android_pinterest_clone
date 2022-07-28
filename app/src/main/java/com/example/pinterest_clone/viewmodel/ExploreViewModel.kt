package com.example.pinterest_clone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.SearchPhotos
import com.example.pinterest_clone.repository.PhotoHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import com.example.pinterest_clone.utils.Logger

@HiltViewModel
class ExploreViewModel  @Inject constructor(private val photoHomeRepository: PhotoHomeRepository) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val searchResultFromApi = MutableLiveData<ArrayList<PhotoHomePage>>()

    /**
     * Retrofit Related
     */

    fun searchPhotos(page: Int, query: String, perPage: Int){
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            photoHomeRepository.apiSearchResultPhotos(page, query, perPage).enqueue(object : Callback<SearchPhotos>{
                override fun onResponse(
                    call: Call<SearchPhotos>,
                    response: Response<SearchPhotos>
                ) {
                    searchResultFromApi.postValue(response.body()!!.results)
                    isLoading.value = false
                }

                override fun onFailure(call: Call<SearchPhotos>, t: Throwable) {
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