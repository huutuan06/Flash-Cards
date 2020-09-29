package com.flashcards.flashcards.ui.progress.helper

import android.content.Context
import com.flashcards.flashcards.R
import com.flashcards.flashcards.ui.progress.model.TestCase
import com.flashcards.flashcards.ui.progress.model.TestType
import javax.inject.Inject

class TestCaseProvider @Inject constructor(
    private val context: Context
) {
    init {
        checkUniqueTestTypeId()
    }

    fun getTestCases() =
        arrayListOf(
            TestCase(TestType.VIBRATOR, context.getString(R.string.vibrator)),
            TestCase(TestType.SPEAKER_AND_MIC, context.getString(R.string.speaker_and_mic)),
            TestCase(TestType.COMPASS, context.getString(R.string.compass)),
            TestCase(TestType.ACCELEROMETER_SENSOR, context.getString(R.string.accelerometer)),
            TestCase(TestType.FAKE_ITEM_1, context.getString(R.string.fake_item_1)),
            TestCase(TestType.FAKE_ITEM_2, context.getString(R.string.fake_item_2)),
            TestCase(TestType.FAKE_ITEM_3, context.getString(R.string.fake_item_3)),
            TestCase(TestType.FAKE_ITEM_4, context.getString(R.string.fake_item_4)),
            TestCase(TestType.FAKE_ITEM_5, context.getString(R.string.fake_item_5)),
            TestCase(TestType.FAKE_ITEM_6, context.getString(R.string.fake_item_6)),
            TestCase(TestType.FAKE_ITEM_7, context.getString(R.string.fake_item_7))
        ).apply {
            forEachIndexed { index, testCase ->
                testCase.index = index + 1
            }
        }.also { list ->
            //check duplicate items
            list.groupBy { it.type }.toList().find { it.second.size > 1 }
                ?.let {
                    throw IllegalArgumentException("Duplicate TestCase: ${it.second.first()}.")
                }
        }

    private fun checkUniqueTestTypeId() {
        TestType.values().map { it.id }.groupBy { it }.toList()
            .find { it.second.size > 1 }
            ?.let {
                throw IllegalArgumentException("Duplicate TestType ID: ${it.first}.")
            }
    }
}
