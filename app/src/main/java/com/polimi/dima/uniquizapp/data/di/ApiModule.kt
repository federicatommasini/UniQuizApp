package com.polimi.dima.uniquizapp.data.di

import com.polimi.dima.uniquizapp.data.api.QuizApi
import com.polimi.dima.uniquizapp.data.api.SubjectApi
import com.polimi.dima.uniquizapp.data.api.UniversityApi
import com.polimi.dima.uniquizapp.data.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule  {

    @Provides
    @Singleton
    fun provideSubjectApi(builder:Retrofit.Builder): SubjectApi {
        return builder
            .build()
            .create(SubjectApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(builder:Retrofit.Builder): UserApi {
        return builder
            .build()
            .create(UserApi::class.java)
    }
    @Provides
    @Singleton
    fun provideUniversityApi(builder:Retrofit.Builder): UniversityApi {
        return builder
            .build()
            .create(UniversityApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQuizApi(builder:Retrofit.Builder): QuizApi {
        return builder
            .build()
            .create(QuizApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder{
        val client = OkHttpClient.Builder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
            //.baseUrl("http://10.0.2.2:8080")
            .baseUrl("http://192.168.1.131:8080") //fede
            //.baseUrl("http://192.168.43.82:8080") //giulia
            .client(client)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }
}