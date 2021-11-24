package com.greedy0110.searchbookkata.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greedy0110.searchbookkata.databinding.ViewholderBookBinding
import com.greedy0110.searchbookkata.presentation.BookUiModel
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

class BookViewHolder(
    private val binding: ViewholderBookBinding,
    private val onClick: (BookUiModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val disposableBags = CompositeDisposable()

    fun onBind(model: BookUiModel) {
        //this subscription should be subscribed at onBind()
        //  and be disposed at onRecycled()
        binding.root.clicks()
            .toFlowable(BackpressureStrategy.LATEST)
            .subscribeBy(onNext = { onClick(model) })
            .addTo(disposableBags)

        Glide.with(binding.root)
            .load(model.imageUrl)
            .override(56)
            .fitCenter()
            .into(binding.thumbnailBookImage)

        binding.titleText.text = model.title
    }

    fun onRecycled() {
        disposableBags.clear()
    }

    companion object {
        fun create(
            viewGroup: ViewGroup,
            onClick: (BookUiModel) -> Unit
        ): BookViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            return BookViewHolder(
                ViewholderBookBinding.inflate(inflater, viewGroup, false),
                onClick
            )
        }
    }
}