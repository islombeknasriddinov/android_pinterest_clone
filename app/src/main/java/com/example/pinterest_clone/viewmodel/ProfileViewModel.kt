package com.example.pinterest_clone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinterest_clone.model.Pin
import com.example.pinterest_clone.repository.PhotoHomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val photoHomeRepository: PhotoHomeRepository) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val photoHomeFromDB = MutableLiveData<ArrayList<Pin>>()

    /**
     * Retrofit Related
     */

    fun getPhotoHomeFromDB() {
        viewModelScope.launch {
            photoHomeFromDB.postValue(photoHomeRepository.getPhotosFromDB() as ArrayList<Pin>)
        }
    }
}