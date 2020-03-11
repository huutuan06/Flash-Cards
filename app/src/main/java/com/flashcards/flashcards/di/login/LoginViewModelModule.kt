package com.flashcards.flashcards.di.login

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import com.flashcards.flashcards.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoginViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}