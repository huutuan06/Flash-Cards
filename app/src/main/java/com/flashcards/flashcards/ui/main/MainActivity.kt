package com.flashcards.flashcards.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.flashcards.flashcards.R
import com.flashcards.flashcards.databinding.ActivityMainBinding
import com.flashcards.flashcards.util.preference.SettingPreference
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var settingPreference: SettingPreference

    private lateinit var mNavHostFragment: NavHostFragment
    lateinit var mNavController: NavController

    private var mActivityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initNavigation()
    }

    private fun initNavigation() {
        mNavHostFragment =
            this.supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        mNavController = NavHostFragment.findNavController(mNavHostFragment)
        mNavController.setGraph(R.navigation.navigation_graph)
    }

    override fun onResume() {
        super.onResume()

        if (settingPreference.getIsFastMode()) {
            theme.applyStyle(R.style.AppTheme, true)
        } else {
            theme.applyStyle(R.style.AppThemeTest, true)
        }
    }
}
