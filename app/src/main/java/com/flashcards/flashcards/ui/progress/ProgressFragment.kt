package com.flashcards.flashcards.ui.progress

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentProgressBinding
import com.flashcards.flashcards.ui.navigator.ProgressNavigator
import com.flashcards.flashcards.ui.progress.helper.TestCaseProvider
import com.flashcards.flashcards.ui.progress.model.TestCase
import com.flashcards.flashcards.ui.progress.model.TestType
import com.flashcards.flashcards.util.NoScrollLinearLayoutManager
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import javax.inject.Inject
import kotlin.random.Random

class ProgressFragment : BaseFragment<FragmentProgressBinding, ProgressViewModel>(),
    ProgressNavigator {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private val testCaseProvider: TestCaseProvider
        get() = TestCaseProvider(context!!)

    lateinit var persistence: ProgressPersistence

    private lateinit var mTestFunctions: List<TestCase>

    override fun getLayoutId(): Int = R.layout.fragment_progress

    override fun getBindingVariable(): Int = BR.progressViewModel

    override fun getViewModel(): ProgressViewModel =
        ViewModelProvider(this, viewModelProviderFactory).get(ProgressViewModel::class.java)

    override fun initViews() {
        setupRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        persistence =
            ProgressPersistence(isGroupTest = false, testCases = testCaseProvider.getTestCases())
        super.onCreate(savedInstanceState)
        mTestFunctions = persistence.testCases
        persistence.notifyUpdateTestCases()
    }

    private fun setupRecyclerView() {
        fun bindLiveData(recyclerView: RecyclerView, liveData: LiveData<List<TestCase>>) {
            recyclerView.apply {
                layoutManager = NoScrollLinearLayoutManager(context!!)
                adapter = ProgressAdapter(
                    this@ProgressFragment,
                    mViewModel::onTestCaseClick,
                    liveData
                )
            }
        }

        bindLiveData(binding.rv1, mViewModel.testCases1)
        bindLiveData(binding.rv2, mViewModel.testCases2)
        bindLiveData(binding.rv3, mViewModel.testCases3)
        bindLiveData(binding.rv4, mViewModel.testCases4)
    }

    override fun testFunction(testCase: TestCase) {
        goToTestFunction(testCase)
    }

    private fun goToTestFunction(testCase: TestCase) {
        when (testCase.type) {
            TestType.VIBRATOR -> setRandomTestResult(TestType.VIBRATOR)
            TestType.SPEAKER_AND_MIC -> setRandomTestResult(TestType.SPEAKER_AND_MIC)
            TestType.COMPASS -> setRandomTestResult(TestType.COMPASS)
            TestType.ACCELEROMETER_SENSOR -> setRandomTestResult(TestType.ACCELEROMETER_SENSOR)
            TestType.FAKE_ITEM_1 -> setRandomTestResult(TestType.FAKE_ITEM_1)
            TestType.FAKE_ITEM_2 -> setRandomTestResult(TestType.FAKE_ITEM_2)
            TestType.FAKE_ITEM_3 -> setRandomTestResult(TestType.FAKE_ITEM_3)
            TestType.FAKE_ITEM_4 -> setRandomTestResult(TestType.FAKE_ITEM_4)
            TestType.FAKE_ITEM_5 -> setRandomTestResult(TestType.FAKE_ITEM_5)
            TestType.FAKE_ITEM_6 -> setRandomTestResult(TestType.FAKE_ITEM_6)
            TestType.FAKE_ITEM_7 -> setRandomTestResult(TestType.FAKE_ITEM_7)
        }
    }

    private fun setRandomTestResult(testType: TestType) {
        val result = Random.nextBoolean()
        mTestFunctions.first { it.type == testType }.apply {
            persistence.updateTestResult(this, result)
        }
    }
}
