package com.flashcards.flashcards.ui.progress

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProgressModule {

    @ContributesAndroidInjector(modules = [ProgressViewModelModule::class])
    abstract fun provideTouchFragment(): ProgressFragment
}
