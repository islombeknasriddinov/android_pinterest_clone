package com.example.pinterest_clone.repository

import com.example.pinterest_clone.db.PhotoHomeDao
import com.example.pinterest_clone.model.Pin
import com.example.pinterest_clone.network.service.PhotoService
import javax.inject.Inject

class PhotoHomeRepository @Inject constructor(private val photoService: PhotoService, private val photoHomeDao: PhotoHomeDao) {

    /**
     * Retrofit Related
     */

    fun apiPhotoHome(page: Int, perPage: Int) = photoService.getPhotos(page, perPage)
    fun apiPhotoRelated(id: String) = photoService.getRelatedPhotos(id)
    fun apiSearchResultPhotos(page: Int, query: String, perPage: Int) = photoService.getSearchPhoto(page, query, perPage)
    fun apiSearchProfilePhotos(page: Int, query: String, perPage: Int) = photoService.getSearchProfile(page, query, perPage)
    fun apiChatUpdatePhotos(page: Int, perPage: Int) = photoService.getTopics(page, perPage)
    fun apiGetUriForDownload(id: String) = photoService.getUrlForDownloadImage(id)

    /**
     *Room db Related
     */

    suspend fun getPhotosFromDB() = photoHomeDao.getPhotosFromDB()
    suspend fun insertPhotosToDB(pin: Pin) = photoHomeDao.insertPhotosToDB(pin)
    suspend fun deletePhotosFromDB() = photoHomeDao.deletePhotosFromDB()
}