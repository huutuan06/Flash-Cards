package com.flashcards.flashcards.application

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class FlashCardsApp : GoogleApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }
}
