package com.flashcards.flashcards.ui

import com.flashcards.flashcards.di.scope.ActivityScope
import com.flashcards.flashcards.ui.dialog.LoadingDialog
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @ActivityScope
    @Provides
    fun provideLoadingDialog(activity: MainActivity) = LoadingDialog(activity)
}