package com.example.pinterest_clone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinterest_clone.model.PhotoHomePage
import com.example.pinterest_clone.model.Pin
import com.example.pinterest_clone.model.RelatedPhotos
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
class DetailViewModel  @Inject constructor(private val photoHomeRepository: PhotoHomeRepository) :
    ViewModel(){
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val relatedPhotoFromApi = MutableLiveData<ArrayList<PhotoHomePage>>()

    fun apiRelatedPhoto(id: String){
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            photoHomeRepository.apiPhotoRelated(id).enqueue(object : Callback<RelatedPhotos> {
                override fun onResponse(
                    call: Call<RelatedPhotos>,
                    response: Response<RelatedPhotos>
                ) {
                    val photoList: ArrayList<PhotoHomePage> = response.body()!!.results!!
                    relatedPhotoFromApi.postValue(photoList)
                    isLoading.value = false
                }

                override fun onFailure(call: Call<RelatedPhotos>, t: Throwable) {
                    onError("Error : ${t.message}")
                }

            })
        }

    }

    fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

    /**
     * Room related
     */

    fun insertPhotoHomeDB(pin: Pin){
        viewModelScope.launch {
            photoHomeRepository.insertPhotosToDB(pin)
        }

    }

}