package com.example.pinterest_clone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.Pin
import com.example.pinterest_clone.repository.PhotoHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val photoHomeRepository: PhotoHomeRepository) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val relatedPhotoFromApi = MutableLiveData<ArrayList<PhotoHomePage>>()
    val uriFromApi = MutableLiveData<String>()


    fun apiRelatedPhoto(id: String) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            var response = photoHomeRepository.apiPhotoRelated(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val photoList: ArrayList<PhotoHomePage>? = response.body()?.results
                    relatedPhotoFromApi.postValue(photoList!!)
                    getUrlForDownloadImage(id)
                    isLoading.value = false
                } else {
                    onError("onError : ${response.message()}")
                }
            }
        }


    }


    fun getUrlForDownloadImage(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = photoHomeRepository.apiGetUriForDownload(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val res = response.body()
                    val uri = res?.url
                    uriFromApi.postValue(uri ?: "")
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

    /**
     * Room related
     */

    fun insertPhotoHomeDB(pin: Pin) {
        viewModelScope.launch {
            photoHomeRepository.insertPhotosToDB(pin)
        }

    }

}