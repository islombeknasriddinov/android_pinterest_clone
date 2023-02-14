package com.example.pinterest_clone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.withContext

@HiltViewModel
class ExploreViewModel @Inject constructor(private val myRepository: MyRepository) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val searchResultFromApi = MutableLiveData<ArrayList<PhotoHomePage>>()

    /**
     * Retrofit Related
     */


    fun searchPhotos(page: Int, query: String, perPage: Int) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = myRepository.apiSearchResultPhotos(page, query, perPage)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val res = response.body()
                    searchResultFromApi.postValue(res?.results!!)
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