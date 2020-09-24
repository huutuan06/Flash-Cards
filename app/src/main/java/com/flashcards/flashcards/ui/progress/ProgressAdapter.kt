package com.flashcards.flashcards.ui.progress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.flashcards.flashcards.databinding.ItemProgressBinding
import com.flashcards.flashcards.ui.progress.model.TestCase

class ProgressAdapter (
    lifecycleOwner: LifecycleOwner,
    private val onItemClick: (TestCase) -> Unit,
    private val listTestCase: LiveData<List<TestCase>>
) : RecyclerView.Adapter<ProgressAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        listTestCase.observe(lifecycleOwner, Observer {
            notifyDataSetChanged()
        })
        setHasStableIds(true)

        mOnClickListener = View.OnClickListener {
            val item = it.tag as TestCase
            onItemClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemProgressBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemAt(position)
        holder.binding.testCase = item

        with(holder.itemView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = listTestCase.value?.size ?: 0

    private fun itemAt(position: Int) : TestCase {
        return listTestCase.value!![position]
    }

    fun indexOf(testCase: TestCase): Int {
        return listTestCase.value?.indexOf(testCase) ?: -1
    }

    class ViewHolder(val binding: ItemProgressBinding) : RecyclerView.ViewHolder(binding.root)
}
