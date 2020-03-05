package com.flashcards.flashcards.ui.login

import com.flashcards.flashcards.ui.ActivityScope
import com.flashcards.flashcards.ui.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginModule {

//    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideLoginFragment(): LoginFragment

    companion object {
//        @FragmentScope
        @Provides
        fun provideString(): String = "aaa"
    }
}