package com.flashcards.flashcards.ui.progress

import android.os.Parcelable
import com.flashcards.flashcards.ui.progress.model.TestCase
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProgressPersistence constructor(
    var isGroupTest: Boolean = false,
    val testCases: List<TestCase>,
    var currentTestCaseIndex: Int = -1
) : Parcelable {
    @IgnoredOnParcel var autoAdvanceToNextTest = false
    @IgnoredOnParcel var autoRetake = false
    @IgnoredOnParcel var autoGoToMainScreenOnSuccess = false
    @IgnoredOnParcel var autoGoToMainScreenOnFail = false

    @IgnoredOnParcel var dialogSupperBackPress = true

    @IgnoredOnParcel var isContinuesTest = true

    //region rx observable
    @IgnoredOnParcel
    private val testCasesSubject = BehaviorSubject.create<List<TestCase>>()
    @IgnoredOnParcel
    val testCasesObservable : Observable<List<TestCase>> = testCasesSubject.hide()

    fun notifyUpdateTestCases() {
        testCasesSubject.onNext(this.testCases)
    }

    @IgnoredOnParcel
    private val testResultSubject = BehaviorSubject.create<TestCase>()
    @IgnoredOnParcel
    val testResultObservable : Observable<TestCase> = testResultSubject.hide()

    fun updateTestResult(testCase: TestCase, result: Boolean) {
        testCase.isSuccess = result
        testCase.isTested = true

        testCases.first { it.type == testCase.type }.let {
            it.isSuccess = testCase.isSuccess
            it.isTested = testCase.isTested

            testResultSubject.onNext(it)
        }

        notifyUpdateTestCases()
    }
    //endregion
}
