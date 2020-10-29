package com.flashcards.flashcards.util

fun  String?.ifNullOrEmpty(text: String): String {
    return if (isNullOrEmpty()) text else this!!
}