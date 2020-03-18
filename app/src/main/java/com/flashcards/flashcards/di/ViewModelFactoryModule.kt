package com.flashcards.flashcards.di

import androidx.lifecycle.ViewModelProvider
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}