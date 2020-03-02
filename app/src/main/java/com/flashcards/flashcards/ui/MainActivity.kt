package com.flashcards.flashcards.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.flashcards.flashcards.R
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var mNavHostFragment: NavHostFragment
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        initNavigation()
    }

    private fun initNavigation() {
        mNavHostFragment =
            this.supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        mNavController = NavHostFragment.findNavController(mNavHostFragment)
        mNavController.setGraph(R.navigation.navigation_graph)
    }
}
