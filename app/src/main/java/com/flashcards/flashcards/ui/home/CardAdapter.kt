package com.flashcards.flashcards.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flashcards.flashcards.R
import com.flashcards.flashcards.service.model.Vocabulary
import com.flashcards.flashcards.ui.MainActivity
import kotlinx.android.synthetic.main.item_card.view.*

class CardAdapter(
    lifecycleOwner: LifecycleOwner,
    private val data: LiveData<List<Vocabulary>>,
    private val onItemClicked: (Vocabulary) -> Unit
) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    init {
        data.observe(lifecycleOwner, Observer {
            notifyDataSetChanged()
        })
    }

    private val listItems: List<Vocabulary>
        get() = data.value.orEmpty()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        )
    }

    override fun getItemCount() = listItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        Glide.with(holder.itemView.context).load(listItems[position].image)
            .into(holder.imgCard)
        holder.apply {
            txtEnglishTitle.text = listItems[position].englishTitle.toString()
            txtVietnameseTitle.text = listItems[position].vietnameseTitle.toString()
            txtType.text = listItems[position].type.toString()
            txtContext.text = listItems[position].context.toString()
            txtExample.text = listItems[position].example.toString()
        }
        with(holder.itemView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    private val mOnClickListener = View.OnClickListener {
        val item = it.tag as Vocabulary
        onItemClicked(item)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgCard: ImageView = itemView.img_card
        var txtEnglishTitle: TextView = itemView.txt_english_title
        var txtVietnameseTitle: TextView = itemView.txt_vietnamese_title
        var txtType: TextView = itemView.txt_type
        var txtContext: TextView = itemView.txt_context
        var txtExample: TextView = itemView.txt_example
    }
}
