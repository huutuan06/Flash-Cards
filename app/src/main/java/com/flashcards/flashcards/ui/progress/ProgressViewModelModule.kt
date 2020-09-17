package com.flashcards.flashcards.ui.progress

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import com.flashcards.flashcards.di.scope.ActivityScope
import com.flashcards.flashcards.di.scope.FragmentScope
import com.flashcards.flashcards.ui.main.MainActivity
import com.flashcards.flashcards.ui.navigator.ProgressNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ProgressPersistenceModule::class])
abstract class ProgressViewModelModule {

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
