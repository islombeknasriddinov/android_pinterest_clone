package com.example.pinterest_clone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pinterest_clone.model.Topic
import com.example.pinterest_clone.repository.PhotoHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.pinterest_clone.utils.Logger
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(private val photoHomeRepository: PhotoHomeRepository) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val updatePhotosFromApi = MutableLiveData<ArrayList<Topic>>()


    fun apiTopics(page: Int, perPage: Int) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            photoHomeRepository.apiChatUpdatePhotos(page, perPage)
                .enqueue(object : Callback<ArrayList<Topic>> {
                    override fun onResponse(
                        call: Call<ArrayList<Topic>>,
                        response: Response<ArrayList<Topic>>
                    ) {
                        updatePhotosFromApi.postValue(response.body()!!)
                        isLoading.value = false
                    }

                    override fun onFailure(call: Call<ArrayList<Topic>>, t: Throwable) {
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