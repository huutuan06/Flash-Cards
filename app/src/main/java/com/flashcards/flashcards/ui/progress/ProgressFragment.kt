package com.flashcards.flashcards.ui.progress

import android.graphics.Rect
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentProgressBinding
import com.flashcards.flashcards.ui.navigator.ProgressNavigator
import com.flashcards.flashcards.ui.progress.adapter.ProgressAdapter
import com.flashcards.flashcards.ui.progress.helper.TestCaseProvider
import com.flashcards.flashcards.ui.progress.model.Category
import com.flashcards.flashcards.ui.progress.model.TestCase
import com.flashcards.flashcards.ui.progress.model.TestType
import com.flashcards.flashcards.util.NoScrollLinearLayoutManager
import timber.log.Timber
import kotlin.random.Random
import kotlin.reflect.KClass

class ProgressFragment : BaseFragment<FragmentProgressBinding, ProgressViewModel>(),
    ProgressNavigator {

    override val layoutId: Int
        get() = R.layout.fragment_progress

    override val viewModelClass: KClass<ProgressViewModel>
        get() = ProgressViewModel::class

    private val testCaseProvider: TestCaseProvider
        get() = TestCaseProvider(context!!)

    lateinit var persistence: ProgressPersistence

    private lateinit var mTestFunctions: List<TestCase>

    override fun initViews() {
        setupRecyclerView()

        compositeDisposable.add(mViewModel.observableAction.subscribe { event ->
            when (event) {
                is ProgressViewModel.Event.DoTest -> {
                    Timber.d("initViews -- DoTest ${event.testCase}")
                    if (isResumed) {
                        goToTestFunction(event.testCase)
                    } else {
                        mViewModel.liveDataStartTest.value = event.testCase
                    }
                }
                is ProgressViewModel.Event.FinishTest -> {
                    Timber.d("initViews -- FinishTest ${event.testCase}")
                    if (isResumed) {
                        clearTest(event.testCase)
                    } else {
                        mViewModel.liveDataFinishTest.value = event.testCase
                    }
                }
                is ProgressViewModel.Event.FinishTesting -> {
                    //Scroll to the last tested item
                    mViewModel.testCaseAll.value!!.last { it.isTested }.let {
                        mHandler.postDelayed({
                            scrollItem(it)
                        }, 300)
                    }
                }
            }
        })
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

    override fun notifyAllTestsAlreadyPassed() {
        Toast.makeText(context, R.string.all_progresses_already_passed, Toast.LENGTH_LONG).show()
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

    private fun clearTest(testCase: TestCase) {
        if (testCase.isFullScreenTest) {
            //TODO: Handle testcase is full screen test in this Fragment, NOT in Activity
//            fragmentListener!!.onFragmentCallback(GEvent.Test.MainMenu)
        } else {
            clearAllFloatingFragment()
        }
    }

    private fun clearAllFloatingFragment() {
        // Currently, No Floating Fragment is used. This code just for enough cases.
        childFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun scrollItem(testCase: TestCase) {
        mHandler.post {
            when (testCase.category) {
                Category.Category1 -> binding.rv1
                Category.Category2 -> binding.rv2
                Category.Category3 -> binding.rv3
                Category.Category4 -> binding.rv4
            }.let { recyclerView ->
                (recyclerView.adapter as ProgressAdapter).indexOf(testCase)
                    .takeIf { it > 0 }
                    ?.let {
                        (recyclerView.layoutManager as LinearLayoutManager).getChildAt(it)
                    }
                    ?.takeUnless { itemView ->
                        val scrollBounds = Rect()
                        binding.scrollViewParent.getDrawingRect(scrollBounds)
                        val top = itemView.y + recyclerView.y
                        val bottom = top + itemView.height

                        scrollBounds.top < top && scrollBounds.bottom > bottom
                    }
                    ?.bottom
            }?.let {
                binding.scrollViewParent.smoothScrollTo(0, it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.rv1.adapter = null
        binding.rv2.adapter = null
        binding.rv3.adapter = null
        binding.rv4.adapter = null
    }
}
