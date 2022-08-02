package com.example.pinterest_clone.repository

import com.example.pinterest_clone.db.PhotoHomeDao
import com.example.pinterest_clone.model.Pin
import com.example.pinterest_clone.network.service.PhotoService
import javax.inject.Inject

class PhotoHomeRepository @Inject constructor(private val photoService: PhotoService, private val photoHomeDao: PhotoHomeDao) {

    /**
     * Retrofit Related
     */

    suspend fun apiPhotoHome(page: Int, perPage: Int) = photoService.getPhotos(page, perPage)
    suspend fun apiPhotoRelated(id: String) = photoService.getRelatedPhotos(id)
    suspend fun apiSearchResultPhotos(page: Int, query: String, perPage: Int) = photoService.getSearchPhoto(page, query, perPage)
    suspend fun apiSearchProfilePhotos(page: Int, query: String, perPage: Int) = photoService.getSearchProfile(page, query, perPage)
    suspend fun apiChatUpdatePhotos(page: Int, perPage: Int) = photoService.getTopics(page, perPage)

    /**
     *Room db Related
     */

    suspend fun getPhotosFromDB() = photoHomeDao.getPhotosFromDB()
    suspend fun insertPhotosToDB(pin: Pin) = photoHomeDao.insertPhotosToDB(pin)
    suspend fun deletePhotosFromDB() = photoHomeDao.deletePhotosFromDB()
}