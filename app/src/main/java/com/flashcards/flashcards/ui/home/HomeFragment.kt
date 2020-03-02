package com.flashcards.flashcards.ui.home

import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.databinding.FragmentHomeBinding
import com.flashcards.flashcards.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initAttributes() {
    }

    override fun getBindingVariable(): Int {
        return BR.homeViewModel
    }

}