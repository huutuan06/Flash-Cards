package com.flashcards.flashcards.ui.home

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentHomeBinding
import com.flashcards.flashcards.service.model.Vocabulary
import com.flashcards.flashcards.ui.MainActivity
import com.flashcards.flashcards.ui.dialog.LoadingDialog
import com.flashcards.flashcards.util.Constants
import com.flashcards.flashcards.util.FlashCardsAnalytics
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private var mHomeViewModel: HomeViewModel? = null

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: CardAdapter

    @Inject
    lateinit var loadingDialog: LoadingDialog

    @Inject
    lateinit var mMainActivity: MainActivity

    @Inject
    lateinit var mFirebaseAnalytics: FirebaseAnalytics

    @Inject
    lateinit var mFlashCardsAnalytics: FlashCardsAnalytics

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getBindingVariable(): Int {
        return BR.homeViewModel
    }

    override fun initAttributes() {
        mFlashCardsAnalytics.reportScreen(mFirebaseAnalytics, mMainActivity, Constants.HOME_SCREEN)
        initRecyclerView()
        mHomeViewModel!!.data.observe(this, changeObserver)
        loadingDialog.show()
    }

    override fun getViewModel(): HomeViewModel {
        mHomeViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(HomeViewModel::class.java)
        return mHomeViewModel!!
    }

    private val changeObserver = Observer<List<Vocabulary>> { vocabularies ->
        vocabularies?.let {
            adapter.setCards(vocabularies as ArrayList<Vocabulary>)
            loadingDialog.dismiss()
        }
    }

    private fun initRecyclerView() {
        recycler_view_vocabularies.hasFixedSize()
        recycler_view_vocabularies.layoutManager = LinearLayoutManager(context)
        recycler_view_vocabularies.adapter = adapter
    }
}