package com.flashcards.flashcards.util

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.flashcards.flashcards.database.entities.Vocabulary
import com.flashcards.flashcards.ui.progress.widget.HeaderLayout
import com.flashcards.flashcards.ui.progress.model.SectionHeader

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("android:src")
fun setSrcImage(view: ImageView, vocabulary: Vocabulary) {
    val context = view.context
    Glide.with(context).load(vocabulary.image).into(view)
}

@BindingAdapter("imageRes")
fun setImageFromSourceID(imageView: ImageView, @DrawableRes resId: Int) {
    Glide.with(imageView.context).load(resId).into(imageView)
}

@BindingAdapter("app:sectionHeader")
fun setSectionHeaderValues(view: HeaderLayout, sectionHeader: SectionHeader) {
    view.setValues(
        totalCount = sectionHeader.totalCount,
        successCount = sectionHeader.successCount,
        failCount = sectionHeader.currentCount - sectionHeader.successCount
    )
}

@BindingAdapter("fragmentVisibility")
fun setFragmentVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}
