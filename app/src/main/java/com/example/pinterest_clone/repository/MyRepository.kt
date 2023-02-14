package com.example.pinterest_clone.repository

import com.example.pinterest_clone.db.PhotoHomeDao
import com.example.pinterest_clone.model.Pin
import com.example.pinterest_clone.network.service.ApiService
import javax.inject.Inject

class MyRepository @Inject constructor(
    private val apiService: ApiService,
    private val photoHomeDao: PhotoHomeDao
) {

    /**
     * Retrofit Related
     */

    suspend fun apiPhotoHome(page: Int, perPage: Int) = apiService.getPhotos(page, perPage)
    suspend fun apiPhotoRelated(id: String) = apiService.getRelatedPhotos(id)
    suspend fun apiSearchResultPhotos(page: Int, query: String, perPage: Int) =
        apiService.getSearchPhoto(page, query, perPage)

    suspend fun apiSearchProfilePhotos(page: Int, query: String, perPage: Int) =
        apiService.getSearchProfile(page, query, perPage)

    suspend fun apiChatUpdatePhotos(page: Int, perPage: Int) = apiService.getTopics(page, perPage)
    suspend fun apiGetUriForDownload(id: String) = apiService.getUrlForDownloadImage(id)

    /**
     *Room db Related
     */

    suspend fun getPhotosFromDB() = photoHomeDao.getPhotosFromDB()
    suspend fun insertPhotosToDB(pin: Pin) = photoHomeDao.insertPhotosToDB(pin)
    suspend fun deletePhotosFromDB() = photoHomeDao.deletePhotosFromDB()
}