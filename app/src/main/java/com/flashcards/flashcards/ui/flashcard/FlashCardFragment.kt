package com.flashcards.flashcards.ui.flashcard

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentFlashcardBinding
import com.flashcards.flashcards.ui.dialog.LoadingDialog
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class FlashCardFragment : BaseFragment<FragmentFlashcardBinding, FlashCardViewModel>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var loadingDialog: LoadingDialog

    override fun getLayoutId(): Int = R.layout.fragment_flashcard

    override fun getBindingVariable(): Int = BR.flashcardViewModel

    override fun getViewModel(): FlashCardViewModel =
        ViewModelProvider(this, viewModelProviderFactory).get(FlashCardViewModel::class.java)

    override fun initView() {
        initRecyclerView()

        mViewModel.isLoading.observe(this, Observer {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })

        compositeDisposable.add(mViewModel.observableAction.subscribe {
            when (it) {
                is FlashCardViewModel.Event.Error -> {
                    handleException(it.throwable)
                }
            }
        })

        binding.recyclerViewVocabularies.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mViewModel.word.value = null
            }
        })
    }

    private fun initRecyclerView() {
        val cardAdapter = CardAdapter(
            this, mViewModel.listVocabularies,
            mViewModel::onWordSelected
        )

        binding.recyclerViewVocabularies.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context)
            adapter = cardAdapter
        }
    }
}
