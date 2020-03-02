package com.flashcards.flashcards.ui

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.flashcards.flashcards.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainActivityModule {

    private lateinit var activity: MainActivity

    @Singleton
    @Provides
    fun provideMainActivity() : MainActivity = activity

    @Singleton
    @Provides
    fun provideNavHostFragment(): NavHostFragment =
        activity.supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment

    @Singleton
    @Provides
    fun provideNavController(navHostFragment: NavHostFragment): NavController =
        NavHostFragment.findNavController(navHostFragment)
}