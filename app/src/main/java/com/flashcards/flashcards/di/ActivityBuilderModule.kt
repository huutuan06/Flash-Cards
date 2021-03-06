package com.flashcards.flashcards.di

import com.flashcards.flashcards.di.scope.ActivityScope
import com.flashcards.flashcards.ui.home.HomePagerInjector
import com.flashcards.flashcards.ui.main.MainActivity
import com.flashcards.flashcards.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainModule::class,
            HomePagerInjector::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}
