package com.flashcards.flashcards.ui.progress

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.ui.progress.model.Category
import com.flashcards.flashcards.ui.progress.model.TestCase
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import kotlin.math.roundToInt

class ProgressViewModel @Inject constructor(
    context: Context,
    persistence: ProgressPersistence
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

    val liveDataCurrentTestCase : LiveData<TestCase?> =
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

    init {
        listTestCases.forEach { it.isTesting = false }
        persistence.notifyUpdateTestCases()

        disposable.add(persistence.testCasesObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                liveDataTestCases.value = listTestCases
            })
    }

    fun onTestCaseClick(testCase: TestCase) {

    }
}
