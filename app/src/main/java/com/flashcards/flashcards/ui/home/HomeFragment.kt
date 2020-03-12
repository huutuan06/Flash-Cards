package com.flashcards.flashcards.ui.home

import androidx.lifecycle.ViewModelProvider
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.databinding.FragmentHomeBinding
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private var mHomeViewModel: HomeViewModel? = null

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getBindingVariable(): Int {
        return BR.homeViewModel
    }

    override fun initAttributes() {
        mHomeViewModel!!.getAllVocabularies()
    }

    override fun getViewModel(): HomeViewModel {

        mHomeViewModel = ViewModelProvider(this, viewModelProviderFactory).get(HomeViewModel::class.java)
        return mHomeViewModel!!
    }
}