package com.flashcards.flashcards.ui.progress.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestCase constructor(
    var type: TestType,
    var name: String,
    var isTested: Boolean = false,
    var isSuccess: Boolean = false,
    var isTesting: Boolean = false
) : Parcelable {
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