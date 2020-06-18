package com.flashcards.flashcards.ui.login

import com.flashcards.flashcards.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginModule {

    @ContributesAndroidInjector(modules = [LoginViewModelModule::class])
    abstract fun provideLoginFragment(): LoginFragment
}
