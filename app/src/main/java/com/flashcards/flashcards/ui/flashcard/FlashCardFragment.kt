package com.flashcards.flashcards.ui.flashcard

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentFlashcardBinding
import com.flashcards.flashcards.ui.flashcard.adapter.CardAdapter
import com.flashcards.flashcards.ui.view.LoadingDialog
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class FlashCardFragment : BaseFragment<FragmentFlashcardBinding, FlashCardViewModel>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var loadingDialog: LoadingDialog

    override fun getLayoutId(): Int = R.layout.fragment_flashcard

    override fun getViewModel(): FlashCardViewModel =
        ViewModelProvider(this, viewModelProviderFactory).get(FlashCardViewModel::class.java)

    private lateinit var cardAdapter: CardAdapter

    override fun initViews() {
        initRecyclerView()
        initSwipeToRefresh()

        mViewModel.isLoading.observe(this, Observer {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })

        hideWordDetailWhenScrollView()
        showTechniqueLearned()
        this.handleException()
    }

    private fun initRecyclerView() {
        cardAdapter = CardAdapter(
            this, mViewModel.listVocabularies,
            mViewModel::onWordSelected
        )

        binding.recyclerViewVocabularies.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context)
            adapter = cardAdapter
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeFresh.setOnRefreshListener {
            binding.swipeFresh.isRefreshing = false
            mViewModel.getVocabularies()
        }
    }

    private fun hideWordDetailWhenScrollView() {
        binding.recyclerViewVocabularies.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mViewModel.word.value = null
            }
        })
    }

    private fun showTechniqueLearned() {
        mViewModel.word.observe(this, Observer {
            if (it != null) {
                toast(R.string.data_binding)
            }
        })
    }

    private fun handleException() {
        compositeDisposable.add(mViewModel.observableAction.subscribe {
            when (it) {
                is FlashCardViewModel.Event.Error -> {
                    handleException(it.throwable)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.recyclerViewVocabularies.adapter = null
    }
}
