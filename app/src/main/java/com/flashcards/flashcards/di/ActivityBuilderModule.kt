package com.flashcards.flashcards.di

import com.flashcards.flashcards.di.home.HomeModule
import com.flashcards.flashcards.di.login.LoginModule
import com.flashcards.flashcards.di.scope.ActivityScope
import com.flashcards.flashcards.ui.MainActivity
import com.flashcards.flashcards.ui.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, HomeModule::class, LoginModule::class])
    abstract fun contributeMainActivity() : MainActivity
}
