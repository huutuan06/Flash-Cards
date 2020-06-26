package com.flashcards.flashcards.ui.flashcard

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FlashCardViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FlashCardViewModel::class)
    abstract fun bindFlashCardViewModel(viewModel: FlashCardViewModel): ViewModel
}
