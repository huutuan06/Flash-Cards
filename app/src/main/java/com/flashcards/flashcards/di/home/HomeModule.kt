package com.flashcards.flashcards.di.home

import com.flashcards.flashcards.di.scope.FragmentScope
import com.flashcards.flashcards.ui.home.HomeFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeViewModelModule::class])
    abstract fun provideHomeFragment(): HomeFragment
}