package com.flashcards.flashcards.ui.home

import com.flashcards.flashcards.di.scope.FragmentScope
import com.flashcards.flashcards.ui.flashcard.FlashCardModule
import com.flashcards.flashcards.ui.progress.ProgressModule
import com.flashcards.flashcards.ui.touch.TouchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomePagerModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomePagerViewModelModule::class, FlashCardModule::class, TouchModule::class, ProgressModule::class])
    abstract fun provideHomePagerFragment(): HomePagerFragment
}

