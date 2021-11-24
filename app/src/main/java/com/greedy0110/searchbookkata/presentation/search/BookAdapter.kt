package com.greedy0110.searchbookkata.presentation.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.greedy0110.searchbookkata.presentation.BookUiModel

class BookAdapter(
    private val onClick: (BookUiModel) -> Unit
) : PagingDataAdapter<BookUiModel, BookViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onViewRecycled(holder: BookViewHolder) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<BookUiModel>() {
            override fun areItemsTheSame(oldItem: BookUiModel, newItem: BookUiModel): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: BookUiModel, newItem: BookUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}