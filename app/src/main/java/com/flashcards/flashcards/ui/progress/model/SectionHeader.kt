package com.flashcards.flashcards.ui.progress.model

import android.content.Context
import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.flashcards.flashcards.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SectionHeader constructor(
    var name: String,
    var totalCount: Int = 0,
    var currentCount: Int = 0,
    var successCount: Int = 0,
    var isSuccess: Boolean = false, // True only if all test passed
    var isRunning: Boolean = false
) : Parcelable {

    fun getIcon() = if (isRunning) R.drawable.ic_pause else R.drawable.ic_play

    @ColorInt
    fun getBackgroundIndicator(context: Context) =
        when {
            currentCount < totalCount -> R.color.color_neutral
            isSuccess -> R.color.color_success
            else -> R.color.color_fail
        }.let {
            ContextCompat.getColor(context, it)
        }
}
