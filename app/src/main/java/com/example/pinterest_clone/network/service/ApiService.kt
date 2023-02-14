package com.example.pinterest_clone.network.service

import com.example.pinterest_clone.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val ACCESS_KEY = "LEOaOtfFbCnYO9THs9tg7pqGgESlkr_Y-pPgT29LekI"
        private const val client_id = "Client-ID"
    }

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<PhotoList>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("photos/{id}/related")
    suspend fun getRelatedPhotos(@Path("id") id: String): Response<RelatedPhotos>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("search/photos")
    suspend fun getSearchPhoto(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): Response<SearchPhotos>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("search/users")
    suspend fun getSearchProfile(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): Response<ResultProfiles>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("topics")
    suspend fun getTopics(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<ArrayList<Topic>>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("/photos/{id}/download")
    suspend fun getUrlForDownloadImage(@Path("id") id: String): Response<Uri>
}