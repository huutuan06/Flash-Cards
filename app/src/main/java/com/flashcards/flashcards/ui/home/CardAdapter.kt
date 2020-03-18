package com.flashcards.flashcards.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flashcards.flashcards.R
import com.flashcards.flashcards.service.model.Vocabulary
import com.flashcards.flashcards.ui.MainActivity
import kotlinx.android.synthetic.main.item_card.view.*

class CardAdapter: RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    private var cardList = ArrayList<Vocabulary>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false))
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView.context).load(cardList[position].image).into(holder.imgCard)
        holder.txtEnglishTitle.text = cardList[position].englishTitle.toString()
        holder.txtVietnameseTitle.text = cardList[position].vietnameseTitle.toString()
        holder.txtType.text = cardList[position].type.toString()
        holder.txtContext.text = cardList[position].context.toString()
        holder.txtExample.text = cardList[position].example.toString()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var imgCard: ImageView = itemView.img_card
        var txtEnglishTitle: TextView = itemView.txt_english_title
        var txtVietnameseTitle: TextView = itemView.txt_vietnamese_title
        var txtType: TextView = itemView.txt_type
        var txtContext: TextView = itemView.txt_context
        var txtExample: TextView = itemView.txt_example

    }

    fun setCards(vocabularies: ArrayList<Vocabulary>){
        this.cardList = vocabularies
        notifyDataSetChanged()
    }
}