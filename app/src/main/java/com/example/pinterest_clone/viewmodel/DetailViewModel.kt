package com.example.pinterest_clone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.Pin
import com.example.pinterest_clone.model.Uri
import com.example.pinterest_clone.repository.MyRepository
import com.example.pinterest_clone.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val myRepository: MyRepository) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val relatedPhotoFromApi = MutableLiveData<ArrayList<PhotoHomePage>>()
    val uriFromApi = MutableLiveData<String>()


    fun apiRelatedPhoto(id: String) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = myRepository.apiPhotoRelated(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val photoList: ArrayList<PhotoHomePage>? = response.body()?.results
                    relatedPhotoFromApi.postValue(photoList ?: ArrayList<PhotoHomePage>())
                    getUrlForDownloadImage(id)
                    isLoading.value = false
                } else {
                    onError("onError : ${response.message()}")
                }
            }
        }


    }


    private fun getUrlForDownloadImage(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = myRepository.apiGetUriForDownload(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val res: Uri? = response.body()
                    val uri: String? = res?.url
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
            myRepository.insertPhotosToDB(pin)
        }

    }

}