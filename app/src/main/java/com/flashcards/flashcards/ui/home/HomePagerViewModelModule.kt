package com.flashcards.flashcards.ui.home

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomePagerViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomePagerViewModel::class)
    abstract fun bindHomePagerViewModel(viewModel: HomePagerViewModel): ViewModel
}
