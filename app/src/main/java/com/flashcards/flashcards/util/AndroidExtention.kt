package com.flashcards.flashcards.util

import com.flashcards.flashcards.BuildConfig

fun isDebugVariant() = BuildConfig.BUILD_TYPE == "debug"