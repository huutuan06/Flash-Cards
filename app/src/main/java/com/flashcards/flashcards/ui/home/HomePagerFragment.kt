package com.flashcards.flashcards.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentHomePagerBinding
import com.flashcards.flashcards.ui.home.adapter.*
import com.flashcards.flashcards.ui.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerAppCompatActivity
import kotlin.reflect.KClass

class HomePagerFragment : BaseFragment<FragmentHomePagerBinding, HomePagerViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_home_pager

    override val viewModelClass: KClass<HomePagerViewModel>
        get() = HomePagerViewModel::class

    override fun initViews() {
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = PagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as DaggerAppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_setting -> {
                goToSettings()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToSettings() {
        (activity as MainActivity).mNavController.navigate(R.id.settings_fragment)
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            FLASH_CARD_PAGE_INDEX -> R.drawable.garden_tab_selector
            PROGRESS_PAGE_INDEX -> R.drawable.plant_list_tab_selector
            JECKPACK_PAGE_INDEX -> R.drawable.garden_tab_selector
            NOTIFICATION_PAGE_INDEX -> R.drawable.plant_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            FLASH_CARD_PAGE_INDEX -> getString(R.string.app_name)
            PROGRESS_PAGE_INDEX -> getString(R.string.progress_title)
            JECKPACK_PAGE_INDEX -> getString(R.string.jetpack_title)
            NOTIFICATION_PAGE_INDEX -> getString(R.string.notification_title)
            else -> throw IndexOutOfBoundsException()
        }
    }
}
