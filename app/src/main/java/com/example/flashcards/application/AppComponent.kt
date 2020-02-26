package com.example.flashcards.application

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

/**
 * Created by Nguyen Huu Tuan on 26/02/2020.
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<DaggerApplication>{

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: FlashCardsApp)

    override fun inject(instance: DaggerApplication)
}