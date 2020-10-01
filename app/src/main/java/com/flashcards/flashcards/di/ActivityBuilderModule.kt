package com.flashcards.flashcards.di

import com.flashcards.flashcards.di.scope.ActivityScope
import com.flashcards.flashcards.ui.home.HomePagerModule
import com.flashcards.flashcards.ui.jetpack.JetpackModule
import com.flashcards.flashcards.ui.main.MainActivity
import com.flashcards.flashcards.ui.main.MainModule
import com.flashcards.flashcards.ui.progress.ProgressModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainModule::class,
            HomePagerModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}
