package com.flashcards.flashcards.base.config

import android.os.Parcelable
import com.flashcards.flashcards.ui.progress.model.TestCase
import com.flashcards.flashcards.ui.progress.model.TestType
import kotlinx.android.parcel.Parcelize

sealed class GEvent {
    @Parcelize
    object GoBackFragment : GEvent(), Parcelable

    @Parcelize
    object Finish : GEvent(), Parcelable

    sealed class Test : GEvent() {
        data class TestResult(val testType: TestType, val result: Boolean) : Test()

        data class Retake(val testType: TestType) : Test()

        data class RunNextTest(val testType: TestType) : Test()

        data class DoTest(val testType: TestType) : Test()

        data class TestDetached(val testType: TestType, val isResultDelivery: Boolean) : Test()

        data class ReportResult(val list: List<TestCase>) : Test()

        object MainMenu : Test()
        object FinishTest : Test()
        object FinishReport : Test()
    }
}