package com.flashcards.flashcards.ui.jetpack

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class JetpackInjector {

    @ContributesAndroidInjector(modules = [JetpackModule::class])
    abstract fun bindJetPackFragment(): JetpackFragment
}

@Module
abstract class JetpackModule {

    @Binds
    @IntoMap
    @ViewModelKey(JetpackViewModel::class)
    abstract fun bindJetpackViewModel(viewModel: JetpackViewModel): ViewModel
}
