package com.flashcards.flashcards.base.config

import android.util.Log
import timber.log.Timber

class TimberReleaseTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        super.log(priority, tag, message, throwable)

        if (priority >= Log.WARN) {
            if (throwable != null) {
//                Crashlytics.logException(throwable)
            } else {
//                Crashlytics.log(priority, tag.orEmpty(), message.orEmpty())
            }
        }
    }
}
