package com.flashcards.flashcards.ui.jetpack

import androidx.lifecycle.ViewModelProvider
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentJetpackBinding
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class JetpackFragment : BaseFragment<FragmentJetpackBinding, JetpackViewModel>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun getLayoutId(): Int = R.layout.fragment_jetpack

    override fun getBindingVariable(): Int = BR.jetpackViewModel

    override fun getViewModel(): JetpackViewModel =
        ViewModelProvider(this, viewModelProviderFactory).get(JetpackViewModel::class.java)
}
