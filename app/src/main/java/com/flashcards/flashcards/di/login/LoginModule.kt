package com.flashcards.flashcards.di.login

import com.flashcards.flashcards.di.scope.FragmentScope
import com.flashcards.flashcards.ui.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [LoginViewModelModule::class])
    abstract fun provideLoginFragment(): LoginFragment

}
