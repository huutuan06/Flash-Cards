package com.flashcards.flashcards.ui.progress

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.ui.navigator.ProgressNavigator
import com.flashcards.flashcards.ui.progress.model.Category
import com.flashcards.flashcards.ui.progress.model.SectionHeader
import com.flashcards.flashcards.ui.progress.model.TestCase
import com.flashcards.flashcards.ui.progress.model.TestSection
import com.flashcards.flashcards.util.applyIOScheduler
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.roundToInt

class ProgressViewModel @Inject constructor(
    context: Context,
    private val persistence: ProgressPersistence,
    private var navigator: ProgressNavigator
) : BaseViewModel() {

    sealed class Event {
        data class DoTest(val testCase: TestCase) : Event()
        data class FinishTest(val testCase: TestCase) : Event()
        object FinishTesting : Event()
    }

    private val listTestCases = persistence.testCases

    val testCaseAll = MutableLiveData<List<TestCase>>()

    val testCases1: LiveData<List<TestCase>> =
        Transformations.map(testCaseAll) { list ->
            list.filter { it.category == Category.Category1 }
        }

    val testCases2: LiveData<List<TestCase>> =
        Transformations.map(testCaseAll) { list ->
            list.filter { it.category == Category.Category2 }
        }

    val testCases3: LiveData<List<TestCase>> =
        Transformations.map(testCaseAll) { list ->
            list.filter { it.category == Category.Category3 }
        }

    val testCases4: LiveData<List<TestCase>> =
        Transformations.map(testCaseAll) { list ->
            list.filter { it.category == Category.Category4 }
        }

    val liveDataFinishTest = MutableLiveData<TestCase?>()
    val liveDataStartTest = MutableLiveData<TestCase?>()

    private val liveDataCurrentTestCase: LiveData<TestCase?> =
        Transformations.map(testCaseAll) { list ->
            list.find { it.isTesting }
        }.let {
            Transformations.distinctUntilChanged(it)
        }

    val progress: LiveData<Int> =
        Transformations.map(testCaseAll) { list ->
            (list.count { it.isTested } * 1.0 / list.size * 100).roundToInt()
        }

    private val isTesting = MutableLiveData(false)

    val headerAll = MutableLiveData(SectionHeader(context.getString(R.string.section_all)))
    val header1 = MutableLiveData(SectionHeader(context.getString(R.string.section_1)))
    val header2 = MutableLiveData(SectionHeader(context.getString(R.string.section_2)))
    val header3 = MutableLiveData(SectionHeader(context.getString(R.string.section_3)))
    val header4 = MutableLiveData(SectionHeader(context.getString(R.string.section_4)))

    private val currentTestingSection = MutableLiveData<TestSection?>()

    val resultSummary: LiveData<String> =
        Transformations.map(headerAll) { item ->
            context.getString(
                R.string.result_summary,
                item.successCount,
                item.currentCount - item.successCount,
                item.totalCount - item.currentCount
            )
        }.let {
            Transformations.distinctUntilChanged(it)
        }

    val floatingFragmentVisible: LiveData<Boolean> =
        Transformations.map(liveDataCurrentTestCase) { testCase ->
            if (testCase != null) {
                !testCase.isBackgroundTest
            } else {
                false
            }
        }

    private val eventAction = PublishSubject.create<Event>()
    val observableAction: Observable<Event> = eventAction.hide()

    init {
        listTestCases.forEach { it.isTesting = false }
        persistence.notifyUpdateTestCases()

        disposable.add(persistence.testResultObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (isTesting.value == true) {
                    it.isTesting = false
                    persistence.notifyUpdateTestCases()
                    eventAction.onNext(Event.FinishTest(it))

                    runNextTestOf(it)
                }
            })

        disposable.add(persistence.testCasesObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                testCaseAll.value = listTestCases
            })

        listenToUpdateHeader(testCaseAll, headerAll)
        listenToUpdateHeader(testCases1, header1)
        listenToUpdateHeader(testCases2, header2)
        listenToUpdateHeader(testCases3, header3)
        listenToUpdateHeader(testCases4, header4)

        currentTestingSection.observeForever {
            isTesting.value = it != null
            persistence.isContinuesTest = it != null

            updateHeaderIsRunning(headerAll, TestSection.All, it)
            updateHeaderIsRunning(header1, TestSection.Session1, it)
            updateHeaderIsRunning(header2, TestSection.Session2, it)
            updateHeaderIsRunning(header3, TestSection.Session3, it)
            updateHeaderIsRunning(header4, TestSection.Session4, it)
        }

        isTesting.observeForever {
            persistence.isContinuesTest = it
        }
    }

    private fun updateHeaderIsRunning(
        header: MutableLiveData<SectionHeader>,
        target: TestSection,
        currentSection: TestSection?
    ) {
        header.value?.copy(isRunning = currentSection == target)?.let {
            header.value = it
        }
    }

    private fun listenToUpdateHeader(
        listTestCase: LiveData<List<TestCase>>,
        header: MutableLiveData<SectionHeader>
    ) {
        listTestCase.observeForever { list ->
            val testedCount = list.count { it.isTested }
            val successCount = list.count { it.isSuccess }
            val isSuccess = list.all { it.isSuccess }
            header.value!!.copy(
                totalCount = list.size,
                currentCount = testedCount,
                successCount = successCount,
                isSuccess = isSuccess
            ).let {
                header.value = it
            }
        }
    }

    fun onTestCaseClick(testCase: TestCase) {
        if (isTesting.value != true) {
            navigator.testFunction(testCase)
        } else {
            Timber.w("onTestCaseClick -- continues test running")
        }
    }

    fun onSectionHeaderClicked(testSection: TestSection) {
        if (isTesting.value == true) {
            if (currentTestingSection.value == testSection) {
                stopTesting()
            } else {
                Timber.w("onSectionHeaderClicked: $testSection current = ${currentTestingSection.value}")
            }
        } else {
            startTesting(testSection)
        }
    }

    private fun stopTesting() {
        clearPendingDelayTest()

        currentTestingSection.value = null

        listTestCases.find { it.isTesting }?.let {
            it.isTesting = false
            eventAction.onNext(Event.FinishTest(it))
        }
        listTestCases.forEach { it.isTesting = false }
        persistence.notifyUpdateTestCases()

    }

    private fun startTesting(testSection: TestSection) {
        when (testSection) {
            TestSection.All -> testCaseAll
            TestSection.Session1 -> testCases1
            TestSection.Session2 -> testCases2
            TestSection.Session3 -> testCases3
            TestSection.Session4 -> testCases4
        }.value!!.find { !it.isSuccess }
            ?.let {
                currentTestingSection.value = testSection
                runTest(it)
            } ?: navigator.notifyAllTestsAlreadyPassed()
    }

    private fun runTest(testCase: TestCase) {
        if (currentTestingSection.value != null) {
            testCase.isTesting = true
            persistence.notifyUpdateTestCases()
            eventAction.onNext(Event.DoTest(testCase))
        }
    }

    private fun finishTest() {
        clearPendingDelayTest()
        currentTestingSection.value = null

        eventAction.onNext(Event.FinishTesting)
    }

    private fun runNextTestOf(testCase: TestCase) {
        when (currentTestingSection.value) {
            TestSection.All -> testCaseAll
            TestSection.Session1 -> testCases1
            TestSection.Session2 -> testCases2
            TestSection.Session3 -> testCases3
            TestSection.Session4 -> testCases4
            else -> {
                Timber.e("runNextTestOf -- currentTestingSection is null.")
                null
            }
        }?.value?.let { listTestCases ->
            listTestCases.findNextItemToTest(testCase)
                ?.apply {
                    isTesting = true
                    persistence.notifyUpdateTestCases()
                    // Delay a bit so that the progress is shown clearly
                    delayTest {
                        runTest(this)
                    }
                } ?: finishTest()
        }
    }

    private fun List<TestCase>.findNextItemToTest(current: TestCase): TestCase? {
        val index = indexOf(current)
        for (i in (index + 1)..lastIndex) {
            val item = get(i)
            if (!item.isSuccess) {
                return item
            }
        }
        return null
    }

    //region support test delay
    private val compositeDisposableDelayTest: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposableDelayTest.clear()
        super.onCleared()
    }

    private fun delayTest(timeInMillis: Long = 1000, action: () -> Unit) {
        compositeDisposableDelayTest.add(Completable.complete()
            .delay(timeInMillis, TimeUnit.MILLISECONDS)
            .applyIOScheduler()
            .doOnComplete {
                action()
            }
            .subscribe())
    }

    private fun clearPendingDelayTest() {
        compositeDisposableDelayTest.clear()
    }
    //endregion
}
