package com.flashcards.flashcards.ui.navigator

import com.flashcards.flashcards.ui.progress.model.TestCase

interface ProgressNavigator {
    fun testFunction(testCase: TestCase)
    fun notifyAllTestsAlreadyPassed()
}
