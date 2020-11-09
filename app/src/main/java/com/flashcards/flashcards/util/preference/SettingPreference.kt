package com.flashcards.flashcards.util.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes
import androidx.preference.PreferenceManager
import com.flashcards.flashcards.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingPreference @Inject constructor(private val context: Context) {
    private var defaultPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getIsFastMode(): Boolean {
        return getBoolean(R.string.pref_key_fast_mode, false)
    }

    fun getIsTrustEPA(): Boolean {
        return getBoolean(R.string.pref_key_trust_epa, true)
    }

    fun getIsBargainOnDeny(): Boolean {
        return getBoolean(R.string.pref_key_bargain_on_deny, false)
    }

    fun getIsUseQRCode(): Boolean {
        return getBoolean(R.string.pref_key_use_qr_code, false)
    }

    /**
     * Support method to automatically rate device after uploading.
     * Deal is immediately rated `Good`.
     */
    fun getIsAutomaticRateDevice(): Boolean {
        return getBoolean(R.string.pref_key_automatic_rate_video, true)
    }

    fun getIsCameraInstructionVoiceInterruptable(): Boolean {
        return getBoolean(R.string.pref_key_interrupt_voice, true)
    }

    fun getIsHideUtilities(): Boolean {
        return getBoolean(R.string.pref_key_hide_utilities, true)
    }

    fun getIsTurnOffLadyVoice(): Boolean {
        return getBoolean(R.string.pref_key_turn_off_lady_voice, false)
    }

    fun getIsHideEvaluateButton(): Boolean {
        return getBoolean(R.string.pref_key_hide_evaluate, true)
    }

    fun getIsQuickTests(): Boolean {
        return getBoolean(R.string.pref_key_quick_tests, true)
    }

    //region support methods
    private fun getBoolean(@StringRes id: Int, defaultValue: Boolean = false): Boolean {
        return defaultPref.getBoolean(getPrefKey(id), defaultValue)
    }

    private fun getPrefKey(@StringRes id: Int): String {
        return context.resources.getString(id) ?: ""
    }
    //endregion
}