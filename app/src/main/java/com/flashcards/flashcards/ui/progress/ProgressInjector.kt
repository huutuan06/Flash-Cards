package com.flashcards.flashcards.ui.progress

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import com.flashcards.flashcards.ui.navigator.ProgressNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ProgressInjector {

    @ContributesAndroidInjector(modules = [ProgressModule::class, ProgressPersistenceModule::class])
    abstract fun bindProgressFragment(): ProgressFragment
}

@Module(includes = [ProgressPersistenceModule::class])
abstract class ProgressModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProgressViewModel::class)
    abstract fun bindProgressViewModel(viewModel: ProgressViewModel): ViewModel

    @Binds
    abstract fun bindNavigator(impl: ProgressFragment): ProgressNavigator
}

@Module
class ProgressPersistenceModule {

    @Provides
    fun providePersistence(fragment: ProgressFragment): ProgressPersistence {
        return fragment.persistence
    }
}
