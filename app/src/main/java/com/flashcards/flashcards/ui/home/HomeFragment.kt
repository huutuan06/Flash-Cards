package com.flashcards.flashcards.ui.home

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentHomeBinding
import com.flashcards.flashcards.ui.dialog.LoadingDialog
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var loadingDialog: LoadingDialog

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getBindingVariable(): Int {
        return BR.homeViewModel
    }

    override fun initView() {
        initRecyclerView()
        mViewModel.isLoading.observe(this, Observer {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    override fun initAttributes() {}

    override fun getViewModel(): HomeViewModel {
        return ViewModelProvider(this, viewModelProviderFactory).get(HomeViewModel::class.java)
    }

    private fun initRecyclerView() {
        val cardAdapter = CardAdapter(this, mViewModel.listVocabularies)

        binding.recyclerViewVocabularies.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context)
            adapter = cardAdapter
        }
    }
}
