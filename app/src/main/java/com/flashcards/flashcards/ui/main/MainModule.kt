package com.flashcards.flashcards.ui.main

import com.flashcards.flashcards.di.scope.ActivityScope
import com.flashcards.flashcards.ui.view.LoadingDialog
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @ActivityScope
    @Provides
    fun provideLoadingDialog(activity: MainActivity) = LoadingDialog(activity)
}
