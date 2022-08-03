package com.example.pinterest_clone.network.service

import com.example.pinterest_clone.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoService {

    companion object {

        private const val ACCESS_KEY = "Xx9ZDZTlUn7YsfX-kKUaPtIl4HfBGW50qFH1UnIJsU8"
        const val client_id = "Client-ID"
    }

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("photos")
    fun getPhotos(@Query("page") page: Int, @Query("per_page") perPage: Int): Call<PhotoList>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("photos/{id}/related")
    fun getRelatedPhotos(@Path("id") id: String): Call<RelatedPhotos>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("search/photos")
    fun getSearchPhoto(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): Call<SearchPhotos>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("search/users")
    fun getSearchProfile(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): Call<ResultProfiles>

    @Headers("Authorization:$client_id $ACCESS_KEY")
    @GET("topics")
    fun getTopics(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<ArrayList<Topic>>

}