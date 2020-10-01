package com.flashcards.flashcards.ui.jetpack

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class JetpackModule {

    @ContributesAndroidInjector(modules = [JetpackViewModelModule::class])
    abstract fun provideTouchFragment(): JetpackFragment
}
