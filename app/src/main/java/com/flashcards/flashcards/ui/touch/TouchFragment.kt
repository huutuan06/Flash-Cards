package com.flashcards.flashcards.ui.touch

import androidx.lifecycle.ViewModelProvider
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentFlashcardBinding
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class TouchFragment : BaseFragment<FragmentFlashcardBinding, TouchViewModel>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun getLayoutId(): Int = R.layout.fragment_touch

    override fun getBindingVariable(): Int = BR.touchViewModel

    override fun getViewModel(): TouchViewModel =
        ViewModelProvider(this, viewModelProviderFactory).get(TouchViewModel::class.java)

    override fun initView() {
    }

}
