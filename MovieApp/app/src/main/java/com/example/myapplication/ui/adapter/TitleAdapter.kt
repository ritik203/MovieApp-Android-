package com.example.myapplication.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemTitleBinding
import com.example.myapplication.model.Title

interface OnItemClickListener {
    fun onItemClick(title: Title)
}

class TitleAdapter(private val listener: OnItemClickListener) : ListAdapter<Title, TitleAdapter.TitleViewHolder>(TitleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        Log.d("TitleAdapter", "onCreateViewHolder() called")
        val binding = ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TitleViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val currentTitle = getItem(position)
        Log.d("TitleAdapter", "onBindViewHolder() called for position $position: ${currentTitle.title}")
        holder.bind(currentTitle)
    }

    class TitleViewHolder(private val binding: ItemTitleBinding, private val listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        private var currentTitle: Title? = null

        init {
            binding.root.setOnClickListener {
                currentTitle?.let {
                    listener.onItemClick(it)
                }
            }
        }

        fun bind(title: Title) {
            currentTitle = title
            binding.titleTextView.text = title.title
            binding.yearTextView.text = "Year: ${title.year}"
        }
    }

    class TitleDiffCallback : DiffUtil.ItemCallback<Title>() {
        override fun areItemsTheSame(oldItem: Title, newItem: Title): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Title, newItem: Title): Boolean {
            return oldItem == newItem
        }
    }
}