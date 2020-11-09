package com.flashcards.flashcards.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.flashcards.flashcards.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_setting, rootKey)
    }

    override fun onResume() {
        super.onResume()

//        setActionBarTitle(R.string.title_settings)
//        setHasOptionsMenu(true)
//        setDisplayHomeAsUpEnabled(true)
    }
}
