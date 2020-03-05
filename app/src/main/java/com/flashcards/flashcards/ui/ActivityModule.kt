package com.flashcards.flashcards.ui

import com.flashcards.flashcards.ui.home.HomeModule
import com.flashcards.flashcards.ui.login.LoginModule
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeModule::class, LoginModule::class])
    abstract fun contributeMainActivity() : MainActivity

//    @ActivityScope
//    @Provides
//    fun provideNavHostFragment(): NavHostFragment =
//        activity.supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
//
//    @ActivityScope
//    @Provides
//    fun provideNavController(navHostFragment: NavHostFragment): NavController =
//        NavHostFragment.findNavController(navHostFragment)
}