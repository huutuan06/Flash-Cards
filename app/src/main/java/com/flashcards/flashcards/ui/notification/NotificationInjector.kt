package com.flashcards.flashcards.ui.notification

import androidx.lifecycle.ViewModel
import com.flashcards.flashcards.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class NotificationInjector {

    @ContributesAndroidInjector(modules = [NotificationModule::class])
    abstract fun bindNotificationFragment(): NotificationFragment
}

@Module
abstract class NotificationModule {

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel::class)
    abstract fun bindNotificationViewModel(viewModel: NotificationViewModel): ViewModel
}
