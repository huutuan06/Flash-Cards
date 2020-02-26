package com.example.flashcards.application

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by Nguyen Huu Tuan on 26/02/2020.
 */

class FlashCardsApp : GoogleApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }
}