package com.flashcards.flashcards.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.flashcards.flashcards.R
import com.flashcards.flashcards.databinding.ActivityMainBinding
import com.flashcards.flashcards.ui.progress.ProgressPersistence
import com.flashcards.flashcards.ui.progress.helper.TestCaseProvider
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var mNavHostFragment: NavHostFragment
    lateinit var mNavController: NavController

    private val testCaseProvider: TestCaseProvider
        get() = TestCaseProvider(this)

    lateinit var persistence: ProgressPersistence

    private var mActivityMainBinding: ActivityMainBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        persistence = ProgressPersistence(isGroupTest = false, testCases = testCaseProvider.getTestCases())
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initNavigation()
    }

    private fun initNavigation() {
        mNavHostFragment =
            this.supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        mNavController = NavHostFragment.findNavController(mNavHostFragment)
        mNavController.setGraph(R.navigation.navigation_graph)
    }
}
