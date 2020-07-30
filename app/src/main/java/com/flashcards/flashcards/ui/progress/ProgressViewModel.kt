package com.flashcards.flashcards.ui.progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.ui.progress.model.Category
import com.flashcards.flashcards.ui.progress.model.TestCase
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ProgressViewModel @Inject constructor(
    private val persistence: ProgressPersistence
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
