package com.flashcards.flashcards.ui.home

import androidx.lifecycle.ViewModelProvider
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentHomePagerBinding
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HomePagerFragment : BaseFragment<FragmentHomePagerBinding, HomePagerViewModel>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun getLayoutId(): Int = R.layout.fragment_home_pager

    override fun getBindingVariable(): Int = BR.homePagerViewModel

    override fun getViewModel(): HomePagerViewModel =
        ViewModelProvider(this, viewModelProviderFactory).get(
            HomePagerViewModel::class.java
        )

    override fun initView() {
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = PagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as DaggerAppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            FLASH_CARD_PAGE_INDEX -> R.drawable.garden_tab_selector
            TOUCH_SCREEN_INDEX -> R.drawable.plant_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            FLASH_CARD_PAGE_INDEX -> getString(R.string.app_name)
            TOUCH_SCREEN_INDEX -> getString(R.string.login_title)
            else -> throw IndexOutOfBoundsException()
        }
    }
}
