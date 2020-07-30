package com.flashcards.flashcards.ui.progress

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentProgressBinding
import com.flashcards.flashcards.ui.progress.helper.TestCaseProvider
import com.flashcards.flashcards.ui.progress.model.TestCase
import com.flashcards.flashcards.util.NoScrollLinearLayoutManager
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class ProgressFragment : BaseFragment<FragmentProgressBinding, ProgressViewModel>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

//    private lateinit var testCaseProvider: TestCaseProvider

    @Inject
    lateinit var persistence: ProgressPersistence

    private lateinit var mTestFunctions : List<TestCase>

    override fun getLayoutId(): Int = R.layout.fragment_progress

    override fun getBindingVariable(): Int = BR.progressViewModel

    override fun getViewModel(): ProgressViewModel =
        ViewModelProvider(this, viewModelProviderFactory).get(ProgressViewModel::class.java)

    override fun initView() {
        setupRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        persistence = ProgressPersistence(isGroupTest = false, testCases = testCaseProvider.getTestCases())
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
}
