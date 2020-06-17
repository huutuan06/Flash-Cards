package com.flashcards.flashcards.application

import com.flashcards.flashcards.service.connect.TrustHTTPS
import com.flashcards.flashcards.service.repository.IService
import com.flashcards.flashcards.ui.MainActivity
import com.flashcards.flashcards.ui.dialog.LoadingDialog
import com.flashcards.flashcards.util.APIConstant
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Nguyen Huu Tuan on 26/02/2020.
 */

@Module
class AppModule {

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
    internal fun provideRetrofitInstance(trustHTTPS: TrustHTTPS,client: OkHttpClient.Builder): Retrofit {
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
}
