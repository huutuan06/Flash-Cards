package com.flashcards.flashcards.ui.main

import com.flashcards.flashcards.di.scope.ActivityScope
import com.flashcards.flashcards.ui.dialog.LoadingDialog
import com.flashcards.flashcards.ui.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @ActivityScope
    @Provides
    fun provideLoadingDialog(activity: MainActivity) = LoadingDialog(activity)
}
