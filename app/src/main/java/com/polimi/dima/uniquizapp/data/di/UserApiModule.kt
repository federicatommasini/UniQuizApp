package com.polimi.dima.uniquizapp.data.di

import com.polimi.dima.uniquizapp.data.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserApiModule  {

    @Provides
    @Singleton
    fun provideApi(builder:Retrofit.Builder): UserApi{
        return builder
            .build()
            .create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            //.baseUrl("http://192.168.101.167:8080") //fede
            //.baseUrl("http://192.168.43.82:8080") //giulia
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }
}