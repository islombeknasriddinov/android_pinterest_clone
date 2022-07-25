package com.example.pinterest_clone.di

import com.example.pinterest_clone.network.Server.IS_TESTER
import com.example.pinterest_clone.network.Server.SERVER_DEVELOPMENT
import com.example.pinterest_clone.network.Server.SERVER_PRODUCTION
import com.example.pinterest_clone.network.service.PhotoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Retrofit Related
     */

    @Provides
    fun server():String{
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit {
        return Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun photoService(): PhotoService{
        return retrofitClient().create(PhotoService::class.java)
    }


}