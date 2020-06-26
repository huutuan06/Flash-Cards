package com.flashcards.flashcards.ui.flashcard

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
import com.flashcards.flashcards.databinding.ItemCardBinding
import com.flashcards.flashcards.service.model.Vocabulary
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
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = listItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vocabulary = listItems[position]
        holder.bind(vocabulary)

        with(holder.itemView) {
            tag = vocabulary
            setOnClickListener(mOnClickListener)
        }
    }

    private val mOnClickListener = View.OnClickListener {
        val item = it.tag as Vocabulary
        onItemClicked(item)
    }

    class ViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Vocabulary) {
            binding.apply {
                vocabulary = item
            }
        }
    }
}
