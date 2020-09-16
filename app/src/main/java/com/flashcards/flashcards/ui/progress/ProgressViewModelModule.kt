package com.flashcards.flashcards.ui.progress

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import com.flashcards.flashcards.di.scope.ActivityScope
import com.flashcards.flashcards.di.scope.FragmentScope
import com.flashcards.flashcards.ui.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ProgressPersistenceModule::class])
abstract class ProgressViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProgressViewModel::class)
    abstract fun bindTouchViewModel(viewModel: ProgressViewModel): ViewModel
}


@Module
class ProgressPersistenceModule {

    //TODO Provide persistence from ProgressFragment instead of MainActivity
//    @Provides
//    fun providePersistence(fragment: ProgressFragment): ProgressPersistence {
//        return fragment.persistence
//    }

    @Provides
    fun providePersistence(activity: MainActivity): ProgressPersistence {
        return activity.persistence
    }
}
