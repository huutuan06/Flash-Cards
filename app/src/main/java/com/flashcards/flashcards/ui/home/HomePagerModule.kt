package com.flashcards.flashcards.ui.home

import com.flashcards.flashcards.di.scope.FragmentScope
import com.flashcards.flashcards.ui.flashcard.FlashCardModule
import com.flashcards.flashcards.ui.jetpack.JetpackModule
import com.flashcards.flashcards.ui.progress.ProgressModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomePagerModule {

    @FragmentScope
    @ContributesAndroidInjector(
        modules = [
            HomePagerViewModelModule::class,
            FlashCardModule::class,
            ProgressModule::class,
            JetpackModule::class
        ]
    )
    abstract fun provideHomePagerFragment(): HomePagerFragment
}
