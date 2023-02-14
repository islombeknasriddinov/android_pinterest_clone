package com.example.pinterest_clone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pinterest_clone.model.Topic
import com.example.pinterest_clone.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(private val myRepository: MyRepository) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val updatePhotosFromApi = MutableLiveData<ArrayList<Topic>>()


    fun apiTopics(page: Int, perPage: Int) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = myRepository.apiChatUpdatePhotos(page, perPage)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val res = response.body()
                    updatePhotosFromApi.postValue(res!!)
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