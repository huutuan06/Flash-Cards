package com.flashcards.flashcards.ui.flashcard

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FlashCardInjector {

    @ContributesAndroidInjector(modules = [FlashCardModule::class])
    abstract fun bindFlashCardFragment(): FlashCardFragment
}

@Module
abstract class FlashCardModule {

    @Binds
    @IntoMap
    @ViewModelKey(FlashCardViewModel::class)
    abstract fun bindFlashCardViewModel(viewModel: FlashCardViewModel): ViewModel
}
