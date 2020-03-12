package com.flashcards.flashcards.di.app

import com.flashcards.flashcards.service.repository.IService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Nguyen Huu Tuan on 26/02/2020.
 */

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://shawn-movie-rental.herokuapp.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideIService(retrofit: Retrofit): IService {
        return retrofit.create(IService::class.java)
    }
}