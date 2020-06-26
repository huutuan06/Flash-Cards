package com.flashcards.flashcards.ui.touch

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TouchModule {

    @ContributesAndroidInjector(modules = [TouchViewModelModule::class])
    abstract fun provideTouchFragment(): TouchFragment
}

