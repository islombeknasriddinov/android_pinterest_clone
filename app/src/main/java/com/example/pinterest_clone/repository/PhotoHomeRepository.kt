package com.example.pinterest_clone.repository

import com.example.pinterest_clone.network.service.PhotoService
import javax.inject.Inject

class PhotoHomeRepository @Inject constructor(private val photoService: PhotoService) {

    /**
     * Retrofit Related
     */

    suspend fun apiPhotoHome(page: Int, perPage: Int) = photoService.getPhotos(page, perPage)
    suspend fun apiPhotoRelated(id : String) = photoService.getRelatedPhotos(id)
}