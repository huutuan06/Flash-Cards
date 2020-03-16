package com.flashcards.flashcards.ui.home

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.databinding.FragmentHomeBinding
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.service.model.Vocabulary
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private var mHomeViewModel: HomeViewModel? = null

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: CardAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getBindingVariable(): Int {
        return BR.homeViewModel
    }

    override fun initAttributes() {
        initRecyclerView()
        subscribeObservers()
    }

    override fun getViewModel(): HomeViewModel {
        mHomeViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(HomeViewModel::class.java)
        return mHomeViewModel!!
    }

    private fun subscribeObservers() {
        mHomeViewModel!!.observeVocabularies().removeObservers(viewLifecycleOwner)
        mHomeViewModel!!.observeVocabularies().observe(
            viewLifecycleOwner,
            Observer<List<Vocabulary>> { vocabularies ->
                adapter.setCards(vocabularies as ArrayList<Vocabulary>)
            })
    }

    private fun initRecyclerView() {
        recycler_view_vocabularies.hasFixedSize()
        recycler_view_vocabularies.adapter = adapter
    }
}