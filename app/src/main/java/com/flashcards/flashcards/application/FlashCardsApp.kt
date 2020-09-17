package com.flashcards.flashcards.application

import com.flashcards.flashcards.base.config.TimberReleaseTree
import com.flashcards.flashcards.util.isDebugVariant
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class FlashCardsApp : GoogleApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()

        if (isDebugVariant()) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(TimberReleaseTree())
        }
    }
}
