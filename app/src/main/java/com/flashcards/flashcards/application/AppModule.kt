package com.flashcards.flashcards.application

import android.app.Application
import android.content.Context
import com.flashcards.flashcards.service.repository.IService
import com.flashcards.flashcards.ui.ActivityModule
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Nguyen Huu Tuan on 26/02/2020.
 */

@Module(includes = [
    AndroidSupportInjectionModule::class,
    AndroidInjectionModule::class,
    ActivityModule::class])
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindContext(application: Application): Context

    @Binds
    @Singleton
    abstract fun bindService(service: IService): IService

}