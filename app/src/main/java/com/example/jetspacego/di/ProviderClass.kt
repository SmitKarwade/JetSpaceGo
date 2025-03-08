package com.example.jetspacego.di

import com.example.jetspacego.constants.Constants
import com.example.jetspacego.request.MongoService
import com.example.jetspacego.request.SpaceService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderClass {

    @Provides
    @Singleton
    fun provideSpaceService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSpaceServiceInstance(retrofit: Retrofit): SpaceService {
        return retrofit.create(SpaceService::class.java)
    }


    @Provides
    @Singleton
    fun provideMongoService(): MongoService{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MongoService::class.java)
    }

}
