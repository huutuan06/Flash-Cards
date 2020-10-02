package com.flashcards.flashcards.application

import android.app.Application
import com.flashcards.flashcards.di.ActivityBuilderModule
import com.flashcards.flashcards.di.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        ActivityBuilderModule::class,
        ViewModelFactoryModule::class,
        AppModule::class
    ]
)

interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: FlashCardsApp)

    override fun inject(instance: DaggerApplication)
}
