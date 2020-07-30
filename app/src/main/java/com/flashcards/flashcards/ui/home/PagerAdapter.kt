package com.flashcards.flashcards.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flashcards.flashcards.ui.flashcard.FlashCardFragment
import com.flashcards.flashcards.ui.progress.ProgressFragment
import com.flashcards.flashcards.ui.touch.TouchFragment

const val FLASH_CARD_PAGE_INDEX = 0
const val TOUCH_SCREEN_INDEX = 1
const val PROGRESS_SCREEN_INDEX = 2

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentCreator: Map<Int, () -> Fragment> = mapOf(
        FLASH_CARD_PAGE_INDEX to { FlashCardFragment() },
        TOUCH_SCREEN_INDEX to { TouchFragment() },
        PROGRESS_SCREEN_INDEX to { ProgressFragment()}
    )

    override fun getItemCount(): Int = tabFragmentCreator.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}