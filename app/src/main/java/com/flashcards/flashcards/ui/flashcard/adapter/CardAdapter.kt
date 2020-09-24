package com.flashcards.flashcards.ui.flashcard.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flashcards.flashcards.databinding.ItemCardBinding
import com.flashcards.flashcards.service.model.Vocabulary

class CardAdapter(
    lifecycleOwner: LifecycleOwner,
    private val data: LiveData<List<Vocabulary>>,
    private val onItemClicked: (Vocabulary) -> Unit
) :
    ListAdapter<Vocabulary, CardAdapter.ViewHolder>(CardDiffCallback()) {

    init {
        data.observe(lifecycleOwner, Observer {
//            notifyDataSetChanged()
            submitList(listItems)
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

private class CardDiffCallback : DiffUtil.ItemCallback<Vocabulary>() {

    override fun areItemsTheSame(oldItem: Vocabulary, newItem: Vocabulary): Boolean {
        return oldItem.englishTitle == newItem.englishTitle
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Vocabulary, newItem: Vocabulary): Boolean {
        return oldItem == newItem
    }
}
