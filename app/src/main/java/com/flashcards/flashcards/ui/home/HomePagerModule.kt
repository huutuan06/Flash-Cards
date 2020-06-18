package com.flashcards.flashcards.ui.home

import com.flashcards.flashcards.di.scope.FragmentScope
import com.flashcards.flashcards.ui.flashcard.FlashCardModule
import com.flashcards.flashcards.ui.login.LoginModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomePagerModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomePagerViewModelModule::class, FlashCardModule::class, LoginModule::class])
    abstract fun provideHomePagerFragment(): HomePagerFragment
}


