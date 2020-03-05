package com.flashcards.flashcards.ui.home

import com.flashcards.flashcards.ui.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

//    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment
}