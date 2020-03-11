package com.flashcards.flashcards.di.login

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import com.flashcards.flashcards.di.scope.FragmentScope
import com.flashcards.flashcards.ui.login.LoginFragment
import com.flashcards.flashcards.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {

//    @FragmentScope
    @ContributesAndroidInjector(modules = [LoginViewModelModule::class])
    abstract fun provideLoginFragment(): LoginFragment

}