package com.flashcards.flashcards.application

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.flashcards.flashcards.database.MainDatabase
import com.flashcards.flashcards.api.connect.TrustHTTPS
import com.flashcards.flashcards.api.repository.IService
import com.flashcards.flashcards.api.repository.IServiceCoroutines
import com.flashcards.flashcards.util.APIConstant
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
    }

    @Singleton
    @Provides
    internal fun provideTrustHttpS(client: OkHttpClient.Builder): TrustHTTPS {
        return TrustHTTPS(client)
    }

    @Singleton
    @Provides
    internal fun provideRetrofitInstance(trustHTTPS: TrustHTTPS, client: OkHttpClient.Builder): Retrofit {
        trustHTTPS.intializeCertificate()
        return Retrofit.Builder()
            .baseUrl(APIConstant.BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideIService(retrofit: Retrofit): IService {
        return retrofit.create(IService::class.java)
    }

    @Singleton
    @Provides
    fun provideIServiceCoroutines(client: OkHttpClient.Builder): IServiceCoroutines {
        val retrofit = Retrofit.Builder()
            .baseUrl(APIConstant.BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return retrofit.create(IServiceCoroutines::class.java)
    }

    @Singleton
    @Provides
    fun provideMainDatabase(context: Context): MainDatabase {
        return Room.databaseBuilder(context, MainDatabase::class.java, "AppDB").build()
    }
}
