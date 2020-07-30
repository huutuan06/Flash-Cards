package com.flashcards.flashcards.util

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NoScrollLinearLayoutManager(context: Context) :
    LinearLayoutManager(context, RecyclerView.VERTICAL, false) {

    override fun canScrollVertically(): Boolean {
        return false
    }
}
