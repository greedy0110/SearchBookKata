package com.greedy0110.searchbookkata.remote

import com.greedy0110.searchbookkata.domain.ITBook

data class SearchBooksResponse(
    val error: Int,
    val total: Int,
    val page: Int,
    val books: List<Book>
) {
    data class Book(
        val title: String,
        val subtitle: String,
        val ishbn13: String,
        val price: String,
        val image: String,
        val url: String
    )
}

fun SearchBooksResponse.Book.toITBook() = ITBook(
    title = title,
    subtitle = subtitle,
    isbn13 = ishbn13,
    price = price,
    imageUrl = image,
    url = url
)

fun ITBook.toSearchBooksResponseBook() = SearchBooksResponse.Book(
    title = title,
    subtitle = subtitle,
    ishbn13 = isbn13,
    price = price,
    image = imageUrl,
    url = url
)
