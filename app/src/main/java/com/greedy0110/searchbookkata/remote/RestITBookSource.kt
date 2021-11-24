package com.greedy0110.searchbookkata.remote

import com.greedy0110.searchbookkata.domain.ITBook
import com.greedy0110.searchbookkata.repository.RemoteITBookSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestITBookSource @Inject constructor(
    private val itBookService: ITBookService
) : RemoteITBookSource {
    override fun getByKeyword(keyword: String, page: Int): Single<List<ITBook>> {
        return itBookService.searchBooks(keyword, page)
            .map { response -> response.books.map { book -> book.toITBook() } }
            .compose()
    }
}