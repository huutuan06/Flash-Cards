package com.flashcards.flashcards.ui.progress.model

enum class TestType(val id: Int) {
    VIBRATOR(1),
    SPEAKER_AND_MIC(2),
    COMPASS(3),
    ACCELEROMETER_SENSOR(4),
    FAKE_ITEM_1(5),
    FAKE_ITEM_2(6),
    FAKE_ITEM_3(7),
    FAKE_ITEM_4(8),
    FAKE_ITEM_5(9),
    FAKE_ITEM_6(10),
    FAKE_ITEM_7(11);

    companion object {
        fun fromId(id: Int): TestType? = values().find { it.id == id }
    }
}