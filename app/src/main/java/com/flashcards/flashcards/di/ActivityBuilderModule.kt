package com.flashcards.flashcards.di

import com.flashcards.flashcards.di.scope.ActivityScope
import com.flashcards.flashcards.ui.MainActivity
import com.flashcards.flashcards.di.home.HomeModule
import com.flashcards.flashcards.di.login.LoginModule
import com.flashcards.flashcards.service.repository.IService
import com.flashcards.flashcards.ui.MainModule
import com.flashcards.flashcards.ui.dialog.LoadingDialog
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, HomeModule::class, LoginModule::class])
    abstract fun contributeMainActivity() : MainActivity

}