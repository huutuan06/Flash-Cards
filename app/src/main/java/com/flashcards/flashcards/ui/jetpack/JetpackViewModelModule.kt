package com.flashcards.flashcards.ui.jetpack

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class JetpackViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(JetpackViewModel::class)
    abstract fun bindJetpackViewModel(viewModel: JetpackViewModel): ViewModel
}
