package com.flashcards.flashcards.ui.touch

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TouchViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TouchViewModel::class)
    abstract fun bindTouchViewModel(viewModel: TouchViewModel): ViewModel
}
