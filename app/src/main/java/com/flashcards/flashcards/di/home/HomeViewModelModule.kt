package com.flashcards.flashcards.di.home

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import com.flashcards.flashcards.ui.home.HomeViewModel
import com.flashcards.flashcards.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindLoginViewModel(viewModel: HomeViewModel): ViewModel
}

