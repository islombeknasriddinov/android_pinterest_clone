package com.example.pinterest_clone.di

import android.app.Application
import com.example.pinterest_clone.db.AppDatabase
import com.example.pinterest_clone.db.PhotoHomeDao
import com.example.pinterest_clone.network.Server.IS_TESTER
import com.example.pinterest_clone.network.Server.getDevelopment
import com.example.pinterest_clone.network.Server.getProduction
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
        if (IS_TESTER) return getDevelopment()
        return getProduction()
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

    /**
     * Room related
     */

    @Provides
    @Singleton
    fun appDatabase(context: Application): AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun dao(appDatabase: AppDatabase): PhotoHomeDao{
        return appDatabase.getPhotoHomeDao()
    }

}