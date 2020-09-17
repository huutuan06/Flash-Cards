package com.flashcards.flashcards.ui.progress.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.flashcards.flashcards.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestCase constructor(
    var type: TestType,
    var name: String,
    var index: Int = -1, // Index of this item in list
    var isTested: Boolean = false,
    var isSuccess: Boolean = false,
    var isTesting: Boolean = false
) : Parcelable {

    fun getWrapperDrawable(context: Context) : Drawable =
        when {
            !isTested -> R.drawable.bg_rectangle_small_round_border_neutral
            isSuccess -> R.drawable.bg_rectangle_small_round_border_success
            else -> R.drawable.bg_rectangle_small_round_border_fail
        }.let {
            ContextCompat.getDrawable(context, it)!!
        }

    @ColorInt
    fun getTextColor(context: Context) =
        when {
            !isTested -> R.color.textColorPrimary
            isSuccess -> R.color.color_success
            else -> R.color.color_fail
        }.let {
            ContextCompat.getColor(context, it)
        }

    val isFullScreenTest: Boolean
        get() = when (type) {
            TestType.VIBRATOR -> false
            TestType.SPEAKER_AND_MIC -> true
            TestType.COMPASS -> true
            TestType.ACCELEROMETER_SENSOR -> true
            TestType.FAKE_ITEM_1 -> false
            TestType.FAKE_ITEM_2 -> false
            TestType.FAKE_ITEM_3 -> false
            TestType.FAKE_ITEM_4 -> false
            TestType.FAKE_ITEM_5 -> false
            TestType.FAKE_ITEM_6 -> false
            TestType.FAKE_ITEM_7 -> false
        }

    val isBackgroundTest: Boolean
        get() = when (type) {
            TestType.VIBRATOR -> true
            TestType.SPEAKER_AND_MIC -> false
            TestType.COMPASS -> false
            TestType.ACCELEROMETER_SENSOR -> false
            TestType.FAKE_ITEM_1 -> false
            TestType.FAKE_ITEM_2 -> false
            TestType.FAKE_ITEM_3 -> false
            TestType.FAKE_ITEM_4 -> false
            TestType.FAKE_ITEM_5 -> false
            TestType.FAKE_ITEM_6 -> false
            TestType.FAKE_ITEM_7 -> false
        }

    val category: Category
        get() = when (type) {
            TestType.VIBRATOR -> Category.Category1
            TestType.SPEAKER_AND_MIC -> Category.Category1
            TestType.COMPASS -> Category.Category1
            TestType.ACCELEROMETER_SENSOR -> Category.Category1
            TestType.FAKE_ITEM_1 -> Category.Category2
            TestType.FAKE_ITEM_2 -> Category.Category2
            TestType.FAKE_ITEM_3 -> Category.Category3
            TestType.FAKE_ITEM_4 -> Category.Category3
            TestType.FAKE_ITEM_5 -> Category.Category4
            TestType.FAKE_ITEM_6 -> Category.Category4
            TestType.FAKE_ITEM_7 -> Category.Category4
        }
}