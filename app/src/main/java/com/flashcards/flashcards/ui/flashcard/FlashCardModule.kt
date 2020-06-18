package com.flashcards.flashcards.ui.flashcard

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FlashCardModule {

    @ContributesAndroidInjector(modules = [FlashCardViewModelModule::class])
    abstract fun provideFlashCardFragment(): FlashCardFragment
}

