package com.flashcards.flashcards.util

import com.flashcards.flashcards.ui.MainActivity
import com.google.firebase.analytics.FirebaseAnalytics

class FlashCardsAnalytics {

    fun reportScreen(analytics: FirebaseAnalytics, activity: MainActivity, screenName: String) {
        analytics.setCurrentScreen(activity, screenName, screenName)
    }
}