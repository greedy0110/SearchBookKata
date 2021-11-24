package com.greedy0110.searchbookkata.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookUiModel(
    val title: String,
    val imageUrl: String
) : Parcelable
