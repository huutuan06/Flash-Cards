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
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.roundToInt

class ProgressViewModel @Inject constructor(
    context: Context,
    persistence: ProgressPersistence,
    var navigator: ProgressNavigator
) : BaseViewModel() {

    private val listTestCases = persistence.testCases

    val liveDataTestCases = MutableLiveData<List<TestCase>>()

    val testCases1: LiveData<List<TestCase>> =
        Transformations.map(liveDataTestCases) { list ->
            list.filter { it.category == Category.Category1 }
        }

    val testCases2: LiveData<List<TestCase>> =
        Transformations.map(liveDataTestCases) { list ->
            list.filter { it.category == Category.Category2 }
        }

    val testCases3: LiveData<List<TestCase>> =
        Transformations.map(liveDataTestCases) { list ->
            list.filter { it.category == Category.Category3 }
        }

    val testCases4: LiveData<List<TestCase>> =
        Transformations.map(liveDataTestCases) { list ->
            list.filter { it.category == Category.Category4 }
        }

    val liveDataFinishTest = MutableLiveData<TestCase?>()
    val liveDataStartTest = MutableLiveData<TestCase?>()

    val liveDataCurrentTestCase: LiveData<TestCase?> =
        Transformations.map(liveDataTestCases) { list ->
            list.find { it.isTesting }
        }.let {
            Transformations.distinctUntilChanged(it)
        }

    val progress: LiveData<Int> =
        Transformations.map(liveDataTestCases) { list ->
            (list.count { it.isTested } * 1.0 / list.size * 100).roundToInt()
        }

    val progressText = Transformations.map(progress) {
        String.format(context.getString(R.string.format_percent_int), it)
    }

    val isTesting = MutableLiveData(false)
    val iconStartStop: LiveData<Int> = Transformations.map(isTesting) {
        if (it) R.drawable.ic_pause else R.drawable.ic_play
    }

    val headerAll = MutableLiveData(SectionHeader(context.getString(R.string.section_all)))
    val header1 = MutableLiveData(SectionHeader(context.getString(R.string.section_1)))
    val header2 = MutableLiveData(SectionHeader(context.getString(R.string.section_2)))
    val header3 = MutableLiveData(SectionHeader(context.getString(R.string.section_3)))
    val header4 = MutableLiveData(SectionHeader(context.getString(R.string.section_4)))

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

    init {
        listTestCases.forEach { it.isTesting = false }
        persistence.notifyUpdateTestCases()

        disposable.add(persistence.testCasesObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                liveDataTestCases.value = listTestCases
            })

        listenToUpdateHeader(liveDataTestCases, headerAll)
        listenToUpdateHeader(testCases1, header1)
        listenToUpdateHeader(testCases2, header2)
        listenToUpdateHeader(testCases3, header3)
        listenToUpdateHeader(testCases4, header4)
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

    }
}
