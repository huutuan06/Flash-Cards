package com.flashcards.flashcards.ui.home

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import com.flashcards.flashcards.di.scope.FragmentScope
import com.flashcards.flashcards.ui.flashcard.FlashCardInjector
import com.flashcards.flashcards.ui.jetpack.JetpackInjector
import com.flashcards.flashcards.ui.notification.NotificationInjector
import com.flashcards.flashcards.ui.progress.ProgressInjector
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomePagerInjector {

    @FragmentScope
    @ContributesAndroidInjector(
        modules = [
            HomePagerModule::class,
            FlashCardInjector::class,
            ProgressInjector::class,
            JetpackInjector::class,
            NotificationInjector::class
        ]
    )
    abstract fun provideHomePagerFragment(): HomePagerFragment
}

@Module
abstract class HomePagerModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomePagerViewModel::class)
    abstract fun bindHomePagerViewModel(viewModel: HomePagerViewModel): ViewModel
}
